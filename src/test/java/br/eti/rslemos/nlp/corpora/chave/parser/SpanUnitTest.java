package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.result;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;

public class SpanUnitTest {
	@Test
	public void testEquals() {
		Span a0 = result(0, 0, 0);
		Span a1 = result(0, 0, 0);
		
		assertThat(a0.equals(a1), is(true));
	}
}
