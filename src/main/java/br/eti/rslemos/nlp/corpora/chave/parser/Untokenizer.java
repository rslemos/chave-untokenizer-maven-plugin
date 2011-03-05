package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.Document;
import gate.GateConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;


public class Untokenizer {
	private static final Formatter FORMATTER = new Formatter();

	public Document parse(List<CGEntry> cg, Document document) throws IOException, ParserException {
		MatchStrategy[] strategies = {
				new DirectMatchStrategy(),
				new EncliticMatchStrategy(),
				new ContractionDeMatchStrategy(),
				new ContractionEmMatchStrategy(),
				new ContractionAMatchStrategy(),
				new ContractionPorMatchStrategy(),
				new ContractionComMatchStrategy(),
				new QuotesMatchStrategy(),
				new WhitespaceMatchStrategy(),
				new NewLineMatchStrategy(),
				new DamerauLevenshteinMatchStrategy(1),
			};

		return parser(strategies, cg, document);
	}

	private Document parser(MatchStrategy[] strategies, List<CGEntry> cg, Document document) throws IOException, ParserException {
		List<CGEntry> cg1 = Collections.unmodifiableList(cg);
		int i = 0;
		
		final String buffer = document.getContent().toString();
		int k = 0;

outer:
		while (i < cg1.size()) {
			try {
				Set<Match> mementos = new LinkedHashSet<Match>(strategies.length);

				String substring = buffer.substring(k);
				List<String> onlyKeys = onlyKeys(cg1.subList(i, cg1.size()));
				
				for (MatchStrategy strategy : strategies) {
					strategy.setText(substring);
					strategy.setCG(onlyKeys);
					mementos.addAll(keepFor(strategy.match(), k, 0));
				}
				
				if (mementos.size() > 0) {
					int minSkip = Integer.MAX_VALUE;
					for (Iterator<Match> iterator = mementos.iterator(); iterator.hasNext();) {
						Match memento = iterator.next();
						minSkip = Math.min(minSkip, memento.getSkipLength());
						if (memento.getSkipLength() > 0)
							iterator.remove();
					}
					
					if (minSkip == 0) {
						Match best = null;
						int bestLength = -1;
						for (Match memento : mementos) {
							if (memento.getMatchLength() > bestLength) {
								best = memento;
								bestLength = best.getMatchLength();
							}
						}
						if (best != null) {
							best.adjust(k, i).apply(document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME), cg1);
							k += best.getMatchLength();
							i += best.getConsume();
							continue outer;
						}
					} else {
						k += minSkip;
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

	public static List<String> onlyKeys(List<CGEntry> cg1) {
		ArrayList<String> result = new ArrayList<String>(cg1.size());
		for (CGEntry entry : cg1) {
			result.add(entry.getKey());
		}
		
		return Collections.unmodifiableList(result);
	}
}
