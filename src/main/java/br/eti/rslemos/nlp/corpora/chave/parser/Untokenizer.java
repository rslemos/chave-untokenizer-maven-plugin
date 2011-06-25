package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.CGEntry.onlyKeys;
import gate.AnnotationSet;
import gate.Document;
import gate.GateConstants;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.util.InvalidOffsetException;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.DamerauLevenshteinTextMatcher.Parameters;
import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;


public class Untokenizer {

	private final MatchStrategy[] STRATEGIES = {
			new DirectMatchStrategy(),
			new ContractionAMatchStrategy(),
			new ContractionComMatchStrategy(),
			new ContractionDeMatchStrategy(),
			new ContractionEmMatchStrategy(),
			new ContractionParaMatchStrategy(),
			new ContractionPorMatchStrategy(),
			new EncliticMatchStrategy(),
			new NewLineMatchStrategy(),
		};

	private AnnotationSet originalMarkups;
	private List<CGEntry> cg;
	private String text;
	private List<String> cgKeys;

	private List<Span>[] spansByEntry;

	private List<Span>[] processingResults;

	public Document createDocument(String text) {
		Document result = new DocumentImpl();
		result.setContent(new DocumentContentImpl(text));
		return result;
	}

	@SuppressWarnings("unchecked")
	public void untokenize(Document document, List<CGEntry> cg) {
		this.cg = cg;

		originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);

		text = document.getContent().toString();
		cgKeys = onlyKeys(cg);
		
		spansByEntry = new List[cgKeys.size()];
		processingResults = new List[cgKeys.size()];
		
		for (int i = 0; i < spansByEntry.length; i++) {
			spansByEntry[i] = new ArrayList<Span>();
			processingResults[i] = new ArrayList<Span>();
		}
		
		
		untokenize();
	}

	private void untokenize() {
		final TextMatcher textMatcher = new Parameters(0, false, true).create(text);
		
		for (MatchStrategy strategy : STRATEGIES) {
			strategy.setData(textMatcher, cgKeys);
			Set<Match> matches = strategy.matchAll();
			
			for (Match match : matches) {
				for (Span span : match.getSpans()) {
					spansByEntry[span.entry].add(span);
				}
			}
		}
		
		annotateAndSplit(0, spansByEntry.length);
	}

	private void annotateAndSplit(int start, int end) {
		if (end <= start)
			return;
		
		Span fixedSpan = chooseFixedSpan(start, end);
		
		if (fixedSpan == null) {
			// squeezed pilcrow with many options
			if (end - start == 1 && "$¶".equals(cgKeys.get(start)) && spansByEntry[start].size() > 1) {
				for (Span span : spansByEntry[start]) {
					if (span.to - span.from == 1) {
						fixedSpan = span;
						break;
					}
				}
				
				if (fixedSpan == null) {
					Collections.sort(spansByEntry[start], new br.eti.rslemos.nlp.corpora.chave.parser.Span.SpanComparatorByOffsets());
					for (Span span : spansByEntry[start]) {
						if (text.charAt(span.from) == ' ') {
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
				
				spanList.add(spansByEntry[i]);
			}
			
			outer:
			for (Map.Entry<String, List<List<Span>>> spanListByKey : spansByKey.entrySet()) {
				List<List<Span>> spanList = spanListByKey.getValue();
				int multiplicity = spanList.size();
				
				if (multiplicity > 1) {
					int[] entries = new int[multiplicity];

					int i = 0;
					for (List<Span> spans : spanList) {
						if (spans.size() != multiplicity)
							continue outer;

						entries[i++] = spans.get(0).entry;
					}
				
					for (i = 0; i < entries.length; i++) {
						int entry = entries[i];
						
						Collections.sort(spansByEntry[entry], new br.eti.rslemos.nlp.corpora.chave.parser.Span.SpanComparatorByOffsets());
						
						Span theSpan = spansByEntry[entry].get(i);
						spansByEntry[entry] = new ArrayList<Match.Span>(1);
						spansByEntry[entry].add(theSpan);
					}
					
					annotateAndSplit(start, end);
					
					return;
				}
			}
		}
		
		if (fixedSpan == null) {
			for (int i = start; i < end; i++) {
				processingResults[i].addAll(spansByEntry[i]);
			}
			//System.err.printf("No fixed span in [%d, %d[\n", start, end);
			return;
		}
		
		Match fixedFullMatch = fixedSpan.getMatch();
		
		try {
			fixedFullMatch.apply(originalMarkups, cg);
		} catch (InvalidOffsetException e) {
			throw new RuntimeException(e);
		}
		
		Span[] spans = fixedFullMatch.getSpans().toArray(new Span[fixedFullMatch.getConsume()]);
		
		int from = 0;
		
		for (int i = 0; i < spans.length; i++) {
			splitAndRecurse(start, spans[i].entry, from, spans[i].from);
			from = spans[i].to;
			start = spans[i].entry + 1;
			
			// garante que a tabela vai possuir apenas o span que já foi commitado
			// nos casos normais não faz diferença, mas nas contrações, garante que todos
			// os spans do conjunto fiquem com contagem 1
			spansByEntry[spans[i].entry].clear();
			spansByEntry[spans[i].entry].add(spans[i]);
			
			processingResults[spans[i].entry].add(spans[i]);
		}
		
		splitAndRecurse(start, end, from, text.length());
	}

	private void splitAndRecurse(int startEntry, int endEntry, int from, int to) {
		for (int i = startEntry; i < endEntry; i++) {
			for (Iterator<Span> iterator = spansByEntry[i].iterator(); iterator.hasNext();) {
				Span span = iterator.next();
				if (span.to > to || span.from < from)
					iterator.remove();
			}
		}
		
		annotateAndSplit(startEntry, endEntry);
	}

	private Span chooseFixedSpan(int start, int end) {
		BitSet fixedEntries = getFixedEntries(start, end);

		int fixedEntry = chooseFixedEntry(fixedEntries);
		
		if (fixedEntry < 0)
			return null;
		else
			return spansByEntry[fixedEntry].get(0);
	}

	private int chooseFixedEntry(BitSet fixedEntries) {
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

	private BitSet getFixedEntries(int start, int end) {
		return getFixedEntries(spansByEntry, start, end);
	}

	public static BitSet getFixedEntries(List<Span>[] spansByEntry, int start, int end) {
		BitSet fixedEntries = new BitSet(spansByEntry.length);
		for (int i = start; i < end; i++) {
			fixedEntries.set(i, spansByEntry[i].size() == 1);
		}
		
		return fixedEntries;
	}

	public List<Span>[] getProcessingResults() {
		return processingResults;
	}

}
