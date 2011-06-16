package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DamerauLevenshteinTextMatcherUnitTest {
	private DamerauLevenshteinTextMatcher matcher;

	@Before
	public void setUp() {
	}
	
	@Test
	public void testMatchKey() throws Exception {
		setText("Devido  às   quais");
		Set<Match> matches = matcher.matchKey("Devido=às=quais", span(0, "Devido=à".length(), 0), span("Devido=".length(), "Devido=às=quais".length(), 1));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "Devido  às   quais".length(),
						span(0, "Devido  à".length(), 0),
						span("Devido  ".length(), "Devido  às   quais".length(), 1)
					)
			));
	}

	@Test
	public void testMapping() throws Exception {
		setText("012 \t56789   3\t\t6789");
		int[] results = matcher.match(
				0, 
				"012=56789=3=6789", 
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
			);
		
		assertThat(results, is(equalTo(new int[] {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 13, 14, 16, 17, 18, 19, 20})));
	}

	//tem que resolver a questão do mapeamento junto com Damerau-Levenshtein distance
	//verificar as idéias contidas em http://en.wikipedia.org/wiki/Smith-Waterman_algorithm,
	//especialmente a reconstrução do alinhamento a partir da matriz; verificar se é possível fazer adaptativo
	@Test
	public void testMappingWithTransposition() throws Exception {
		setText("012 \t56879   3\t\t6789");
		int[] results = matcher.match(
				0, 
				"012=56789=3=6789", 
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
			);
		
		assertThat(results, is(equalTo(new int[] {0, 1, 2, 3, 5, 6, 8, 7, 9, 10, 13, 14, 16, 17, 18, 19, 20})));
	}

	@Test
	public void testDamerauLevenshteinDistance1() throws Exception {
		setText("3.º");
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

	private void setText(String text) {
		matcher = new DamerauLevenshteinTextMatcher(text);
	}

}