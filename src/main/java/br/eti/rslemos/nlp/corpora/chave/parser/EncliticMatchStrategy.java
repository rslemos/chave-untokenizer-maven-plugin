package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class EncliticMatchStrategy extends AbstractMatchStrategy {

	public Set<Match> matchAll(int from, int to, int start, int end) {
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (int i = start; i < end - 1; i++) {
			String key0 = cg.get(i).toLowerCase();
			String key1 = cg.get(i + 1).toLowerCase();
			
			if (Arrays.asList("la", "las", "lo", "los").contains(key1)) {
				String toMatch = null;
				
				if (key0.endsWith("ar-"))
					toMatch = key0.replaceAll("ar-$", "á-");
		
				if (key0.endsWith("er-"))
					toMatch = key0.replaceAll("er-$", "ê-");
				
				if (key0.endsWith("ir-"))
					toMatch = key0.replaceAll("ir-$", "i-");
				
				if (key0.endsWith("por-"))
					toMatch = key0.replaceAll("por-$", "po-");
				
				if (key0.endsWith("pôr-"))
					toMatch = key0.replaceAll("pôr-$", "pô-");
				
				if (toMatch != null) {
					toMatch += key1;
					
					for (Match match : matchAndUpdateCache(from, to, toMatch, key1)) {
						result.add(match.adjust(0, i, this.getClass()));
					}
				}
			}
		}
		
		return result;
	}

	private Set<Match> matchAndUpdateCache(int from, int to, String toMatch, String key1) {
		return matcher.matchKey(from, to, toMatch,
					span(0, toMatch.length() - key1.length(), 0),
					span(toMatch.length() - key1.length(), toMatch.length(), 1)
				);
	}

}
