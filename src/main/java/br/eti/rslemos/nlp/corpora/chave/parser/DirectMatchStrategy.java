package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;
import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;

public class DirectMatchStrategy implements MatchStrategy {
	
	public Set<Match> match(String text, List<String> cg) {
		Set<Match> matches = new LinkedHashSet<Match>();

		for (int i = 0; i < cg.size(); i++) {
			String key0 = getKey(cg.get(i));
			
			if (cg.size() > i + 1) {
				String key1 = getKey(cg.get(i + 1));
				
				if (Arrays.binarySearch(new String[] { ",", "-", ".", ":", ";" }, key1) >= 0) {
					if (key0.endsWith(key1)) {
						key0 = key0.substring(0, key0.length() - key1.length());
					}
				}
			}
			
			matches.addAll(matchKey(text, key0, span(0, key0.length(), i)));
		}
		
		return matches;
	}

	private Set<Match> matchKey(String text, String key, Span... inSpans) {
		Set<Match> matches = new LinkedHashSet<Match>();

		int[] inPoints = new int[inSpans.length*2 + 2];
		inPoints[0] = 0;
		inPoints[1] = key.length();
		
		for (int i = 0; i < inSpans.length; i++) {
			inPoints[i*2 + 2] = inSpans[i].from;
			inPoints[i*2 + 3] = inSpans[i].to;
		}
		
		for (int k = 0; k < text.length(); k++) {
			int[] outPoints = matchKey(text, k, key, inPoints);
			if (outPoints != null) {
				Span[] outSpans = new Span[inSpans.length];
				for (int i = 0; i < outSpans.length; i++) {
					outSpans[i] = span(outPoints[i*2 + 2], outPoints[i*2 + 3], inSpans[i].entry);
				}
				
				matches.add(new Match(outPoints[0], outPoints[1], outSpans));
			}
		}
		
		return matches;
	}

	public int[] matchKey(String text, int k, String key, int... points) {
		int[] mappedPoints = new int[points.length];
		Arrays.fill(mappedPoints, -1);
		
		int j = 0;
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

	private static String getKey(String currentKey) {
		currentKey = currentKey.split(" ")[0];
		
		if (currentKey.startsWith("$"))
			currentKey = currentKey.substring(1);
		
		return currentKey;
	}

	public Set<Match> matchTwo(String text, String key, 
			int span0_start, int span0_end, 
			int span1_start, int span1_end) {

		return matchKey(text, key, span(span0_start, span0_end, 0), span(span1_start, span1_end, 1));
	}
}