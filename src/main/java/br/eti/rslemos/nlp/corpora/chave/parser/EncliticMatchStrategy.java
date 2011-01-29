package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EncliticMatchStrategy implements MatchStrategy {

	private static final DirectMatchStrategy DM = new DirectMatchStrategy();

	public Set<Match> match(String text, List<String> cg) {
		Map<String, Set<Match>> cache = new LinkedHashMap<String, Set<Match>>();
		
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (int i = 0; i < cg.size() - 1; i++) {
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
					
					if (!cache.containsKey(toMatch)) {
						cache.put(toMatch, DM.matchKey(text, toMatch,
									span(0, toMatch.length() - key1.length(), 0),
									span(toMatch.length() - key1.length(), toMatch.length(), 1)
								));
					}
					
					for (Match match : cache.get(toMatch)) {
						result.add(match.adjust(0, i));
					}
				}
			}
		}
		
		return result;
	}

}
