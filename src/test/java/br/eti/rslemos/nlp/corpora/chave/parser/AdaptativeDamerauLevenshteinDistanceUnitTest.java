package br.eti.rslemos.nlp.corpora.chave.parser;


import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.DELETION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.INSERTION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.MATCH;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.SUBSTITUTION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.TRANSPOSITION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp;

public class AdaptativeDamerauLevenshteinDistanceUnitTest {
	@Test
	public void testDistance() {
		testDistance("kitten", "sitting", 6, 6, 5, 4, 3, 3, 2, 3);
		testDistance("saturday", "sunday", 8, 7, 6, 6, 5, 4, 3);
	}

	@Test
	public void testMinimalDistance() {
		testMinimalDistance("kitten", "sitting", 0, 1, 1, 1, 1, 2, 2, 3);
		testMinimalDistance("saturday", "sunday", 0, 0, 1, 2, 3, 3, 3);
	}
	
	@Test
	public void testAlignment() {
		testAlignment("kitten", "sitting", SUBSTITUTION, MATCH, MATCH, MATCH, SUBSTITUTION, MATCH, DELETION);
		testAlignment("saturday", "sunday", MATCH, INSERTION, INSERTION, MATCH, SUBSTITUTION, MATCH, MATCH, MATCH);
		testAlignment("euer", "eure", MATCH, MATCH, TRANSPOSITION);
	}
	
	private void testDistance(String key, String text, int start, int... distance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		assertThat(c.getDistance(), is(equalTo(start)));
		
		for (int i = 0; i < distance.length; i++) {
			c.append(text.charAt(i));
			assertThat(c.getDistance(), is(equalTo(distance[i])));
		}
	}

	private void testMinimalDistance(String key, String text, int start, int... minimalDistance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		assertThat(c.getMinimalDistance(), is(equalTo(start)));
		
		for (int i = 0; i < minimalDistance.length; i++) {
			c.append(text.charAt(i));
			assertThat(c.getMinimalDistance(), is(equalTo(minimalDistance[i])));
		}
	}
	
	private void testAlignment(String key, String text, AlignOp... alignment) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		for (int i = 0; i < text.length(); i++) {
			c.append(text.charAt(i));
		}
		
		assertThat(c.getAlignment(), is(equalTo(alignment)));
	}

}
