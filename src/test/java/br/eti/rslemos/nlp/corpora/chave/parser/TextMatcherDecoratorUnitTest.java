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
