package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.MATCH;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.TRANSPOSITION;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;
import static java.lang.Character.isWhitespace;

import java.util.LinkedHashSet;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp;

public class DamerauLevenshteinTextMatcher implements TextMatcher {
	public static class Parameters {
		public int threshold;
		public boolean caseSensitive;
		public boolean wordBoundaryCheck;
		
		public Parameters(int threshold, boolean caseSensitive, boolean wordBoundaryCheck) {
			this.threshold = threshold;
			this.caseSensitive = caseSensitive;
			this.wordBoundaryCheck = wordBoundaryCheck;
		}
		
		public TextMatcher create(String text) {
			if (threshold > 0)
				return new DamerauLevenshteinTextMatcher(text, threshold, caseSensitive, wordBoundaryCheck);
			else
				return new PlainTextMatcher(text, caseSensitive, wordBoundaryCheck);
		}
		
		public TextMatcher create(String text, int from, int to) {
			if (threshold > 0)
				return new DamerauLevenshteinTextMatcher(text, from, to, threshold, caseSensitive, wordBoundaryCheck);
			else
				return new PlainTextMatcher(text, from, to, caseSensitive, wordBoundaryCheck);
		}
	}
	
	private final char[] text;
	private final int from;
	private final int to;

	private final int threshold;
	private final boolean caseSensitive;
	private final boolean wordBoundaryCheck;

	public DamerauLevenshteinTextMatcher(String text) {
		this(text, 0, text.length());
	}
	
	public DamerauLevenshteinTextMatcher(String text, int threshold) {
		this(text, 0, text.length(), threshold);
	}

	public DamerauLevenshteinTextMatcher(String text, int threshold, boolean caseSensitive) {
		this(text, 0, text.length(), threshold, caseSensitive);
	}

	public DamerauLevenshteinTextMatcher(String text, int threshold, boolean caseSensitive, boolean wordBoundaryCheck) {
		this(text, 0, text.length(), threshold, caseSensitive, wordBoundaryCheck);
	}

	public DamerauLevenshteinTextMatcher(String text, int from, int to) {
		this(text, from, to, 1);
	}
	
	public DamerauLevenshteinTextMatcher(String text, int from, int to, int threshold) {
		this(text, from, to, threshold, false);
	}

	public DamerauLevenshteinTextMatcher(String text, int from, int to, int threshold, boolean caseSensitive) {
		this(text, from, to, threshold, caseSensitive, true);
	}

	public DamerauLevenshteinTextMatcher(String text, int from, int to, int threshold, boolean caseSensitive, boolean wordBoundaryCheck) {
		this.text = text.toCharArray();
		this.from = from;
		this.to = to;
		this.caseSensitive = caseSensitive;
		this.wordBoundaryCheck = wordBoundaryCheck;
		this.threshold = threshold;
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

	int[] match(int k0, String toMatch, int... inPoints) {
		int k = k0;
		int threshold = toMatch.length() > 1 ? this.threshold : 0;
		
		if (wordBoundaryCheck && isNeitherWhitespaceNorWordBoundary(k - 1) && !isWordBoundary(toMatch.charAt(0)))
			return null;

		int[] outPoints = new int[inPoints.length];
		System.arraycopy(inPoints, 0, outPoints, 0, outPoints.length);
		
		AdaptativeDamerauLevenshteinDistance dl = new AdaptativeDamerauLevenshteinDistance(toLowerCase(toMatch).toCharArray());
		
		while (k < to && dl.getDistance() > 0) {
			if (isWhitespace(text[k])) {
				dl.append('=');
				k++;
				while (k < to && isWhitespace(text[k])) {
					// idealmente seria Integer.MAX_VALUE em vez de text.length*2
					// porém estava dando overflow nos cálculos internos do Damerau-Levenshtein
					// e os custos ficavam negativos
					dl.append(to*2, to*2, to*2, 0, to*2, ' ');
					k++;
				}
			} else {
				dl.append(toLowerCase(text[k++]));
			}
		}
		
		if (dl.getDistance() > threshold)
			return null;

		if (wordBoundaryCheck && isNeitherWhitespaceNorWordBoundary(k) && !isWordBoundary(toMatch.charAt(toMatch.length() - 1)))
			return null;

		AlignOp[] alignment = stuffTransposition(selectBestAlignment(dl.getAlignments()));
		for (int i = 0; i < alignment.length; i++) {
			AlignOp op = alignment[i];
			
			switch (op) {
			case MATCH:
			case SUBSTITUTION:
				break;
			case DELETION:
				for (int l = 0; l < outPoints.length; l++) {
					if (outPoints[l] >= i)
						outPoints[l]++;
				}
				break;
			case INSERTION:
				for (int l = 0; l < outPoints.length; l++) {
					if (outPoints[l] >= i)
						outPoints[l]--;
				}
				break;
			case TRANSPOSITION:
				for (int l = 0; l < outPoints.length; l++) {
					if (outPoints[l] == i)
						outPoints[l]++;
					else if (outPoints[l] == i+1)
						outPoints[l]--;
				}
				break;
			default:
				break;
			}
		}

		for (int i = 0; i < outPoints.length; i++) {
			outPoints[i] += k0;
		}
		
		return outPoints;
	}

	private String toLowerCase(String toMatch) {
		return caseSensitive ? toMatch : toMatch.toLowerCase();
	}

	private char toLowerCase(char ch) {
		return caseSensitive ? ch : Character.toLowerCase(ch);
	}

	private static AlignOp[] stuffTransposition(AlignOp[] alignment) {
		int transpositions = 0;
		for (AlignOp op : alignment) {
			if (op == TRANSPOSITION)
				transpositions++;
		}
		
		if (transpositions > 0) {
			AlignOp[] result = new AlignOp[alignment.length + transpositions];
			
			for (int i = 0, j = 0; i < alignment.length; i++) {
				result[j++] = alignment[i];
				if (alignment[i] == TRANSPOSITION)
					result[j++] = MATCH;
			}
			
			return result;
		} else
			return alignment;
	}

	private static AlignOp[] selectBestAlignment(Set<AlignOp[]> alignments) {
		// por enquanto pega o primeiro (segundo ordenação do iterator)
		// mais tarde pode preferir sequências mais simples
		// ou dar peso maior a alinhamentos sem determinadas operações
		return alignments.iterator().next();
	}

	private boolean isNeitherWhitespaceNorWordBoundary(int k) {
		return (k >= from && k < to) && !(isWhitespace(text[k]) || isWordBoundary(text[k]));
	}
	
	private static boolean isWordBoundary(char c) {
		return !Character.isLetterOrDigit(c);
	}
}
