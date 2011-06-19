package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class NewLineMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public NewLineMatchStrategyUnitTest() {
		super(new NewLineMatchStrategy());
	}
	
	@Test
	public void testNewLine() throws Exception {
		cg.add("$¶");
		runOver("\n");
		
		verifyMatches(match(0, 1, span(0, 1, 0)));
	}
}
