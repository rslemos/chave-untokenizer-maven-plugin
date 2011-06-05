package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.MATCH;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.TRANSPOSITION;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;
import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp;
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

	int[] match(int k0, String toMatch, int... inPoints) {
		int k = k0;
		int threshold = toMatch.length() > 1 ? 1 : 0;
		
		if (isNeitherWhitespaceNorWordBoundary(k - 1) && !isWordBoundary(toMatch.charAt(0)))
			return null;

		int[] outPoints = new int[inPoints.length];
		System.arraycopy(inPoints, 0, outPoints, 0, outPoints.length);
		
		AdaptativeDamerauLevenshteinDistance dl = new AdaptativeDamerauLevenshteinDistance(toMatch.toLowerCase().toCharArray());
		
		while (k < text.length && dl.getDistance() > 0) {
			if (isWhitespace(text[k])) {
				dl.append('=');
				k++;
				while (k < text.length && isWhitespace(text[k])) {
					// idealmente seria Integer.MAX_VALUE em vez de text.length*2
					// porém estava dando overflow nos cálculos internos do Damerau-Levenshtein
					// e os custos ficavam negativos
					dl.append(text.length*2, text.length*2, text.length*2, 1, text.length*2, ' ');
					threshold++;
					k++;
				}
			} else {
				dl.append(toLowerCase(text[k++]));
			}
		}
		
		if (dl.getDistance() > threshold)
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
		
		if (isNeitherWhitespaceNorWordBoundary(k) && !isWordBoundary(toMatch.charAt(toMatch.length() - 1)))
			return null;

		return outPoints;
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
		return (k >= 0 && k < text.length) && !(isWhitespace(text[k]) || isWordBoundary(text[k]));
	}
	
	private static boolean isWordBoundary(char c) {
		return !Character.isLetterOrDigit(c);
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
		System.arraycopy(text, 0, cs, 0, length);
	}
}
