package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class QuotesMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public QuotesMatchStrategyUnitTest() {
		super(new QuotesMatchStrategy());
	}
	
	@Test
	public void testDoubleQuotesMatchesKey() throws Exception {
		cg.add("$\"");
		
		runOver("\"abcd");
		
		verifyMatches(
				match(0, 1),
				match(0, 1, span(0, 1, 0))
			);
	}
	
	@Test
	public void testDoubleQuotesMatchesNoKey() throws Exception {
		runOver("\"abcd");
		
		verifyMatches(match(0, 1));
	}

	@Test
	public void testMatchesAll() throws Exception {
		cg.add("0");
		cg.add("$\"");
		cg.add("other");
		cg.add("$\"");
		cg.add("4");
		cg.add("$\"");
		cg.add("6");
		
		runOver("0 \"other\" 4 \" 6");
		
		verifyMatches(
				match(2, 3),
				match(8, 9),
				match(12, 13),

				match(2, 3, span(2, 3, 1)),
				match(2, 3, span(2, 3, 3)),
				match(2, 3, span(2, 3, 5)),

				match(8, 9, span(8, 9, 1)),
				match(8, 9, span(8, 9, 3)),
				match(8, 9, span(8, 9, 5)),

				match(12, 13, span(12, 13, 1)),
				match(12, 13, span(12, 13, 3)),
				match(12, 13, span(12, 13, 5))
			);
	}
	
}
