/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "chave-untokenizer-maven-plugin"
 * Copyright 2013  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.MATCH;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.TRANSPOSITION;
import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.isWhitespace;

import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp;

public class DamerauLevenshteinTextMatcher extends AbstractTextMatcher {
	private final int threshold;
	
	public DamerauLevenshteinTextMatcher(String text) {
		this(text, 1);
	}
	
	public DamerauLevenshteinTextMatcher(String text, int threshold) {
		this(text, threshold, false);
	}

	public DamerauLevenshteinTextMatcher(String text, int threshold, boolean caseSensitive) {
		this(text, threshold, caseSensitive, true);
	}

	public DamerauLevenshteinTextMatcher(String text, int threshold, boolean caseSensitive, boolean wordBoundaryCheck) {
		super(text.toCharArray(), caseSensitive, wordBoundaryCheck);
		this.threshold = threshold;
	}

	protected int[] match(int k0, String toMatch, int... inPoints) {
		return match(0, text.length, k0, toMatch, inPoints);
	}
	
	@Override
	protected int[] match(int from, int to, int k0, String toMatch, int... inPoints) {
		int k = k0;
		int threshold = toMatch.length() > 1 ? this.threshold : 0;
		
		if (wordBoundaryCheck && (k - 1) >= from && (k - 1) < to && isLetterOrDigit(text[k - 1]) && isLetterOrDigit(toMatch.charAt(0)))
			return null;

		int[] outPoints = new int[inPoints.length];
		System.arraycopy(inPoints, 0, outPoints, 0, outPoints.length);
		
		AdaptativeDamerauLevenshteinDistance dl = new AdaptativeDamerauLevenshteinDistance(toLowerCase(toMatch).toCharArray());
		
		// algo deve ser feito aqui, porque o algoritmo para apenas quando se chega ao final do texto
		// (k == to) ou quando acontece um match com custo zero (getDistance() == 0)
		// obviamente deveria parar quando atingisse o menor custo possível
		while (k < to && dl.getDistance() >= dl.getMinimalFutureDistance()) {
			if (isWhitespace(text[k])) {
				dl.append('=');
				k++;
				boolean wouldContinue = dl.getDistance() >= dl.getMinimalFutureDistance();
				while (k < to && isWhitespace(text[k])) {
					// idealmente seria Integer.MAX_VALUE em vez de text.length*2
					// porém estava dando overflow nos cálculos internos do Damerau-Levenshtein
					// e os custos ficavam negativos
					dl.append(to*2, to*2, to*2, 0, to*2, ' ');
					k++;
				}
				if (!wouldContinue) break;
			} else {
				dl.append(toLowerCase(text[k++]));
			}
		}
		
		if (dl.getDistance() > dl.getPastDistance())
			dl.undo();
		
		if (dl.getDistance() > threshold)
			return null;

		if (wordBoundaryCheck && k >= from && k < to && isLetterOrDigit(text[k]) && isLetterOrDigit(toMatch.charAt(toMatch.length() - 1)))
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
}
