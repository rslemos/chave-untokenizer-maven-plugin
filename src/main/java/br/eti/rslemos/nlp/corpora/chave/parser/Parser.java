package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Parser {
	private static final Formatter FORMATTER = new Formatter();

	private Handler out;
	
	public Parser(Handler out) {
		this.out = out;
	}

	public void parse(List<CGEntry> cg, Document document) throws IOException, ParserException {
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

		parser(strategies, cg, document);
	}

	private void parser(MatchStrategy[] strategies, List<CGEntry> cg, Document document) throws IOException, ParserException {
		LinkedList<CGEntry> cg1 = new LinkedList<CGEntry>(cg);
		
		String buffer = document.getContent().toString();

outer:
		while (!cg1.isEmpty()) {
			try {
				ArrayList<MatchResult> mementos = new ArrayList<MatchResult>(strategies.length);
				
				for (MatchStrategy strategy : strategies) {
					mementos.add(strategy.match(buffer.toString(), onlyKeys(cg1)));
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
							int k = best.apply(buffer, cg1, out);
							buffer = buffer.substring(k);
							continue outer;
						}
					} else {
						char[] toSkip = new char[minSkip];
						buffer.getChars(0, toSkip.length, toSkip, 0);
						out.characters(toSkip);
						buffer = buffer.substring(toSkip.length);
						continue;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			FORMATTER.format("%d-th entry: %s; Dump remaining buffer: %s\n", cg.size() - cg1.size(), cg1.get(0).getKey(), buffer);
			throw new ParserException(FORMATTER.out().toString());
		}
	}

	private static List<String> onlyKeys(LinkedList<CGEntry> cg1) {
		ArrayList<String> result = new ArrayList<String>(cg1.size());
		for (CGEntry entry : cg1) {
			result.add(entry.getKey());
		}
		
		return result;
	}
}
