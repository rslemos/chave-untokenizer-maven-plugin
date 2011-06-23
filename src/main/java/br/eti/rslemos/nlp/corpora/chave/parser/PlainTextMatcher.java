package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;
import static java.lang.Character.isWhitespace;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlainTextMatcher implements TextMatcher {
	private final char[] text;
	private final int from;
	private final int to;

	private final boolean caseSensitive;
	private final boolean wordBoundaryCheck;

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
		this.text = text.toCharArray();
		this.from = from;
		this.to = to;
		this.caseSensitive = caseSensitive;
		this.wordBoundaryCheck = wordBoundaryCheck;
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
		
		for (int k = from; k < to; k++) {
			int[] outPoints = match(k, key, inPoints);
			if (outPoints != null) {
				Span[] outSpans = new Span[inSpans.length];
				for (int i = 0; i < outSpans.length; i++) {
					outSpans[i] = span(outPoints[i*2 + 2], outPoints[i*2 + 3], inSpans[i].entry);
				}
				
				matches.add(Match.match(outPoints[0], outPoints[1], outSpans));
			}
		}
		
		return matches;
	}

	int[] match(int k, String toMatch, int... inPoints) {
		if (wordBoundaryCheck && isNeitherWhitespaceNorWordBoundary(k - 1) && !isWordBoundary(toMatch.charAt(0)))
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

		if (wordBoundaryCheck && isNeitherWhitespaceNorWordBoundary(k) && !isWordBoundary(toMatch.charAt(toMatch.length() - 1)))
			return null;

		return outPoints;
	}

	private char toLowerCase(char ch) {
		return caseSensitive ? ch : Character.toLowerCase(ch);
	}

	private boolean isNeitherWhitespaceNorWordBoundary(int k) {
		return (k >= from && k < to) && !(isWhitespace(text[k]) || isWordBoundary(text[k]));
	}
	
	private static void remap(int j, int[] inPoints, int k, int[] outPoints) {
		for (int i = 0; i < inPoints.length; i++) {
			if (inPoints[i] == j)
				outPoints[i] = k;
		}
	}

	private static boolean isWordBoundary(char c) {
		return !Character.isLetterOrDigit(c);
	}
}
