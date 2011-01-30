package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;
import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;

public class DirectMatchStrategy implements MatchStrategy {
	
	public Set<Match> match(String text, List<String> cg) {
		Map<String, Set<Match>> cache = new LinkedHashMap<String, Set<Match>>();
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
			
			if (!cache.containsKey(key0)){
				cache.put(key0, matchKey(text, key0, span(0, key0.length(), 0)));
			}
			
			for (Match match : cache.get(key0)) {
				matches.add(match.adjust(0, i));
			}
		}
		
		return matches;
	}

	public Set<Match> matchKey(String text, String key, Span... inSpans) {
		Set<Match> matches = new LinkedHashSet<Match>();

		int[] inPoints = new int[inSpans.length*2 + 2];
		inPoints[0] = 0;
		inPoints[1] = key.length();
		
		for (int i = 0; i < inSpans.length; i++) {
			inPoints[i*2 + 2] = inSpans[i].from;
			inPoints[i*2 + 3] = inSpans[i].to;
		}
		
		for (int k = 0; k < text.length(); k++) {
			int[] outPoints = matchKey(text, k, key, true, inPoints);
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

	public int[] matchKey(String text, int k, String key, boolean wordBoundary, int... keyPoints) {
		int[] textPoints = new int[keyPoints.length];
		Arrays.fill(textPoints, -1);
		
		if (wordBoundary && k > 0 && !isWordBoundary(text.charAt(k - 1)))
			return null;
		
		for (int j = 0; j < key.length(); j++) {
			remap(j, keyPoints, k, textPoints);
			
			if (key.charAt(j) == '=') {
				while (isWhitespace(text.charAt(k)))
					k++;
				
				continue;
			}
			
			if (k >= text.length() || toLowerCase(key.charAt(j)) != toLowerCase(text.charAt(k))) {
				return null;
			}
			
			k++;
		}

		if (wordBoundary && k < text.length() && !isWordBoundary(text.charAt(k)))
			return null;
		
		remap(key.length(), keyPoints, k, textPoints);

		return textPoints;
	}

	private static boolean isWordBoundary(char c) {
		return !Character.isLetterOrDigit(c);
	}

	private void remap(int j, int[] inPoints, int k, int[] outPoints) {
		for (int i = 0; i < inPoints.length; i++) {
			if (inPoints[i] == j)
				outPoints[i] = k;
		}
	}

	private static String getKey(String currentKey) {
		currentKey = currentKey.split(" ")[0];
		
		if (currentKey.startsWith("$"))
			currentKey = currentKey.substring(1);
		
		return currentKey;
	}
}