package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class NewLineMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public NewLineMatchStrategyUnitTest() {
		super(new NewLineMatchStrategy());
	}
	
	@Test
	public void testNewLine() throws Exception {
		cg.add("$¶");
		MatchResult result = match("\n");
		
		verifyTokensInSequence(result, "\n");
	}

	@Test
	public void testOptionalNewLine() throws Exception {
		cg.add("$¶");
		MatchResult result = match("abcd");
		
		verifyTokensInSequence(result, "");
	}

	@Test
	public void testEmptyInput() throws Exception {
		cg.add("$¶");
		MatchResult result = match("");
		
		verifyTokensInSequence(result, "");
	}
}
