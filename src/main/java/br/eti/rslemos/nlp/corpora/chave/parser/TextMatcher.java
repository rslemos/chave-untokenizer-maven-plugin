package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;
import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;

public class TextMatcher {
	private char[] text;

	public void setText(String text) {
		this.text = text.toCharArray();
	}

	public Set<Match> matchKey(String key, Span... inSpans) {
		Set<Match> matches = new LinkedHashSet<Match>();

		int[] inPoints = new int[inSpans.length*2 + 2];
		inPoints[0] = 0;
		inPoints[1] = key.length();
		
		for (int i = 0; i < inSpans.length; i++) {
			inPoints[i*2 + 2] = inSpans[i].from;
			inPoints[i*2 + 3] = inSpans[i].to;
		}
		
		for (int k = 0; k < text.length; k++) {
			int[] outPoints = match(k, key, inPoints);
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

	int[] match(int k, String toMatch, int... inPoints) {
		if (isNeitherWhitespaceNorWordBoundary(k - 1) && !isWordBoundary(toMatch.charAt(0)))
			return null;
		
		int[] outPoints = new int[inPoints.length];
		Arrays.fill(outPoints, -1);
		
		for (int j = 0; j < toMatch.length(); j++) {
			remap(j, inPoints, k, outPoints);
			
			if (toMatch.charAt(j) == '=') {
				while (isWhitespace(text[k]))
					k++;
				
				continue;
			}
			
			if (k >= text.length || toLowerCase(toMatch.charAt(j)) != toLowerCase(text[k])) {
				return null;
			}
			
			k++;
		}

		remap(toMatch.length(), inPoints, k, outPoints);

		if (isNeitherWhitespaceNorWordBoundary(k) && !isWordBoundary(toMatch.charAt(toMatch.length() - 1)))
			return null;

		return outPoints;
	}

	private boolean isNeitherWhitespaceNorWordBoundary(int k) {
		return (k >= 0 && k < text.length) && !(isWhitespace(text[k]) || isWordBoundary(text[k]));
	}
	
	private static boolean isWordBoundary(char c) {
		return !Character.isLetterOrDigit(c);
	}

	private static void remap(int j, int[] inPoints, int k, int[] outPoints) {
		for (int i = 0; i < inPoints.length; i++) {
			if (inPoints[i] == j)
				outPoints[i] = k;
		}
	}

	@Deprecated
	public char charAt(int i) {
		return text[i];
	}

	@Deprecated
	void getChars(char[] cs) {
		int length = cs.length;
		if (length > text.length) {
		    throw new StringIndexOutOfBoundsException(length);
		}
		System.arraycopy(text, 0, cs, 0, length - 0);
	}
}
