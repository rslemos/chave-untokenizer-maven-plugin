package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import java.util.Arrays;

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
			matcher.getChars(cs);
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
			return new Match(0, cs1.length, span(0, cs1.length, 0));
		} else
			return null;
	}

	private static int levenshteinDistance(char[] key, char[] text) {
		class AdaptativeDamerauLevenshteinDistance {
			private int d0[] = null;
			private int d1[] = null;
			private int d2[] = null;
			
			private int j = 1;
			private char c0;
			
			private final char[] key;
			
			private AdaptativeDamerauLevenshteinDistance(char[] key) {
				this.key = key;
				d2 = new int[key.length + 1];
				
				for(int i=0; i<=key.length; i++) {
					d2[i] = i;
				}
			}
			
			int appendChar(char c1) {
				d0 = d1;
				d1 = d2;
				d2 = new int[key.length + 1];
				
				d2[0] = j;
				
				for(int i=1; i<=key.length; i++) {
					int cost;
					
					if (key[i-1] == c1)
						cost = 0;
					else
						cost = 1;
					
					d2[i] = Math.min(Math.min(d2[i-1] + 1, d1[i] + 1), d1[i-1] + cost);

					if (i > 1 && j > 1 && key[i-1] == c0 && key[i-2] == c1)
						d2[i] = Math.min(d2[i], d0[i-2] + cost);
				}
				
				c0 = c1;
				j++;
				
				return d2[key.length];
			}
		}

		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key);
		
		int distance = -1;
		
		for(int j=1; j<=text.length; j++) {
			distance = c.appendChar(text[j-1]);
			System.out.println(distance);
		}
		
		return distance;
	}
}
