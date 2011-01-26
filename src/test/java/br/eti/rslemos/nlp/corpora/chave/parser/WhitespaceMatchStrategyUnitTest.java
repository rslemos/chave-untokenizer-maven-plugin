package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class WhitespaceMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public WhitespaceMatchStrategyUnitTest() {
		super(new WhitespaceMatchStrategy());
	}
	
	@Test
	public void testWhitespace() throws Exception {
		Match result = match("   \t\t   \t   abcd");
		
		verifyTextButNoToken(result, "   \t\t   \t   ");
	}
	
	@Test
	public void testFinalWhitespace() throws Exception {
		Match result = match("   \t\t   \t   ");
		
		verifyTextButNoToken(result, "   \t\t   \t   ");
	}
}
