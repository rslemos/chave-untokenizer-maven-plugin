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

public class Untokenizer {

	private final MatchStrategy[] STRATEGIES = {
			new DirectMatchStrategy(),
			new ContractionMatchStrategy(),
			new EncliticMatchStrategy(),
			new NewLineMatchStrategy(),
		};

	private List<CGEntry> cg;
	private String text;
	private List<String> cgKeys;

	public Document createDocument(String text) {
		Document result = new DocumentImpl();
		result.setContent(new DocumentContentImpl(text));
		return result;
	}

	public void untokenize(Document document, List<CGEntry> cg) {
		this.cg = cg;

		AnnotationSet originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);

		text = document.getContent().toString();
		cgKeys = onlyKeys(cg);
		
		Set<Match> matches = new MatchWork(0, text.length(), 0, cgKeys.size()).untokenize();
		
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

	private class MatchWork {
		private final Parameters config = new Parameters(0, false, true);

		private ArrayList<Span>[] spansByEntry;

		private final int from;
		private final int to;
		
		private final int start;
		private final int end;

		@SuppressWarnings("unchecked")
		private MatchWork(int from, int to, int start, int end) {
			this.from = from;
			this.to = to;
			this.start = start;
			this.end = end;
			
			spansByEntry = new ArrayList[end];
			
			for (int i = start; i < end; i++) {
				spansByEntry[i] = new ArrayList<Span>();
			}
		}

		public Set<Match> untokenize() {

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
			
			return new NarrowWork(from, to, start, end).annotateAndSplit();
		}

		private class NarrowWork {
			private final int from;
			private final int to;
			private final int start;
			private final int end;

			public NarrowWork(int from, int to, int start, int end) {
				this.from = from;
				this.to = to;
				this.start = start;
				this.end = end;
			}

			private Set<Match> annotateAndSplit() {
				if (end <= start)
					return Collections.emptySet();
				
				Span fixedSpan = chooseFixedSpan();
				
				if (fixedSpan == null) {
					// squeezed pilcrow with many options
					if (end - start == 1 && "$¶".equals(cgKeys.get(start)) && validSpans(spansByEntry[start]).size() > 1) {
						for (Span span : validSpans(spansByEntry[start])) {
							if (span.to - span.from == 1) {
								fixedSpan = span;
								break;
							}
						}
						
						if (fixedSpan == null) {
							for (Span span : validSpans(spansByEntry[start])) {
								if (span.to == span.from || text.charAt(span.from) == ' ') {
									fixedSpan = span;
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
						
						spanList.add(validSpans(spansByEntry[i]));
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
						
							fixedSpan = validSpans(spansByEntry[spanList.get(0).get(0).entry]).get(0);
							break;
						}
					}
				}
				
				if (fixedSpan == null) {
//					for (int i = start; i < end; i++) {
//						processingResults[i].addAll(spansByEntry[i]);
//					}
					//System.err.printf("No fixed span in [%d, %d[\n", start, end);
					return Collections.emptySet();
				}
				
				Match fixedFullMatch = fixedSpan.getMatch();
				
				Set<Match> matches = new HashSet<Match>();
				matches.add(fixedFullMatch);
				
				int from = this.from;
				int start = this.start;
				
				for (Span span : fixedFullMatch.getSpans()) {
					matches.addAll(new NarrowWork(from, span.from, start, span.entry).splitAndRecurse());
					from = span.to;
					start = span.entry + 1;
				}
				
				matches.addAll(new NarrowWork(from, to, start, end).splitAndRecurse());
				
				return matches;
			}

			private Set<Match> splitAndRecurse() {
				return annotateAndSplit();
			}
			
			private Span chooseFixedSpan() {
				BitSet fixedEntries = getFixedEntries();
		
				int fixedEntry = chooseFixedEntry(fixedEntries);
				
				if (fixedEntry < 0)
					return null;
				else
					return validSpans(spansByEntry[fixedEntry]).get(0);
			}

			private BitSet getFixedEntries() {
				BitSet fixedEntries = new BitSet(spansByEntry.length);
				for (int i = start; i < end; i++) {
					fixedEntries.set(i, validSpans(spansByEntry[i]).size() == 1);
				}
				
				return fixedEntries;
			}

			private List<Span> validSpans(List<Span> spans) {
				ArrayList<Span> validSpans = new ArrayList<Span>(spans.size());
				
				for (Span span : spans) {
					if (span.from >= from && span.to <= to)
						validSpans.add(span);
				}
				
				validSpans.trimToSize();
				return validSpans;
			}
		}
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

	private static int chooseFixedEntry(BitSet fixedEntries) {
		int cardinality = fixedEntries.cardinality();
		
		if (cardinality == 1)
			return fixedEntries.nextSetBit(0);
		else if (cardinality == 0)
			return -1;
		else { // cardinality >= 2
			int median = cardinality/2;
			
			int entry = fixedEntries.nextSetBit(0);
			
			while (entry >= 0 && median > 0) {
				median--;
				entry = fixedEntries.nextSetBit(entry+1);
			}
			
			return entry;
		}
	}
}
