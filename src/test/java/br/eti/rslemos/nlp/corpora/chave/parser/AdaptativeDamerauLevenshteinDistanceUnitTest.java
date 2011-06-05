package br.eti.rslemos.nlp.corpora.chave.parser;


import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.DELETION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.INSERTION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.MATCH;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.SUBSTITUTION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.TRANSPOSITION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.collection.IsCollectionContaining.hasItem;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

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
	public void testAlignments() {
		testAlignments("kitten", "sitting", makeAlignment(SUBSTITUTION, MATCH, MATCH, MATCH, SUBSTITUTION, MATCH, DELETION));
		testAlignments("saturday", "sunday", makeAlignment(MATCH, INSERTION, INSERTION, MATCH, SUBSTITUTION, MATCH, MATCH, MATCH));
		testAlignments("euer", "eure", makeAlignment(MATCH, MATCH, TRANSPOSITION));
		testAlignments("dunkel", "dunkler", 
				makeAlignment(MATCH, MATCH, MATCH, MATCH, TRANSPOSITION, DELETION),
				makeAlignment(MATCH, MATCH, MATCH, MATCH, DELETION, MATCH, SUBSTITUTION)
			);
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
	
	private void testAlignments(String key, String text, AlignOp[]... alignments) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		for (int i = 0; i < text.length(); i++) {
			c.append(text.charAt(i));
		}
		
		Set<AlignOp[]> actualAlignments = c.getAlignments();
		
		for (AlignOp[] alignment : alignments) {
			assertThat(actualAlignments, hasItem(alignment));
			
			for (Iterator<AlignOp[]> iterator = actualAlignments.iterator(); iterator.hasNext();) {
				AlignOp[] actualAlignment = iterator.next();
				if (Arrays.equals(alignment, actualAlignment))
					iterator.remove();
			}
		}
		
		if (actualAlignments.size() > 0) {
			StringBuilder message = new StringBuilder();
			message.append("More alignments found:\n");
			
			for (AlignOp[] alignment : actualAlignments) {
				message.append(Arrays.toString(alignment)).append("\n");
			}
			
			fail(message.toString());
		}
	}

	private static AlignOp[] makeAlignment(AlignOp... op) {
		return op;
	}
}
