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
		String key = "key";
		Span[] inSpans = {};
		
		Set<Match> set = new HashSet<Match>();
		
		when(concreteTextMatcher.matchKey(key, inSpans)).thenReturn(set);
		
		Set<Match> result = decoratorTextMatcher.matchKey(key, inSpans);
		assertThat(result, is(sameInstance(set)));
		
		verify(concreteTextMatcher).matchKey(key, inSpans);
	}

	
	@Test
	public void testMatchWordEndOrNewLine() {
		Set<Match> set = new HashSet<Match>();
		
		when(concreteTextMatcher.matchWordEndOrNewLine()).thenReturn(set);
		
		Set<Match> result = decoratorTextMatcher.matchWordEndOrNewLine();
		assertThat(result, is(sameInstance(set)));
		
		verify(concreteTextMatcher).matchWordEndOrNewLine();
	}
}
