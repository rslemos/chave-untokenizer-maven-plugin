package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.Document;
import gate.GateConstants;

import java.io.IOException;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;



public class Untokenizer {
	private static final Formatter FORMATTER = new Formatter();

	public Document parse(List<CGEntry> cg, Document document) throws IOException, ParserException {
		MatchStrategy[] strategies2 = {
				new DirectMatchStrategy(),
				new EncliticMatchStrategy(),
				new ContractionDeMatchStrategy(),
				new ContractionEmMatchStrategy(),
				new ContractionAMatchStrategy(),
				new ContractionPorMatchStrategy(),
				new ContractionComMatchStrategy(),
				new QuotesMatchStrategy(),
				new WhitespaceMatchStrategy(),
			};

		MatchStrategy[] strategies = {
				new NewLineMatchStrategy(),
				//new DamerauLevenshteinMatchStrategy(1),
			};

		List<CGEntry> cg1 = Collections.unmodifiableList(cg);
		int i = 0;
		
		final String buffer = document.getContent().toString();
		int k = 0;

		Set<Match> mementos2 = new LinkedHashSet<Match>();
		for (MatchStrategy strategy : strategies2) {
			strategy.setData(new DamerauLevenshteinTextMatcher(buffer), CGEntry.onlyKeys(cg1));
			mementos2.addAll(strategy.matchAll());
		}

outer:
		while (i < cg1.size()) {
			try {
				Set<Match> mementos = new LinkedHashSet<Match>(strategies.length);

				String substring = buffer.substring(k);
				List<String> onlyKeys = CGEntry.onlyKeys(cg1.subList(i, cg1.size()));
				
				for (MatchStrategy strategy : strategies) {
					strategy.setData(new DamerauLevenshteinTextMatcher(substring), onlyKeys);
					mementos.addAll(adjust(keepFor(strategy.matchAll(), k, 0), k, i));
				}

				mementos.addAll(keepFor(mementos2, k, i));
				
				if (mementos.size() > 0) {
					int nextChar = Integer.MAX_VALUE;
					for (Iterator<Match> iterator = mementos.iterator(); iterator.hasNext();) {
						Match memento = iterator.next();
						if (memento.getSkipLength() >= k)
							nextChar = Math.min(nextChar, memento.getSkipLength());
						
						if (memento.getSkipLength() != k)
							iterator.remove();
					}
					
					if (nextChar == k) {
						Match best = null;
						int bestLength = -1;
						int bestEntries = -1;
						for (Match memento : mementos) {
							if (memento.getMatchLength() > bestLength || (memento.getMatchLength() == bestLength && memento.getSpans().size() > bestEntries)) {
								best = memento;
								bestLength = best.getMatchLength();
								bestEntries = best.getSpans().size();
							}
						}
						if (best != null) {
							best.apply(document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME), cg1);
							k += best.getMatchLength();
							i += best.getConsume();
							continue outer;
						}
					} else if (nextChar < Integer.MAX_VALUE){
						k = nextChar;
						continue;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			FORMATTER.format("%d-th entry: %s; Dump remaining buffer: %s\n", i, cg1.get(i).getKey(), buffer.substring(k));
			throw new ParserException(FORMATTER.out().toString());
		}
		
		return document;
	}

	private Set<Match> adjust(Set<Match> keepFor, int k, int i) {
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (Match match : keepFor) {
			result.add(match.adjust(k, i));
		}
		
		return result;
	}

	private Set<Match> keepFor(Set<Match> matches, int k, int i) {
		matches = new LinkedHashSet<Match>(matches);
		for (Iterator<Match> iterator = matches.iterator(); iterator.hasNext();) {
			Match match = iterator.next();
			int minEntry = Integer.MAX_VALUE;
			for (Span span : match.getSpans()) {
				minEntry = Math.min(minEntry, span.entry);
			}
			
			if (minEntry != i && match.getSpans().size() > 0)
				iterator.remove();
		}
		
		return matches;
	}
}
