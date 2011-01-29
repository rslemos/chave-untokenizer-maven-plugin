package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;
import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DirectMatchStrategy implements MatchStrategy {
	
	public Match match0(String text, List<String> cg) {
		String key0 = getKey(cg.get(0));
		
		if (cg.size() > 1) {
			String key1 = getKey(cg.get(1));
			
			if (Arrays.binarySearch(new String[] { ",", "-", ".", ":", ";" }, key1) >= 0) {
				if (key0.endsWith(key1)) {
					key0 = key0.substring(0, key0.length() - key1.length());
				}
			}
		}
		
		int[] points = matchKey(text, key0, true, 0, key0.length());
		
		if (points != null) {
			return new Match(points[0], points[1], span(points[0], points[1], 0));
		} else {
			return null;
		}
	}

	public int[] matchKey(String text, String key, boolean maySkip, int... points) {
		int[] mappedPoints = new int[points.length];
		Arrays.fill(mappedPoints, -1);
		
		int j = 0;
		int k = 0;

		if (maySkip)
			try {
				if (key.charAt(j) != '=')
					while (toLowerCase(key.charAt(j)) != toLowerCase(text.charAt(k))) {
						k++;
					}
				else
					while (!isWhitespace(text.charAt(k))) {
						k++;
					}
			} catch (IndexOutOfBoundsException e) {
			}
		
		try {
			while (true) {
				for (int i = 0; i < points.length; i++) {
					if (points[i] == j)
						mappedPoints[i] = k;
				}
				
				if (key.charAt(j) == '=') {
					while (isWhitespace(text.charAt(k)))
						k++;
					j++;
					
					
					continue;
				}
				
				if (toLowerCase(key.charAt(j)) != toLowerCase(text.charAt(k))) {
					break;
				}
				
				j++;
				k++;
			}
		} catch (IndexOutOfBoundsException e) {
		}

		if (j == key.length()) {
			return mappedPoints;
		} else {
			return null;
		}
	}

	public Set<Match> match(String text, List<String> cg) {
		Match match0 = match0(text, cg);
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}

	private static String getKey(String currentKey) {
		currentKey = currentKey.split(" ")[0];
		
		if (currentKey.startsWith("$"))
			currentKey = currentKey.substring(1);
		
		return currentKey;
	}

	public Match matchTwo(String text, String key, 
			int span0_start, int span0_end, 
			int span1_start, int span1_end) {

		int[] points = matchKey(text, key, false, 0, key.length(), span0_start, span0_end, span1_start, span1_end);
		
		if (points != null) {
			return new Match(points[0], points[1], 
					span(points[2], points[3], 0),
					span(points[4], points[5], 1)
				);
		} else {
			return null;
		}
	}
}