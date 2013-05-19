/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "chave-untokenizer-maven-plugin"
 * Copyright 2013  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.CGEntry.onlyKeys;
import gate.AnnotationSet;
import gate.Document;
import gate.FeatureMap;
import gate.GateConstants;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;

import com.google.common.collect.ImmutableList;

public class Untokenizer {

	private static final Set<Match> NO_MATCHES = Collections.emptySet();

	private static final MatchStrategy DIRECTMATCH = new DirectMatchStrategy();
	private static final MatchStrategy CONTRACTIONMATCH = new ContractionMatchStrategy();
	private static final MatchStrategy ENCLITICMATCH = new EncliticMatchStrategy();
	private static final MatchStrategy NEWLINEMATCH = new NewLineMatchStrategy();
	private static final MatchStrategy EPSILONMATCH = new EpsilonMatchStrategy();
	
	private static final Parameters CONFIG02 = new Parameters(1, false, false, EPSILONMATCH);
	private static final Parameters CONFIG01 = new Parameters(CONFIG02, 1, false, false, DIRECTMATCH, CONTRACTIONMATCH, ENCLITICMATCH, NEWLINEMATCH);
	private static final Parameters CONFIG00 = new Parameters(CONFIG01, 0, false, true, DIRECTMATCH, CONTRACTIONMATCH, ENCLITICMATCH, NEWLINEMATCH);

	private final Document document;
	private final String text;
	private final List<CGEntry> cg;
	private final List<String> cgKeys;
	
	public Untokenizer(Document document, List<CGEntry> cg) {
		this.document = document;
		this.text = document.getContent().toString();
		this.cg = ImmutableList.copyOf(cg);
		this.cgKeys = ImmutableList.copyOf(onlyKeys(cg));
	}
	
	public static Document createDocument(String text) {
		Document result = new DocumentImpl();
		result.setContent(new DocumentContentImpl(text));
		return result;
	}

	public void untokenize() throws UntokenizerException {
		
		Set<Match> matches = untokenize(CONFIG00, 0, text.length(), 0, cgKeys.size());
		
		AnnotationSet originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);

		try {
			for (Match match : matches) {
				apply(match, originalMarkups);
			}
		} catch (InvalidOffsetException e) {
			throw new RuntimeException(e);
		}
	}

	private void apply(Match match, AnnotationSet markups) throws InvalidOffsetException {
		List<FeatureMap> tokens = new ArrayList<FeatureMap>();
		for (Span span : match.getSpans()) {
			if (span.from >= 0 && span.to >= 0) {
				FeatureMap features = new SimpleFeatureMapImpl();
				features.put("index", span.entry);
				features.put("match", cg.get(span.entry).getKey());
				features.put("cg", cg.get(span.entry).getValue());
				markups.add((long)span.from, (long)span.to, "token", features);
				
				tokens.add(features);
			}
		}
		
		FeatureMap fullMatch = new SimpleFeatureMapImpl();
		
		fullMatch.put("strategy", match.strategy.getName());
		fullMatch.put("tokens", tokens.toArray(new FeatureMap[tokens.size()]));
		
		markups.add((long)match.from, (long)match.to, "match", fullMatch);
	}

	public abstract class AbstractWork { 
		protected final Parameters config;
		
		protected final int from;
		protected final int to;
		protected final int start;
		protected final int end;

		protected ArrayList<Span>[] spansByEntry;

		public AbstractWork(Parameters config, int from, int to, int start, int end) {
			this.config = config;
			this.from = from;
			this.to = to;
			this.start = start;
			this.end = end;
		}

		public List<Span> getSpans(int i) {
			if (i < start || i >= end)
				throw new IndexOutOfBoundsException();
			
			return spansByEntry[i];
		}
		
		public Set<Match> split() throws UntokenizerException {
			return split0();
		}
		
		protected abstract void init();

		public Set<Match> split0() throws UntokenizerException {
			if (end <= start)
				return NO_MATCHES;
			
			init();
			
			List<Span> candidates = getFixedEntries();

			if (candidates.isEmpty()) {
				// squeezed pilcrow with many options
				if (end - start == 1 && "$¶".equals(cgKeys.get(start)) && getSpans(start).size() > 1) {
					for (Span span : getSpans(start)) {
						if (span.to - span.from == 1) {
							candidates.add(span);
							break;
						}
					}
					
					if (candidates.isEmpty()) {
						for (Span span : getSpans(start)) {
							if (span.to == span.from || text.charAt(span.from) == ' ') {
								candidates.add(span);
								break;
							}
						}
					}
				}
				
				HashMap<String, List<List<Span>>> spansByKey = new HashMap<String, List<List<Span>>>(end - start);
				for (int i = start; i < end; i++) {
					List<List<Span>> spanList = spansByKey.get(cgKeys.get(i));
					
					if (spanList == null) {
						spanList = new ArrayList<List<Span>>();
						spansByKey.put(cgKeys.get(i), spanList);
					}
					
					spanList.add(getSpans(i));
				}
				
				outer:
				for (Map.Entry<String, List<List<Span>>> spanListByKey : spansByKey.entrySet()) {
					List<List<Span>> spanList = spanListByKey.getValue();
					int multiplicity = spanList.size();
					
					if (multiplicity > 1) {
						for (List<Span> spans : spanList) {
							if (spans.size() != multiplicity)
								continue outer;
						}
					
						candidates.add(getSpans(spanList.get(0).get(0).entry).get(0));
						break;
					}
				}
			}

			if (!candidates.isEmpty())
				return fixSpan(candidates.get(candidates.size() / 2));
			
			if (config.next != null)
				return new MatchWork(config.next, from, to, start, end).split();
			
			throw new UntokenizerException(this);
		}

		private Set<Match> fixSpan(Span fixedSpan) throws UntokenizerException {
			Match fixedFullMatch = fixedSpan.getMatch();
			Set<Match> matches = new HashSet<Match>();
			matches.add(fixedFullMatch);
				
			int from = this.from;
			int start = this.start;
			
			for (Span span : fixedFullMatch.getSpans()) {
				matches.addAll(narrow(from, span.from, start, span.entry).split());
				from = span.to;
				start = span.entry + 1;
			}
			
			matches.addAll(narrow(from, to, start, end).split());
			
			return matches;
		}

		public AbstractWork narrow(int from, int to, int start, int end) {
			return new NarrowWork(config, from, to, start, end, this);
		}

		public List<Span> getFixedEntries() {
			ArrayList<Span> fixedEntries = new ArrayList<Span>();
			
			for (int i = start; i < end; i++) {
				if (getSpans(i).size() == 1)
					fixedEntries.add(getSpans(i).get(0));
			}
			
			return fixedEntries;
		}
		
	}
	
	public class MatchWork extends AbstractWork {
		public MatchWork(Parameters config, int from, int to, int start, int end) {
			super(config, from, to, start, end);
		}

		@SuppressWarnings("unchecked")
		public void init() {
			spansByEntry = new ArrayList[end];
			
			for (int i = start; i < end; i++) {
				spansByEntry[i] = new ArrayList<Span>();
			}

			final TextMatcher textMatcher = config.create(text);
			
			for (MatchStrategy strategy : config.strategies) {
				strategy.setData(textMatcher, cgKeys);
				Set<Match> matches = strategy.matchAll(from, to, start, end);

				for (Match match : matches) {
					for (Span span : match.getSpans()) {
						spansByEntry[span.entry].add(span);
					}
				}
			}

			for (int i = start; i < end; i++) {
				spansByEntry[i].trimToSize();
				Collections.sort(spansByEntry[i], new br.eti.rslemos.nlp.corpora.chave.parser.Span.SpanComparatorByOffsets());
			}
		}
	}
	
	public class NarrowWork extends AbstractWork {

		private AbstractWork parent;

		public NarrowWork(Parameters config, int from, int to, int start, int end, AbstractWork parent) {
			super(config, from, to, start, end);
			this.parent = parent;
		}

		@SuppressWarnings("unchecked")
		protected void init() {
			spansByEntry = new ArrayList[end];
			
			for (int i = start; i < end; i++) {
				spansByEntry[i] = new ArrayList<Span>(/*parent.spansByEntry[i].size()*/);
				for (Span span : parent.spansByEntry[i]) {
					if (span.from >= from && span.to <= to)
						spansByEntry[i].add(span);
				}
				spansByEntry[i].trimToSize();
			}
			
			parent = null;
		}
		
	}
	public Set<Match> untokenize(Parameters config, int from, int to, int start, int end) throws UntokenizerException {
		MatchWork work = new MatchWork(config, from, to, start, end);
		
		return work.split();
	}

	private static class Parameters {
		public final int threshold;
		public final boolean caseSensitive;
		public final boolean wordBoundaryCheck;
		public final Parameters next;
		public final MatchStrategy[] strategies;
		
		public Parameters(int threshold, boolean caseSensitive, boolean wordBoundaryCheck, MatchStrategy... strategies) {
			this(null, threshold, caseSensitive, wordBoundaryCheck, strategies);
		}
		
		public Parameters(Parameters next, int threshold, boolean caseSensitive, boolean wordBoundaryCheck, MatchStrategy... strategies) {
			this.threshold = threshold;
			this.caseSensitive = caseSensitive;
			this.wordBoundaryCheck = wordBoundaryCheck;
			this.next = next;
			this.strategies = strategies;
		}
		
		public TextMatcher create(String text) {
			if (threshold > 0)
				return new CachingTextMatcher(new DamerauLevenshteinTextMatcher(text, threshold, caseSensitive, wordBoundaryCheck));
			else
				return new CachingTextMatcher(new PlainTextMatcher(text, caseSensitive, wordBoundaryCheck));
		}
	}
}
