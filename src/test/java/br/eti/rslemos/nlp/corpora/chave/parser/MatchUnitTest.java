package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.MatchResult.result;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.eti.rslemos.nlp.corpora.chave.parser.MatchResult.Match;

public class MatchUnitTest {
	@Test
	public void testEquals() {
		Match a0 = result(0, 0, 0);
		Match a1 = result(0, 0, 0);
		
		assertThat(a0.equals(a1), is(true));
	}
}
