package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.Arrays;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;

public class DamerauLevenshteinMatchStrategy extends OldStyleMatchStrategy {

	private final int maxDistance;

	public DamerauLevenshteinMatchStrategy(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public Match match0() {
		String key0 = cg.get(0);

		if (cg.size() > 1) {
			String key1 = cg.get(1);
			if (key1.startsWith("$"))
				key1 = key1.substring(1);
			
			if (Arrays.binarySearch(new String[] { ",", "-", ".", ":", ";" }, key1) >= 0) {
				if (key0.endsWith(key1)) {
					key0 = key0.substring(0, key0.length() - key1.length());
				}
			}
		}

		char[] cs = new char[key0.length()];
		try {
			((DamerauLevenshteinTextMatcher)matcher).getChars(cs);
		} catch (StringIndexOutOfBoundsException e) {
			return null;
		}
		
		String[] slices = new String(cs).split("\\p{Space}");
		if (slices.length == 0)
			return null;
		
		char[] cs1 = slices[0].toCharArray();
		if (cs1.length < 2)
			return null;
		
		if (levenshteinDistance(key0.toCharArray(), cs1) <= maxDistance) {
			return match(0, cs1.length, span(0, cs1.length, 0));
		} else
			return null;
	}

	private static int levenshteinDistance(char[] key, char[] text) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key);
		
		int distance = c.getDistance();
		
		for(int j=1; j<=text.length; j++) {
			distance = c.append(text[j-1]);
		}
		
		return distance;
	}
}
