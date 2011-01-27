package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class WhitespaceMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public WhitespaceMatchStrategyUnitTest() {
		super(new WhitespaceMatchStrategy());
	}
	
	@Test
	public void testWhitespace() throws Exception {
		Match result = runOver("   \t\t   \t   abcd");
		
		verifyMatch(result, 0, "   \t\t   \t   ".length());
	}
	
	@Test
	public void testFinalWhitespace() throws Exception {
		Match result = runOver("   \t\t   \t   ");
		
		verifyMatch(result, 0, "   \t\t   \t   ".length());
	}
}
