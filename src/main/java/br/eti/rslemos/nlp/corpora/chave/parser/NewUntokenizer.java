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
import java.util.List;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;


public class NewUntokenizer {

	public Document createDocument(String text) {
		Document result = new DocumentImpl();
		result.setContent(new DocumentContentImpl(text));
		return result;
	}

	public void untokenize(Document document, List<CGEntry> cg) {
		final String text = document.getContent().toString();
		final List<String> cgKeys = onlyKeys(cg);
		
		AnnotationSet originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
		
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
		
		Span fixedSpan = chooseFixedSpan(spansByEntry);
		Match fixedFullMatch = fixedSpan.getMatch();
		
		try {
			fixedFullMatch.apply(originalMarkups, cg);
		} catch (InvalidOffsetException e) {
			throw new RuntimeException(e);
		}
	}

	private Span chooseFixedSpan(final List<Span>[] spansByEntry) {
		BitSet fixedEntries = new BitSet(spansByEntry.length);
		for (int i = 0; i < spansByEntry.length; i++) {
			fixedEntries.set(i, spansByEntry[i].size() == 1);
		}

		int fixedEntry = chooseFixedEntry(fixedEntries);
		
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
