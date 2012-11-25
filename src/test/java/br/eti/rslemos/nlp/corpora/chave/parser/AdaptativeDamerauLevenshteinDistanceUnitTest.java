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


import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.buildCost1FromClasses;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.buildCost2FromClasses;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.constantCost1;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.constantCost2;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.DELETION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.INSERTION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.MATCH;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.SUBSTITUTION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.TRANSPOSITION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp;
import br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.Cost1;
import br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.Cost2;

public class AdaptativeDamerauLevenshteinDistanceUnitTest {
	@Test
	public void testDistance() {
		testDistance("kitten", "sitting", 6, 6, 5, 4, 3, 3, 2, 3);
		testDistance("saturday", "sunday", 8, 7, 6, 6, 5, 4, 3);
	}

	@Test
	public void testPastDistance() {
		testPastDistance("kitten", "sitting", 6, 6, 5, 4, 3, 3, 2);
		testPastDistance("saturday", "sunday", 8, 7, 6, 6, 5, 4);
	}

	@Test
	public void testDistanceCustomCosts() {
		testDistanceCustomCosts(0, 2, 2, 2, 2, "kitten", "sitting", 12, 12, 10, 8, 6, 6, 4, 6);
		testDistanceCustomCosts(0, 2, 2, 2, 2, "saturday", "sunday", 16, 14, 12, 12, 10, 8, 6);
		
		testDistanceCustomCosts(1, 2, 3, 4, 5, "kitten", "sitting", 18, 17, 15, 13, 11, 10, 8, 12);
		testDistanceCustomCosts(5, 4, 3, 2, 1, "saturday", "sunday", 24, 25, 26, 27, 28, 29, 30);
	}

	@Test
	public void testDistanceAdvancedCostModel() {
		Cost1 cost1Upper = buildCost1FromClasses(1, Character.UPPERCASE_LETTER);
		Cost1 cost0Any = new Cost1(-1, 0);
		Cost1 cost2Any = new Cost1(-1, 2);
		
		Cost2 cost1LowerToUpper = buildCost2FromClasses(1, Character.LOWERCASE_LETTER, Character.UPPERCASE_LETTER);
		Cost2 cost1UpperToLower = buildCost2FromClasses(1, Character.UPPERCASE_LETTER, Character.LOWERCASE_LETTER);
		Cost2 cost2AnyToAny = new Cost2(-1, -1, 2);
		
		// matchcost
		testDistanceAdvancedCostModel(new Cost1[] { cost1Upper, cost0Any }, constantCost2(2), constantCost1(2), constantCost1(2), constantCost2(2),
				"Kitten", "Kitten", 12, 11, 9, 7, 5, 3, 1);
		// substcost
		testDistanceAdvancedCostModel(constantCost1(0), new Cost2[] { cost1LowerToUpper, cost2AnyToAny }, constantCost1(2), constantCost1(2), constantCost2(2),
				"kitten", "KITTEN", 12, 11, 10, 9, 8, 7, 6);
		// delcost
		testDistanceAdvancedCostModel(constantCost1(0), constantCost2(2), constantCost1(2), new Cost1[] { cost1Upper, cost2Any }, constantCost2(2),
				"kitten", "Kkitten", 12, 12, 11, 9, 7, 5, 3, 1);
		// inscost
		testDistanceAdvancedCostModel(constantCost1(0), constantCost2(2), new Cost1[] { cost1Upper, cost2Any }, constantCost1(2), constantCost2(2),
				"Kitten", "itten", 11, 9, 7, 5, 3, 1);
		// tranpcost
		testDistanceAdvancedCostModel(constantCost1(0), constantCost2(2), constantCost1(2), constantCost1(2), new Cost2[] { cost1UpperToLower, cost2AnyToAny },
				"kItten", "Iktten", 12, 10, 9, 7, 5, 3, 1);
	}
	
	@Test
	public void testDistanceCustomLocalCost() {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance("kitten".toCharArray());

		assertThat(c.getDistance(), is(equalTo(6)));
		c.append(0, 0, 1, 1, 1, 's');
		assertThat(c.getDistance(), is(equalTo(5)));
		c.append('i');
		assertThat(c.getDistance(), is(equalTo(4)));
		c.append('t');
		assertThat(c.getDistance(), is(equalTo(3)));
		c.append('t');
		assertThat(c.getDistance(), is(equalTo(2)));
		
	}
	
	@Test
	public void testMinimalDistance() {
		testMinimalDistance("kitten", "sitting", 0, 1, 1, 1, 1, 2, 2, 3);
		testMinimalDistance("saturday", "sunday", 0, 0, 1, 2, 3, 3, 3);
	}
	
	@Test
	public void testMinimalFutureDistance() {
		testMinimalFutureDistance("kitten", "sitting", 0, 1, 1, 1, 1, 2, 3, 4);
		testMinimalFutureDistance("saturday", "sunday", 0, 0, 1, 2, 3, 3, 4);
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

	@Test
	public void testUndo() {
		testUndo("kitten", "sitting", 6, 6, 5, 4, 3, 3, 2, 3);
		testUndo("saturday", "sunday", 8, 7, 6, 6, 5, 4, 3);
	}

	@Test
	public void testUndoOverAlignments() {
		testUndoOverAlignments("kitten", "sitting", makeAlignment(SUBSTITUTION, MATCH, MATCH, MATCH, SUBSTITUTION, MATCH, DELETION));
		testUndoOverAlignments("saturday", "sunday", makeAlignment(MATCH, INSERTION, INSERTION, MATCH, SUBSTITUTION, MATCH, MATCH, MATCH));
		testUndoOverAlignments("euer", "eure", makeAlignment(MATCH, MATCH, TRANSPOSITION));
		testUndoOverAlignments("dunkel", "dunkler", 
				makeAlignment(MATCH, MATCH, MATCH, MATCH, TRANSPOSITION, DELETION),
				makeAlignment(MATCH, MATCH, MATCH, MATCH, DELETION, MATCH, SUBSTITUTION)
			);
	}

	@Test
	public void testForbid2UndosInARow() {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance("kitten".toCharArray());
		
		c.append('s');
		c.append('i');
		c.append('t');

		c.undo();
		try {
			c.undo();
			fail("Should have thrown " + IllegalStateException.class);
		} catch (IllegalStateException e) {}
	}

	@Test
	public void testAllowUndoAfterFirstChar() {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance("kitten".toCharArray());
		
		c.append('s');
		c.undo();
	}

	@Test
	public void testForbidUndoBeforeFirstChar() {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance("kitten".toCharArray());
		
		try {
			c.undo();
			fail("Should have thrown " + IllegalStateException.class);
		} catch (IllegalStateException e) {}
	}

	private Method getMethod(String methodName) {
		Method method;
		try {
			method = AdaptativeDamerauLevenshteinDistance.class.getMethod(methodName);
			if (!int.class.isAssignableFrom(method.getReturnType()))
				throw new IllegalArgumentException(methodName + " should be assignable to int");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return method;
	}
	
	private int[] runAndCollect(AdaptativeDamerauLevenshteinDistance c, String text, String methodName) {
		return runAndCollect(c, text, methodName, 0);
	}
	
	private int[] runAndCollect(AdaptativeDamerauLevenshteinDistance c, String text, String methodName, int skipMeasurements) {
		return runAndCollect0(c, text, getMethod(methodName), skipMeasurements);
	}

	private int[] runAndCollect0(AdaptativeDamerauLevenshteinDistance c, String text, Method method, int skipMeasurements) {
		int[] result = new int[text.length() + 1 - skipMeasurements];

		try {
			int i;
			
			for (i = 0; i < skipMeasurements; i++) {
				c.append(text.charAt(i));
			}
			
			text = text.substring(i);
			
			for (i = 0; i < text.length(); i++) {
				result[i] = (Integer)method.invoke(c);
				c.append(text.charAt(i));
				
			}
			
			result[i] = (Integer)method.invoke(c);
			
			return result;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private void testDistance(String key, String text, int... expectedDistance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		assertThat(runAndCollect(c, text, "getDistance"), is(equalTo(expectedDistance)));
	}

	private void testDistanceCustomCosts(int matchcost, int substcost, int insertcost, int delcost, int transpcost, String key, String text, int... expectedDistance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray(), matchcost, substcost, insertcost, delcost, transpcost);
		
		assertThat(runAndCollect(c, text, "getDistance"), is(equalTo(expectedDistance)));
	}

	private void testDistanceAdvancedCostModel(Cost1[] matchcosts, Cost2[] substcosts, Cost1[] insertcosts, Cost1[] delcosts, Cost2[] transpcosts, String key, String text, int... expectedDistance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray(), matchcosts, substcosts, insertcosts, delcosts, transpcosts);
		
		assertThat(runAndCollect(c, text, "getDistance"), is(equalTo(expectedDistance)));
	}
	
	private void testPastDistance(String key, String text, int... expectedPastDistance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		assertThat(runAndCollect(c, text, "getPastDistance", 1), is(equalTo(expectedPastDistance)));
	}

	private void testMinimalDistance(String key, String text, int... expectedMinimalDistance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		assertThat(runAndCollect(c, text, "getMinimalDistance"), is(equalTo(expectedMinimalDistance)));
	}

	private void testMinimalFutureDistance(String key, String text, int... expectedMinimalFutureDistance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		assertThat(runAndCollect(c, text, "getMinimalFutureDistance"), is(equalTo(expectedMinimalFutureDistance)));
	}
	
	private void testUndo(String key, String text, int start, int... distance) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		assertThat(c.getDistance(), is(equalTo(start)));
		
		c.append(text.charAt(0));
		assertThat(c.getDistance(), is(equalTo(distance[0])));

		for (int i = 1; i < distance.length; i++) {
			c.append(text.charAt(i));
			assertThat(c.getDistance(), is(equalTo(distance[i])));
			c.undo();
			assertThat(c.getDistance(), is(equalTo(distance[i-1])));
			c.append(text.charAt(i));
			assertThat(c.getDistance(), is(equalTo(distance[i])));
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

	private void testUndoOverAlignments(String key, String text, AlignOp[]... alignments) {
		AdaptativeDamerauLevenshteinDistance c = new AdaptativeDamerauLevenshteinDistance(key.toCharArray());
		
		c.append(text.charAt(0));

		for (int i = 1; i < text.length(); i++) {
			c.append(text.charAt(i));
			c.undo();
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
