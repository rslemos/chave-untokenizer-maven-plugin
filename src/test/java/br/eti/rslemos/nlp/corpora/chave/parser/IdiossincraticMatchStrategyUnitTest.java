package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class IdiossincraticMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest<IdiossincraticMatchStrategy> {

	public IdiossincraticMatchStrategyUnitTest() {
		super(new IdiossincraticMatchStrategy());
	}

	@Test
	public void testMatch() {
		strategy.addMatch("d'Água", "dÁgua");
		
		cg.add("dÁgua ALT xxxua");
		
		runOver(" d'Água' ");
		
		verifyMatches(
				match(1, 7, span(1, 7, 0))
			);
		
	}
}
