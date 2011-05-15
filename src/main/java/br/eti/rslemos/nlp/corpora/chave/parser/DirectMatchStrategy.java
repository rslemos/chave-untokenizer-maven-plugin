package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectMatchStrategy extends AbstractMatchStrategy {
	
	private Map<String, Set<Match>> cache;

	@Override
	public void setData(String text, List<String> cg) {
		super.setData(text, cg);
		cache = new LinkedHashMap<String, Set<Match>>();
	}

	public Set<Match> matchAll() {
		Set<Match> matches = new LinkedHashSet<Match>();

		for (int i = 0; i < cg.size(); i++) {
			addAllMatches(i, matches);
		}
		
		return matches;
	}

	private void addAllMatches(int i, Set<Match> matches) {
		for (Match match : matchAndUpdateCache(getKey(i))) {
			matches.add(match.adjust(0, i));
		}
	}

	private Set<Match> matchAndUpdateCache(String key0) {
		if (!cache.containsKey(key0)){
			cache.put(key0, matcher.matchKey(key0, span(0, key0.length(), 0)));
		}
		
		return cache.get(key0);
	}

	private String getKey(int i) {
		String key0 = getKey(cg.get(i));
		
		if (cg.size() > i + 1) {
			String key1 = getKey(cg.get(i + 1));
			
			if (Arrays.binarySearch(new String[] { ",", "-", ".", ":", ";" }, key1) >= 0) {
				if (key0.endsWith(key1)) {
					key0 = key0.substring(0, key0.length() - key1.length());
				}
			}
		}
		
		return key0;
	}


	private static String getKey(String currentKey) {
		currentKey = currentKey.split(" ")[0];
		
		if (currentKey.startsWith("$"))
			currentKey = currentKey.substring(1);
		
		return currentKey;
	}
}