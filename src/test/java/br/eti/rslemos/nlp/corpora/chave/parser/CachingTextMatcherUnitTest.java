package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CachingTextMatcherUnitTest {

	private int from;
	private int to;
	private TextMatcher concreteTextMatcher;
	private CachingTextMatcher cachingTextMatcher;

	@Before
	public void setUp() {
		from = (int) (Math.random() * Integer.MAX_VALUE);
		to = (int) (Math.random() * Integer.MAX_VALUE);
		concreteTextMatcher = mock(TextMatcher.class);
		cachingTextMatcher = new CachingTextMatcher(concreteTextMatcher);
	}
	
	@Test
	public void testCacheMatchKey() {
		String key1 = "key1";
		
		Span[] spans1 = new Span[1];
		
		Set<Match> set11 = new HashSet<Match>();
		
		when(concreteTextMatcher.matchKey(from, to, key1, spans1)).thenReturn(set11);
		
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans1);
	}
	
	@Test
	public void testCacheMatchKeyConsideringSpan() {
		String key1 = "key1";
		
		Span[] spans1 = new Span[1];
		Span[] spans2 = new Span[2];
		
		Set<Match> set11 = new HashSet<Match>();
		Set<Match> set12 = new HashSet<Match>();
		
		when(concreteTextMatcher.matchKey(from, to, key1, spans1)).thenReturn(set11);
		when(concreteTextMatcher.matchKey(from, to, key1, spans2)).thenReturn(set12);
		
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(sameInstance(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(sameInstance(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(sameInstance(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(sameInstance(set12)));
		
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans1);
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans2);
	}
	
	@Test
	public void testCacheMatchKeyFull() {
		String key1 = "key1";
		String key2 = "key2";
		
		Span[] spans1 = new Span[1];
		Span[] spans2 = new Span[2];
		
		Set<Match> set11 = new HashSet<Match>();
		Set<Match> set12 = new HashSet<Match>();
		Set<Match> set21 = new HashSet<Match>();
		Set<Match> set22 = new HashSet<Match>();
		
		when(concreteTextMatcher.matchKey(from, to, key1, spans1)).thenReturn(set11);
		when(concreteTextMatcher.matchKey(from, to, key1, spans2)).thenReturn(set12);
		when(concreteTextMatcher.matchKey(from, to, key2, spans1)).thenReturn(set21);
		when(concreteTextMatcher.matchKey(from, to, key2, spans2)).thenReturn(set22);
		
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(sameInstance(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans1), is(sameInstance(set21)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans2), is(sameInstance(set22)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(sameInstance(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans1), is(sameInstance(set21)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans2), is(sameInstance(set22)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(sameInstance(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(sameInstance(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans1), is(sameInstance(set21)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans2), is(sameInstance(set22)));
		
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans1);
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans2);
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key2, spans1);
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key2, spans2);
	}
}
