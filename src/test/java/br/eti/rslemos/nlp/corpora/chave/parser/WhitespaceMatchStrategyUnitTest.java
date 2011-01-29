package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;

import org.junit.Test;

public class WhitespaceMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public WhitespaceMatchStrategyUnitTest() {
		super(new WhitespaceMatchStrategy());
	}
	
	@Test
	public void testWhitespace() throws Exception {
		runOver("   \t\t   \t   abcd");
		
		verifyMatches(match(0, "   \t\t   \t   ".length()));
	}
	
	@Test
	public void testFinalWhitespace() throws Exception {
		runOver("   \t\t   \t   ");
		
		verifyMatches(match(0, "   \t\t   \t   ".length()));
	}
}
