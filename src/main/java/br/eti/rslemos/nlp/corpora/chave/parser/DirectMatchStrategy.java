package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class DirectMatchStrategy extends AbstractMatchStrategy {
	
	public Set<Match> matchAll(int start, int end) {
		Set<Match> matches = new LinkedHashSet<Match>();

		for (int i = start; i < end; i++) {
			addAllMatches(i, matches);
		}
		
		return matches;
	}

	private void addAllMatches(int i, Set<Match> matches) {
		for (Match match : matchKey(getKey(i))) {
			matches.add(match.adjust(0, i));
		}
	}

	private Set<Match> matchKey(String key0) {
		return matcher.matchKey(key0, span(0, key0.length(), 0));
	}

	private String getKey(int i) {
		String key0 = getKey(cg.get(i));
		
		// TODO: retirar este bacalho quando o DL for religado;
		// este bacalho resolve o caso "sra." + "$."; o DL conseguiria capturar este caso
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
		
		if ("$--".equals(currentKey))
			currentKey = "-";
		
		if (currentKey.startsWith("$"))
			currentKey = currentKey.substring(1);

		
		return currentKey;
	}
}