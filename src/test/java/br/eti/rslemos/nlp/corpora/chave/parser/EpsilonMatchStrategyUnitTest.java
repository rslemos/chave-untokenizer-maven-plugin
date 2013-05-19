package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class EpsilonMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest<EpsilonMatchStrategy> {

	public EpsilonMatchStrategyUnitTest() {
		super(new EpsilonMatchStrategy());
	}
	
	@Test
	public void testOneMatchOverEmptySpan() {
		cg.add("any");
		
		runOver("");
		
		verifyMatches(match(0, 0, span(0, 0, 0)));
	}

	@Test
	public void testOneMatchOverOneWhitespace() {
		cg.add("any");
		
		runOver(" ");
		
		verifyMatches(
				match(0, 0, span(0, 0, 0)),
				match(1, 1, span(1, 1, 0))
			);
	}
}
