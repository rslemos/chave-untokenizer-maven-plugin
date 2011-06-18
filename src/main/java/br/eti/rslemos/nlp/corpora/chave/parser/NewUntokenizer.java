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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;


public class NewUntokenizer {

	private AnnotationSet originalMarkups;
	private List<CGEntry> cg;
	private Document document;
	private String text;
	private List<String> cgKeys;

	public Document createDocument(String text) {
		Document result = new DocumentImpl();
		result.setContent(new DocumentContentImpl(text));
		return result;
	}

	public void untokenize(Document document, List<CGEntry> cg) {
		this.document = document;
		this.cg = cg;

		originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);

		text = document.getContent().toString();
		cgKeys = onlyKeys(cg);
		
		untokenize();
	}

	private void untokenize() {
		final MatchStrategy[] strategies = {
			new DirectMatchStrategy(),
			new ContractionAMatchStrategy(),
			new ContractionComMatchStrategy(),
			new ContractionDeMatchStrategy(),
			new ContractionEmMatchStrategy(),
			new ContractionParaMatchStrategy(),
			new ContractionPorMatchStrategy(),
			new EncliticMatchStrategy(),
		};
		
		@SuppressWarnings("unchecked")
		final List<Span>[] spansByEntry = new List[cgKeys.size()];
		
		for (int i = 0; i < spansByEntry.length; i++) {
			spansByEntry[i] = new ArrayList<Span>();
		}
		
		final TextMatcher textMatcher = new DamerauLevenshteinTextMatcher(text, 0);
		
		for (MatchStrategy strategy : strategies) {
			strategy.setData(textMatcher, cgKeys);
			Set<Match> matches = strategy.matchAll();
			
			for (Match match : matches) {
				for (Span span : match.getSpans()) {
					spansByEntry[span.entry].add(span);
				}
			}
		}
		
		int start = 0;
		int end = spansByEntry.length;
		
		annotateAndSplit(spansByEntry, start, end);
	}

	private void annotateAndSplit(List<Span>[] spansByEntry, int start, int end) {
		if (end <= start)
			return;
		
		Span fixedSpan = chooseFixedSpan(spansByEntry, start, end);
		if (fixedSpan == null)
			return;
		
		Match fixedFullMatch = fixedSpan.getMatch();
		
		try {
			fixedFullMatch.apply(originalMarkups, cg);
		} catch (InvalidOffsetException e) {
			throw new RuntimeException(e);
		}
		
		Span[] spans = fixedFullMatch.getSpans().toArray(new Span[fixedFullMatch.getConsume()]);
		
		int from = 0;
		
		for (int i = 0; i < spans.length; i++) {
			splitAndRecurse(spansByEntry, start, spans[i].entry, from, spans[i].from);
			from = spans[i].to;
			start = spans[i].entry + 1;
		}
		splitAndRecurse(spansByEntry, start, end, from, text.length());
	}

	private void splitAndRecurse(List<Span>[] spansByEntry, int startEntry, int endEntry, int from, int to) {
		for (int i = startEntry; i < endEntry; i++) {
			for (Iterator<Span> iterator = spansByEntry[i].iterator(); iterator.hasNext();) {
				Span span = iterator.next();
				if (span.to > to || span.from < from)
					iterator.remove();
			}
		}
		
		annotateAndSplit(spansByEntry, startEntry, endEntry);
	}

	private Span chooseFixedSpan(List<Span>[] spansByEntry, int start, int end) {
		BitSet fixedEntries = new BitSet(spansByEntry.length);
		for (int i = start; i < end; i++) {
			fixedEntries.set(i, spansByEntry[i].size() == 1);
		}

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

}
