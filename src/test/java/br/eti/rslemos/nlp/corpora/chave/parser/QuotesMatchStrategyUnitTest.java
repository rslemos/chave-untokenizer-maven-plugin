package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry.entry;

import org.junit.Before;
import org.junit.Test;

public class QuotesMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	@Before
	public void setUp() {
		super.setUp(new QuotesMatchStrategy());
	}
	
	@Test
	public void testDoubleQuotesMatchesKey() throws Exception {
		cg.add(entry("$\"", " [$\"] PU"));
		
		MatchResult result = match("\"abcd");
		
		verifyTokensInSequence(result, "\"");
	}
	
	@Test
	public void testDoubleQuotesMatchesNoKey() throws Exception {
		MatchResult result = match("\"abcd");
		
		verifyTextButNoToken(result, "\"");
	}
}
