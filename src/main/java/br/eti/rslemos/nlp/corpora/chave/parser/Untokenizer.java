package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.Document;
import gate.GateConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;


public class Untokenizer {
	private static final Formatter FORMATTER = new Formatter();

	private Handler out;
	
	public Untokenizer(Handler out) {
		this.out = out;
	}

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
				new SentenceMarkerMatchStrategy(),
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
				ArrayList<MatchResult> mementos = new ArrayList<MatchResult>(strategies.length);
				
				for (MatchStrategy strategy : strategies) {
					mementos.add(strategy.match(buffer.substring(k), onlyKeys(cg1.subList(i, cg1.size()))));
				}
				
				do {} while (mementos.remove(null));
				
				if (mementos.size() > 0) {
					int minSkip = Integer.MAX_VALUE;
					for (Iterator<MatchResult> iterator = mementos.iterator(); iterator.hasNext();) {
						MatchResult memento = iterator.next();
						minSkip = Math.min(minSkip, memento.getSkipLength());
						if (memento.getSkipLength() > 0)
							iterator.remove();
					}
					
					if (minSkip == 0) {
						MatchResult best = mementos.get(0);
						int bestLength = -1;
						for (MatchResult memento : mementos) {
							bestLength = best != null ? best.getMatchLength() : -1;
							if (memento.getMatchLength() > bestLength) {
								best = memento;
							}
						}
						if (best != null) {
							best.apply(buffer.substring(k), cg1.subList(i, cg1.size()), out);
							best.apply(document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME), k, cg1, i);
							k += best.getMatchLength();
							i += best.getConsume();
							continue outer;
						}
					} else {
						char[] toSkip = new char[minSkip];
						buffer.substring(k).getChars(0, toSkip.length, toSkip, 0);
						out.characters(toSkip);
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

	private static List<String> onlyKeys(List<CGEntry> cg1) {
		ArrayList<String> result = new ArrayList<String>(cg1.size());
		for (CGEntry entry : cg1) {
			result.add(entry.getKey());
		}
		
		return Collections.unmodifiableList(result);
	}
}
