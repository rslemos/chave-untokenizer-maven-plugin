package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CachingTextMatcherUnitTest {

	private int from;
	private int to;
	
	private int[] rand = new int[20];
	
	private TextMatcher concreteTextMatcher;
	private CachingTextMatcher cachingTextMatcher;

	@Before
	public void setUp() {
		concreteTextMatcher = mock(TextMatcher.class);
		cachingTextMatcher = new CachingTextMatcher(concreteTextMatcher);
		
		for (int i = 0; i < rand.length; i++) {
			rand[i] = (int) (Math.random() * 10000);
		}
		
		Arrays.sort(rand);
		
		from = rand[0];
		to = rand[rand.length - 1];
	}
	
	@Test
	public void testCacheMatchKey() {
		String key1 = "key1";
		
		Span[] spans1 = new Span[1];
		
		Set<Match> set11 = generateMatchSet(from, to);
		
		when(concreteTextMatcher.matchKey(from, to, key1, spans1)).thenReturn(set11);
		
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans1);
	}
	
	@Test
	public void testCacheMatchKeyConsideringSpan() {
		String key1 = "key1";
		
		Span[] spans1 = new Span[1];
		Span[] spans2 = new Span[2];
		
		Set<Match> set11 = generateMatchSet(from, to/2);
		Set<Match> set12 = generateMatchSet(from, to/1);
		
		when(concreteTextMatcher.matchKey(from, to, key1, spans1)).thenReturn(set11);
		when(concreteTextMatcher.matchKey(from, to, key1, spans2)).thenReturn(set12);
		
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(equalTo(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(equalTo(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(equalTo(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(equalTo(set12)));
		
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans1);
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans2);
	}
	
	@Test
	public void testCacheMatchKeyFull() {
		String key1 = "key1";
		String key2 = "key2";
		
		Span[] spans1 = new Span[1];
		Span[] spans2 = new Span[2];
		
		Set<Match> set11 = generateMatchSet(from, to/4);
		Set<Match> set12 = generateMatchSet(from, to/3);
		Set<Match> set21 = generateMatchSet(from, to/2);
		Set<Match> set22 = generateMatchSet(from, to/1);
		
		when(concreteTextMatcher.matchKey(from, to, key1, spans1)).thenReturn(set11);
		when(concreteTextMatcher.matchKey(from, to, key1, spans2)).thenReturn(set12);
		when(concreteTextMatcher.matchKey(from, to, key2, spans1)).thenReturn(set21);
		when(concreteTextMatcher.matchKey(from, to, key2, spans2)).thenReturn(set22);
		
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(equalTo(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans1), is(equalTo(set21)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans2), is(equalTo(set22)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(equalTo(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans1), is(equalTo(set21)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans2), is(equalTo(set22)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans1), is(equalTo(set11)));
		assertThat(cachingTextMatcher.matchKey(from, to, key1, spans2), is(equalTo(set12)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans1), is(equalTo(set21)));
		assertThat(cachingTextMatcher.matchKey(from, to, key2, spans2), is(equalTo(set22)));
		
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans1);
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key1, spans2);
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key2, spans1);
		verify(concreteTextMatcher, times(1)).matchKey(from, to, key2, spans2);
	}
	
	@Test
	public void testCacheMatchKeyOverNonOverlappingFragments() {
		int fromA = rand[0];
		int toA = rand[rand.length/2 - 1];
		int fromB = rand[rand.length/2 - 1];
		int toB = rand[rand.length - 1];
		
		String key1 = "key1";
		
		Span[] spans1 = new Span[1];
		
		Set<Match> set1a = generateMatchSet(fromA, toA);
		Set<Match> set1b = generateMatchSet(fromB, toB);
		
		when(concreteTextMatcher.matchKey(fromA, toA, key1, spans1)).thenReturn(set1a);
		when(concreteTextMatcher.matchKey(fromB, toB, key1, spans1)).thenReturn(set1b);
		
		assertThat(cachingTextMatcher.matchKey(fromA, toA, key1, spans1), is(equalTo(set1a)));
		assertThat(cachingTextMatcher.matchKey(fromB, toB, key1, spans1), is(equalTo(set1b)));
		assertThat(cachingTextMatcher.matchKey(fromA, toA, key1, spans1), is(equalTo(set1a)));
		assertThat(cachingTextMatcher.matchKey(fromB, toB, key1, spans1), is(equalTo(set1b)));
		assertThat(cachingTextMatcher.matchKey(fromA, toA, key1, spans1), is(equalTo(set1a)));
		assertThat(cachingTextMatcher.matchKey(fromB, toB, key1, spans1), is(equalTo(set1b)));
		assertThat(cachingTextMatcher.matchKey(fromA, toA, key1, spans1), is(equalTo(set1a)));
		assertThat(cachingTextMatcher.matchKey(fromB, toB, key1, spans1), is(equalTo(set1b)));
		
		verify(concreteTextMatcher, times(1)).matchKey(fromA, toA, key1, spans1);
		verify(concreteTextMatcher, times(1)).matchKey(fromB, toB, key1, spans1);
	}

	@Test
	public void testCacheMatchKeyOverOverlappingFragments() {
		int fromA = rand[0];
		int toA = rand[rand.length*2/4 - 1];
		int fromB = rand[rand.length*1/4 - 1];
		int toB = rand[rand.length*3/4 - 1];
		int fromC = rand[rand.length*2/4 - 1];
		int toC = rand[rand.length - 1];
		
		String key1 = "key1";
		
		Span[] spans1 = new Span[1];
		
		Set<Match> set1a = generateMatchSet(fromA, toA);
		Set<Match> set1b = generateMatchSet(fromB, toB);
		Set<Match> set1c = generateMatchSet(fromC, toC);
		
		when(concreteTextMatcher.matchKey(fromA, toA, key1, spans1)).thenReturn(set1a);
		when(concreteTextMatcher.matchKey(toA, toB, key1, spans1)).thenReturn(difference(set1b, set1a));
		when(concreteTextMatcher.matchKey(toB, toC, key1, spans1)).thenReturn(difference(set1c, set1b));
		
		assertThat(cachingTextMatcher.matchKey(fromA, toA, key1, spans1), is(equalTo(set1a)));
		assertThat(cachingTextMatcher.matchKey(fromB, toB, key1, spans1), is(equalTo(set1b)));
		assertThat(cachingTextMatcher.matchKey(fromC, toC, key1, spans1), is(equalTo(set1c)));
		assertThat(cachingTextMatcher.matchKey(fromA, toA, key1, spans1), is(equalTo(set1a)));
		assertThat(cachingTextMatcher.matchKey(fromB, toB, key1, spans1), is(equalTo(set1b)));
		assertThat(cachingTextMatcher.matchKey(fromC, toC, key1, spans1), is(equalTo(set1c)));
		assertThat(cachingTextMatcher.matchKey(fromA, toA, key1, spans1), is(equalTo(set1a)));
		assertThat(cachingTextMatcher.matchKey(fromB, toB, key1, spans1), is(equalTo(set1b)));
		assertThat(cachingTextMatcher.matchKey(fromC, toC, key1, spans1), is(equalTo(set1c)));
		
		verify(concreteTextMatcher, times(1)).matchKey(fromA, toA, key1, spans1);
		verify(concreteTextMatcher, times(1)).matchKey(toA, toB, key1, spans1);
		verify(concreteTextMatcher, times(1)).matchKey(toB, toC, key1, spans1);
	}

	private Set<Match> generateMatchSet(int from0, int to0) {
		Set<Match> result = new HashSet<Match>();

		int i = Arrays.binarySearch(rand, from0);
		int j = Arrays.binarySearch(rand, to0);
		
		if (i < 0)
			i = -(i + 1);
		
		if (j < 0)
			j = -(j + 1);
		
		i &= ~1;
		j &= ~1;
		
		for (; i + 1 < j; i += 2) {
			result.add(Match.match(rand[i], rand[i+1]));
		}
		
		return result;
	}
	
	private static Set<Match> difference(Set<Match> a, Set<Match> b) {
		Set<Match> result = new LinkedHashSet<Match>(a);
		
		result.removeAll(b);
		
		return result;
	}

}
