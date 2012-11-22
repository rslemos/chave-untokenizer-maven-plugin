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
import java.util.Arrays;
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

	private final static MatchStrategy[] STRATEGIES = {
			new DirectMatchStrategy(),
			new ContractionMatchStrategy(),
			new EncliticMatchStrategy(),
			new NewLineMatchStrategy(),
		};

	private final Document document;
	private final String text;
	private final List<CGEntry> cg;
	private final List<String> cgKeys;
	
	private final Map<AbstractWork, Object> memoization = new HashMap<AbstractWork, Object>();
	private int miss, hit;

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
		System.out.println();
		Multiset<Set<Match>> allMatches = untokenize(config, 0, text.length(), 0, cgKeys.size());
		
		// pegando a mais comum
		int max = Integer.MIN_VALUE;
		Set<Match> matches = null;
		
		System.out.printf("\n%d unique results:\n", allMatches.entrySet().size());
		
		for (Entry<Set<Match>> entry : allMatches.entrySet()) {
			System.out.printf("+ %d copies\n", entry.getCount());
			if (entry.getCount() > max) {
				max = entry.getCount();
				matches = entry.getElement();
			}
		}
		
		System.out.printf("= %d total results\n", allMatches.size());
		System.out.printf("memoization: %d hit + %d miss (%f hit rate)\n", hit, miss, (float)hit / (float)(hit + miss));
		
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

//	StringBuilder indent = new StringBuilder();
	int level;

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
		
		public Multiset<Set<Match>> split() throws UntokenizerException {
			level++;
			System.out.printf("%d\r", level);
			System.out.flush();
//			indent.append("  ");
//			System.out.printf("%ssplit() ", indent);
			try {
				// memoization
				Object result;
				
				if (!memoization.containsKey(this)) {
					miss++;
//					System.out.println("miss");
					try {
						result = split0();
					} catch (UntokenizerException e) {
						result = e;
					}
					memoization.put(this, result);
				} else {
//					System.out.println("hit");
					hit++;
					result = memoization.get(this);
				}
				
				if (result instanceof UntokenizerException) {
//					System.out.printf("%sno results\n", indent);
					throw (UntokenizerException)result;
				} else {
//					System.out.printf("%sresults: %d/%d\n", indent, ((Multiset<Set<Match>>) result).entrySet().size(), ((Multiset<Set<Match>>) result).size());
//					System.out.printf("%smemoization: %d/%d\n", indent, ((Multiset<Set<Match>>) result).entrySet().size(), ((Multiset<Set<Match>>) result).size());
					return (Multiset<Set<Match>>) result;
				}
			} finally {
				level--;
//				indent.setLength(indent.length() - 2);
			}
		}
		
		protected abstract void init();

		public Multiset<Set<Match>> split0() throws UntokenizerException {
			init();
			
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
				Multiset<Set<Match>> partialResults = fixSpan(span);
				for (Entry<Set<Match>> entry : partialResults.entrySet()) {
					if (result.contains(entry.getElement())) {
						long count = (long)result.count(entry.getElement()) + (long)entry.getCount(); 
						result.setCount(entry.getElement(), (int)Math.min(count, Integer.MAX_VALUE - 1));
					} else
						result.add(entry.getElement(), entry.getCount());
					
				}
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
					result.add(matches, (int)Math.min((long)entryA.getCount() * (long)entryB.getCount(), Integer.MAX_VALUE - 1));
				}
			}
			
			return result;
		}

		public AbstractWork narrow(int from, int to, int start, int end) {
			return new NarrowWork(config, from, to, start, end, this);
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
		
		@Override
		public int hashCode() {
			int hashCode = 1;
			
			hashCode += from + to + start + end;
			hashCode *= 2;
			hashCode += config.hashCode();
//			hashCode *= 3;
//			hashCode += Arrays.deepHashCode(spansByEntry);
			
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			
			if (obj == this)
				return true;
			
			if (!(obj instanceof NarrowWork))
				return false;
			
			NarrowWork other = (NarrowWork) obj;
			
			
			return from == other.from &&
					to == other.to &&
					start == other.start &&
					end == other.end &&
					config.equals(other.config);
//					Arrays.deepEquals(spansByEntry, other.spansByEntry);
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
			
			for (MatchStrategy strategy : config.getStrategies()) {
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
	public Multiset<Set<Match>> untokenize(Parameters config, int from, int to, int start, int end) throws UntokenizerException {
		MatchWork work = new MatchWork(config, from, to, start, end);
		
		return work.split();
	}

	private static class Parameters {
		public final int threshold;
		public final boolean caseSensitive;
		public final boolean wordBoundaryCheck;
		
		private final transient int hashCode;
		
		public Parameters(int threshold, boolean caseSensitive, boolean wordBoundaryCheck) {
			this.threshold = threshold;
			this.caseSensitive = caseSensitive;
			this.wordBoundaryCheck = wordBoundaryCheck;
			
			int hashCode = 0;
			
			hashCode += caseSensitive ? 1 : 0;
			hashCode += wordBoundaryCheck ? 2 : 0;
			hashCode *= 17;
			hashCode += threshold;
			hashCode *= 13;
			hashCode += Arrays.deepHashCode(STRATEGIES);
			
			this.hashCode = hashCode;
		}
		
		public MatchStrategy[] getStrategies() {
			return STRATEGIES;
		}

		public TextMatcher create(String text) {
			if (threshold > 0)
				return new CachingTextMatcher(new DamerauLevenshteinTextMatcher(text, threshold, caseSensitive, wordBoundaryCheck));
			else
				return new CachingTextMatcher(new PlainTextMatcher(text, caseSensitive, wordBoundaryCheck));
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			
			if (obj == null)
				return false;
			
			if (!(obj instanceof Parameters))
				return false;
			
			Parameters other = (Parameters)obj;
			
			return threshold == other.threshold &&
					caseSensitive == other.caseSensitive &&
					wordBoundaryCheck == other.wordBoundaryCheck &&
					Arrays.deepEquals(STRATEGIES, STRATEGIES);
		}
		
		
	}
}
