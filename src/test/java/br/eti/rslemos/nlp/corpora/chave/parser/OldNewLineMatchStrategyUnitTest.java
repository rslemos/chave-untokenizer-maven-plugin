package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class OldNewLineMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public OldNewLineMatchStrategyUnitTest() {
		super(new OldNewLineMatchStrategy());
	}
	
	@Test
	public void testNewLine() throws Exception {
		cg.add("$¶");
		runOver("\n");
		
		verifyMatches(match(0, 1, span(0, 1, 0)));
	}

	@Test
	public void testOptionalNewLine() throws Exception {
		cg.add("$¶");
		runOver("abcd");
		
		verifyMatches(match(0, 0, span(0, 0, 0)));
	}

	@Test
	public void testEmptyInput() throws Exception {
		cg.add("$¶");
		runOver("");
		
		verifyMatches(match(0, 0, span(0, 0, 0)));
	}
}
