package br.eti.rslemos.nlp.corpora.chave.parser;

import static junit.framework.Assert.assertNull;

import org.junit.Test;

public class DamerauLevenshteinMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public DamerauLevenshteinMatchStrategyUnitTest() {
		super(new DamerauLevenshteinMatchStrategy(2));
	}
	
	@Test
	public void testSingleWordDistance1() throws Exception {
		cg.add("3º.");
		MatchResult result = match("3.º");
		
		verifyTokensInSequence(result, "3.º");
	}
	
	@Test
	public void testMatchTillWhitespace() throws Exception {
		cg.add("3º.");
		MatchResult result = match("3. º");
		
		verifyTokensInSequence(result, "3.");
	}
	
	@Test
	public void testDontMatchEmpty() throws Exception {
		cg.add("o");
		assertNull(match(" o"));
	}

	@Test
	public void testDontMatchEmpty2() throws Exception {
		cg.add("em");
		assertNull(match(" em"));
	}

	@Test
	public void testDontMatchSingleChar() throws Exception {
		cg.add("o");
		assertNull(match("\""));
	}


	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		cg.add("sra.");
		cg.add("$.");

		MatchResult result = match("sra.");
		
		verifyTokensInSequence(result, "sra");
	}
}
