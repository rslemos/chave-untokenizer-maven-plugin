package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MatchUnitTest {
	@Test
	public void testEquals() {
		Match d0 = match(0, 15, span(0, 10, 0), span(5, 15, 1));
		Match d1 = match(0, 15, span(0, 10, 0), span(5, 15, 1));

		assertThat(d0.equals(d1), is(true));
	}
}
