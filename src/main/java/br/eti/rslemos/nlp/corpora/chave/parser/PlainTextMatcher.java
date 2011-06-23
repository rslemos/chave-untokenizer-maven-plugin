package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.isWhitespace;

import java.util.Arrays;

public class PlainTextMatcher extends AbstractTextMatcher {

	public PlainTextMatcher(String text) {
		this(text, 0, text.length());
	}
	
	public PlainTextMatcher(String text, boolean caseSensitive) {
		this(text, 0, text.length(), caseSensitive);
	}

	public PlainTextMatcher(String text, boolean caseSensitive, boolean wordBoundaryCheck) {
		this(text, 0, text.length(), caseSensitive, wordBoundaryCheck);
	}

	public PlainTextMatcher(String text, int from, int to) {
		this(text, from, to, false);
	}

	public PlainTextMatcher(String text, int from, int to, boolean caseSensitive) {
		this(text, from, to, caseSensitive, true);
	}

	public PlainTextMatcher(String text, int from, int to, boolean caseSensitive, boolean wordBoundaryCheck) {
		super(text.toCharArray(), from, to, caseSensitive, wordBoundaryCheck);
	}

	protected int[] match(int k, String toMatch, int... inPoints) {
		int k1 = k - 1;
		if (wordBoundaryCheck && k1 >= from && k1 < to && isLetterOrDigit(text[k1]) && isLetterOrDigit(toMatch.charAt(0)))
			return null;

		int[] outPoints = new int[inPoints.length];
		Arrays.fill(outPoints, -1);
		
		for (int j = 0; j < toMatch.length(); j++) {
			remap(j, inPoints, k, outPoints);

			if (toMatch.charAt(j) == '=') {
				while (isWhitespace(text[k]) && k < to)
					k++;
				
				continue;
			}
			
			if (k >= to || toLowerCase(toMatch.charAt(j)) != toLowerCase(text[k])) {
				return null;
			}
			
			k++;
		}
		
		remap(toMatch.length(), inPoints, k, outPoints);

		if (wordBoundaryCheck && k >= from && k < to && isLetterOrDigit(text[k]) && isLetterOrDigit(toMatch.charAt(toMatch.length() - 1)))
			return null;

		return outPoints;
	}

	private static void remap(int j, int[] inPoints, int k, int[] outPoints) {
		for (int i = 0; i < inPoints.length; i++) {
			if (inPoints[i] == j)
				outPoints[i] = k;
		}
	}
}
