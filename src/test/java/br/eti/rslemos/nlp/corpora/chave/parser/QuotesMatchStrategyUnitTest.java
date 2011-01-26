package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class QuotesMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public QuotesMatchStrategyUnitTest() {
		super(new QuotesMatchStrategy());
	}
	
	@Test
	public void testDoubleQuotesMatchesKey() throws Exception {
		cg.add("$\"");
		
		Match result = match("\"abcd");
		
		verifyTokensInSequence(result, "\"");
	}
	
	@Test
	public void testDoubleQuotesMatchesNoKey() throws Exception {
		Match result = match("\"abcd");
		
		verifyTextButNoToken(result, "\"");
	}
}
