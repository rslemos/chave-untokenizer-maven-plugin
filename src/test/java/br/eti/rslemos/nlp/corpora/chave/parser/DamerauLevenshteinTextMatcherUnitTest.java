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

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;

public class DamerauLevenshteinTextMatcherUnitTest extends TextMatcherAbstractUnitTest {
	@Test
	public void testMapping() throws Exception {
		DamerauLevenshteinTextMatcher matcher = createTextMatcher("012 \t56789   3\t\t6789");
		
		int[] results = ((DamerauLevenshteinTextMatcher)matcher).match(
				0, 
				"012 56789 3 6789", 
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
			);
		
		assertThat(results, is(equalTo(new int[] {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 13, 14, 16, 17, 18, 19, 20})));
	}

	//tem que resolver a questão do mapeamento junto com Damerau-Levenshtein distance
	//verificar as idéias contidas em http://en.wikipedia.org/wiki/Smith-Waterman_algorithm,
	//especialmente a reconstrução do alinhamento a partir da matriz; verificar se é possível fazer adaptativo
	@Test
	public void testMappingWithTransposition() throws Exception {
		DamerauLevenshteinTextMatcher matcher = createTextMatcher("012 \t56879   3\t\t6789");
		
		int[] results = ((DamerauLevenshteinTextMatcher)matcher).match(
				0, 
				"012 56789 3 6789", 
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
			);
		
		assertThat(results, is(equalTo(new int[] {0, 1, 2, 3, 5, 6, 8, 7, 9, 10, 13, 14, 16, 17, 18, 19, 20})));
	}

	@Test
	public void testDamerauLevenshteinDistance1() throws Exception {
		DamerauLevenshteinTextMatcher matcher = createTextMatcher("3.º");
		
		Set<Match> matches = matcher.matchKey("3º.",
				span(0, "3º.".length(), 0)
			);
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "3.º".length(),
						span(0, "3.º".length(), 0)
					)
			));
	}
	

	@Test
	public void testDamerauLevenshteinDistance1EmbeddedInLongerText() throws Exception {
		DamerauLevenshteinTextMatcher matcher = createTextMatcher(" 1.º ");
		
		Set<Match> matches = matcher.matchKey("1º.",
				span(0, "1º.".length(), 0)
			);
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(" ".length(), " 1.º".length(),
						span(" ".length(), " 1.º".length(), 0)
					)
			));
	}
	
	@Override
	protected DamerauLevenshteinTextMatcher createTextMatcher(String text) {
		return new DamerauLevenshteinTextMatcher(text);
	}

	@Override
	protected DamerauLevenshteinTextMatcher createTextMatcher(String text, boolean caseSensitive) {
		return new DamerauLevenshteinTextMatcher(text, 0, caseSensitive);
	}

	@Override
	protected DamerauLevenshteinTextMatcher createTextMatcher(String text, boolean caseSensitive, boolean wordBoundaryCheck) {
		return new DamerauLevenshteinTextMatcher(text, 0, caseSensitive, wordBoundaryCheck);
	}
}
