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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TextMatcherDecoratorUnitTest {
	private TextMatcher concreteTextMatcher;
	private TextMatcher decoratorTextMatcher;
	
	@Before
	public void setUp() {
		concreteTextMatcher = mock(TextMatcher.class);
		decoratorTextMatcher = new TextMatcherDecorator(concreteTextMatcher);
	}
	
	@Test
	public void testForwardMatchKey() {
		int from = (int) (Math.random() * Integer.MAX_VALUE);
		int to = (int) (Math.random() * Integer.MAX_VALUE);
		String key = "key";
		Span[] inSpans = {};
		
		Set<Match> set = new HashSet<Match>();
		
		when(concreteTextMatcher.matchKey(from, to, key, inSpans)).thenReturn(set);
		
		Set<Match> result = decoratorTextMatcher.matchKey(from, to, key, inSpans);
		assertThat(result, is(sameInstance(set)));
		
		verify(concreteTextMatcher).matchKey(from, to, key, inSpans);
	}

	
	@Test
	public void testMatchWordEndOrNewLine() {
		int from = (int) (Math.random() * Integer.MAX_VALUE);
		int to = (int) (Math.random() * Integer.MAX_VALUE);

		Set<Match> set = new HashSet<Match>();
		
		when(concreteTextMatcher.matchWordEndOrNewLine(from, to)).thenReturn(set);
		
		Set<Match> result = decoratorTextMatcher.matchWordEndOrNewLine(from, to);
		assertThat(result, is(sameInstance(set)));
		
		verify(concreteTextMatcher).matchWordEndOrNewLine(from, to);
	}
}
