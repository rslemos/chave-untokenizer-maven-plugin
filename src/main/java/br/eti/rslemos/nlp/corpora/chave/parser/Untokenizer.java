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
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

public class Untokenizer {

	private static final Set<Match> NO_MATCHES = Collections.emptySet();

	private final MatchStrategy[] STRATEGIES = {
			new DirectMatchStrategy(),
			new ContractionMatchStrategy(),
			new EncliticMatchStrategy(),
			new NewLineMatchStrategy(),
		};

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
		
		Parameters config = new Parameters(0, false, true);
		Multiset<Set<Match>> allMatches = untokenize(config, 0, text.length(), 0, cgKeys.size());
		
		// pegando a mais comum
		int max = Integer.MIN_VALUE;
		Set<Match> matches = null;
		
		for (Entry<Set<Match>> entry : allMatches.entrySet()) {
			if (entry.getCount() > max) {
				max = entry.getCount();
				matches = entry.getElement();
			}
		}
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

	public class NarrowWork { 
		private final ArrayList<Span>[] spansByEntry;
		
		private final int from;
		private final int to;
		private final int start;
		private final int end;

		public NarrowWork(int from, int to, int start, int end, ArrayList<Span>[] spansByEntry) {
			this.from = from;
			this.to = to;
			this.start = start;
			this.end = end;
			this.spansByEntry = spansByEntry;
		}

		@SuppressWarnings("unchecked")
		public NarrowWork(Parameters config, int from, int to, int start, int end) {
			this(from, to, start, end, new ArrayList[end]);
			
			for (int i = start; i < end; i++) {
				spansByEntry[i] = new ArrayList<Span>();
			}

			final TextMatcher textMatcher = config.create(text);
			
			for (MatchStrategy strategy : STRATEGIES) {
				strategy.setData(textMatcher, cgKeys);
				Set<Match> matches = strategy.matchAll(from, to);
				
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

		public List<Span> getSpans(int i) {
			if (i < start || i >= end)
				throw new IndexOutOfBoundsException();
			
			return spansByEntry[i];
		}
		
		public Multiset<Set<Match>> split() throws UntokenizerException {
			if (end <= start)
				return ImmutableMultiset.of(NO_MATCHES);
			
			List<Span> fixedSpans = getFixedSpans();

			if (fixedSpans.isEmpty()) {
				// squeezed pilcrow with many options
				if (end - start == 1 && "$¶".equals(cgKeys.get(start)) && getSpans(start).size() > 1) {
					for (Span span : getSpans(start)) {
						if (span.to - span.from == 1) {
							fixedSpans.add(span);
							break;
						}
					}
					
					if (fixedSpans.isEmpty()) {
						for (Span span : getSpans(start)) {
							if (span.to == span.from || text.charAt(span.from) == ' ') {
								fixedSpans.add(span);
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
					
						fixedSpans.add(getSpans(spanList.get(0).get(0).entry).get(0));
						break;
					}
				}
			}

			if (fixedSpans.isEmpty())
				throw new UntokenizerException(this);

			Multiset<Set<Match>> result = HashMultiset.create();
			
			for (Span span : fixedSpans) {
				result.addAll(fixSpan(span));
			}
			
			return result;
		}

		private Multiset<Set<Match>> fixSpan(Span fixedSpan) throws UntokenizerException {
			Multiset<Set<Match>> result = HashMultiset.create();
			
			Match fixedFullMatch = fixedSpan.getMatch();
			
			{
				Set<Match> matches = new HashSet<Match>();
				matches.add(fixedFullMatch);
				
				result.add(matches);
			}
			
			int from = this.from;
			int start = this.start;
			
			for (Span span : fixedFullMatch.getSpans()) {
				result = cartesianProduct(result, narrow(from, span.from, start, span.entry).split());
				from = span.to;
				start = span.entry + 1;
			}
			
			result = cartesianProduct(result, narrow(from, to, start, end).split());
			
			return result;
		}

		private Multiset<Set<Match>> cartesianProduct(Multiset<Set<Match>> A, Multiset<Set<Match>> B) {
			Multiset<Set<Match>> result = HashMultiset.create();
			
			for (Entry<Set<Match>> entryA : A.entrySet()) {
				for (Entry<Set<Match>> entryB : B.entrySet()) {
					Set<Match> matches = new HashSet<Match>(entryA.getElement());
					matches.addAll(entryB.getElement());
					result.add(matches, entryA.getCount() * entryB.getCount());
				}
			}
			
			return result;
		}

		public NarrowWork narrow(int from, int to, int start, int end) {
			@SuppressWarnings("unchecked")
			ArrayList<Span>[] validSpans = new ArrayList[end];
			
			for (int i = start; i < end; i++) {
				validSpans[i] = new ArrayList<Span>(this.spansByEntry[i].size());
				for (Span span : this.spansByEntry[i]) {
					if (span.from >= from && span.to <= to)
						validSpans[i].add(span);
				}
				validSpans[i].trimToSize();
			}
			
			return new NarrowWork(from, to, start, end, validSpans);
		}

		public List<Span> getFixedSpans() {
			ArrayList<Span> fixedSpans = new ArrayList<Span>();
			
			BitSet fixedEntries = getFixedEntries0();
	
			for (int i = fixedEntries.nextSetBit(0); i >= 0; i = fixedEntries.nextSetBit(i+1)) {
				fixedSpans.add(getSpans(i).get(0));
			}
			
			return fixedSpans;
		}

		private BitSet getFixedEntries0() {
			BitSet fixedEntries = new BitSet(spansByEntry.length);
			for (int i = start; i < end; i++) {
				fixedEntries.set(i, getSpans(i).size() == 1);
			}
			
			return fixedEntries;
		}
	}
	
	public Multiset<Set<Match>> untokenize(Parameters config, int from, int to, int start, int end) throws UntokenizerException {
		NarrowWork work = new NarrowWork(config, from, to, start, end);
		
		return work.split();
	}

	private static class Parameters {
		public int threshold;
		public boolean caseSensitive;
		public boolean wordBoundaryCheck;
		
		public Parameters(int threshold, boolean caseSensitive, boolean wordBoundaryCheck) {
			this.threshold = threshold;
			this.caseSensitive = caseSensitive;
			this.wordBoundaryCheck = wordBoundaryCheck;
		}
		
		public TextMatcher create(String text) {
			if (threshold > 0)
				return new CachingTextMatcher(new DamerauLevenshteinTextMatcher(text, threshold, caseSensitive, wordBoundaryCheck));
			else
				return new CachingTextMatcher(new PlainTextMatcher(text, caseSensitive, wordBoundaryCheck));
		}
	}
}
