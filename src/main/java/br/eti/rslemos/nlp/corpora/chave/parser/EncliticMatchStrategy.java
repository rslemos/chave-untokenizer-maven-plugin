package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EncliticMatchStrategy extends AbstractMatchStrategy {

	private Map<String, Set<Match>> cache;

	@Override
	public void setData(TextMatcher matcher, List<String> cg) {
		super.setData(matcher, cg);
		cache = new LinkedHashMap<String, Set<Match>>();
	}

	public Set<Match> matchAll() {
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
					
					for (Match match : matchAndUpdateCache(toMatch, key1)) {
						result.add(match.adjust(0, i));
					}
				}
			}
		}
		
		return result;
	}

	private Set<Match> matchAndUpdateCache(String toMatch, String key1) {
		if (!cache.containsKey(toMatch)) {
			cache.put(toMatch, matcher.matchKey(toMatch,
						span(0, toMatch.length() - key1.length(), 0),
						span(toMatch.length() - key1.length(), toMatch.length(), 1)
					));
		}
		
		return cache.get(toMatch);
	}

}
