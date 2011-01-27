package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class QuotesMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public QuotesMatchStrategyUnitTest() {
		super(new QuotesMatchStrategy());
	}
	
	@Test
	public void testDoubleQuotesMatchesKey() throws Exception {
		cg.add("$\"");
		
		Match result = runOver("\"abcd");
		
		verifyMatch(result, 0, 1, 
				span(0, 1, 0)
			);
	}
	
	@Test
	public void testDoubleQuotesMatchesNoKey() throws Exception {
		Match result = runOver("\"abcd");
		
		verifyMatch(result, 0, 1);
	}
}
