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

import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.isWhitespace;

import java.util.Arrays;

public class PlainTextMatcher extends AbstractTextMatcher {

	public PlainTextMatcher(String text) {
		this(text, false);
	}
	
	public PlainTextMatcher(String text, boolean caseSensitive) {
		this(text, caseSensitive, true);
	}

	public PlainTextMatcher(String text, boolean caseSensitive, boolean wordBoundaryCheck) {
		super(text.toCharArray(), caseSensitive, wordBoundaryCheck);
	}

	protected int[] match(int from, int to, int k, String toMatch, int... inPoints) {
		int k1 = k - 1;
		if (wordBoundaryCheck && k1 >= from && k1 < to && isLetterOrDigit(text[k1]) && isLetterOrDigit(toMatch.charAt(0)))
			return null;

		int[] outPoints = new int[inPoints.length];
		Arrays.fill(outPoints, -1);
		
		for (int j = 0; j < toMatch.length(); j++) {
			remap(j, inPoints, k, outPoints);

			if (toMatch.charAt(j) == ' ') {
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
