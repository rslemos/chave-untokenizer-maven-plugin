package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AdaptativeDamerauLevenshteinDistanceUnitTest {
	//@Test
	public void testKittenSitting() {
		test0("kitten", "sitting", 6, 6, 5, 4, 3, 3, 2, 3);
	}

	//@Test
	public void testSaturdaySunday() {
		test0("saturday", "sunday", 8, 7, 6, 6, 5, 4, 3);
	}
	
	@Test
	public void show() {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance("abcdefghijklmnopqrstuvwxyz".toCharArray());

		for (char d : "bacfedggi_jklqpomnrstvwyzx".toCharArray()) {
			c.append(d);
		}
	}

	private void test0(String key, String text, int start, int... distance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		assertThat(c.getDistance(), is(equalTo(start)));
		
		for (int i = 0; i < distance.length; i++) {
			c.append(text.charAt(i));
			assertThat(c.getDistance(), is(equalTo(distance[i])));
		}
	}
}
