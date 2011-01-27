package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class NewLineMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public NewLineMatchStrategyUnitTest() {
		super(new NewLineMatchStrategy());
	}
	
	@Test
	public void testNewLine() throws Exception {
		cg.add("$¶");
		Match result = runOver("\n");
		
		verifyMatch(result, 0, 1, 
				span(0, 1, 0)
			);
	}

	@Test
	public void testOptionalNewLine() throws Exception {
		cg.add("$¶");
		Match result = runOver("abcd");
		
		verifyMatch(result, 0, 0, 
				span(0, 0, 0)
			);
	}

	@Test
	public void testEmptyInput() throws Exception {
		cg.add("$¶");
		Match result = runOver("");
		
		verifyMatch(result, 0, 0, 
				span(0, 0, 0)
			);
	}
}
