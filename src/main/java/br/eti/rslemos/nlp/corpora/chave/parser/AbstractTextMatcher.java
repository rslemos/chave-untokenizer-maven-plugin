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

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;
import static java.lang.Character.isLetterOrDigit;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractTextMatcher implements TextMatcher {

	protected abstract int[] match(int from, int to, int k0, String toMatch, int... inPoints);

	protected final char[] text;
	
	private final boolean caseSensitive;
	protected final boolean wordBoundaryCheck;

	public AbstractTextMatcher(char[] text, boolean caseSensitive, boolean wordBoundaryCheck) {
		super();
		this.text = text;
		this.caseSensitive = caseSensitive;
		this.wordBoundaryCheck = wordBoundaryCheck;
	}

	public Set<Match> matchKey(String key, Span... inSpans) {
		return matchKey(0, text.length, key, inSpans);
	}
	
	public Set<Match> matchKey(int from, int to, String key, Span... inSpans) {
		Set<Match> matches = new LinkedHashSet<Match>();
	
		int[] inPoints = new int[inSpans.length*2 + 2];
		inPoints[0] = 0;
		inPoints[1] = key.length();
		
		for (int i = 0; i < inSpans.length; i++) {
			inPoints[i*2 + 2] = inSpans[i].from;
			inPoints[i*2 + 3] = inSpans[i].to;
		}
		
		for (int k = from; k < to; k++) {
			int[] outPoints = match(from, to, k, key, inPoints);
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

	public Set<Match> matchWordEndOrNewLine() {
		return matchWordEndOrNewLine(0, text.length);
	}
	
	public Set<Match> matchWordEndOrNewLine(int from, int to) {
		Set<Match> result = new LinkedHashSet<Match>();


		int k = from;

		if (text[k] == '\n')
			result.add(Match.match(k, k + 1, span(k, k + 1, 0)));

		for (k++; k < to; k++) {
			if (text[k] == '\n') {
				result.add(Match.match(k, k + 1, span(k, k + 1, 0)));
			} else {
				if ((!isLetterOrDigit(text[k]) && isLetterOrDigit(text[k - 1])) || isPunctuation(text[k - 1])) {
					result.add(Match.match(k, k, span(k, k, 0)));
				}
			}
		}

		if (isLetterOrDigit(text[k - 1]) || isPunctuation(text[k - 1])) {
			result.add(Match.match(k, k, span(k, k, 0)));
		}

		return result;
	}

	protected String toLowerCase(String toMatch) {
		return caseSensitive ? toMatch : toMatch.toLowerCase();
	}

	protected char toLowerCase(char ch) {
		return caseSensitive ? ch : Character.toLowerCase(ch);
	}

	private static boolean isPunctuation(char ch) {
		final int MASK = (1 << Character.OTHER_PUNCTUATION) | (1 << Character.END_PUNCTUATION);
		
		return ((MASK >> Character.getType(ch)) & 1) != 0;
	}
}
