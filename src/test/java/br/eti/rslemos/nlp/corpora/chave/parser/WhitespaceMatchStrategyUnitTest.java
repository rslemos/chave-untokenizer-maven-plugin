package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Before;
import org.junit.Test;

public class WhitespaceMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	@Before
	public void setUp() {
		super.setUp(new WhitespaceMatchStrategy());
	}
	
	@Test
	public void testWhitespace() throws Exception {
		MatchResult result = match("   \t\t   \t   abcd");
		
		verifyTextButNoToken(result, "   \t\t   \t   ");
	}
	
	@Test
	public void testFinalWhitespace() throws Exception {
		MatchResult result = match("   \t\t   \t   ");
		
		verifyTextButNoToken(result, "   \t\t   \t   ");
	}
}
