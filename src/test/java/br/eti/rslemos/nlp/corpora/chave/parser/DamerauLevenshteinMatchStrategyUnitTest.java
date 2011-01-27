package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;
import static junit.framework.Assert.assertNull;

import org.junit.Test;

public class DamerauLevenshteinMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public DamerauLevenshteinMatchStrategyUnitTest() {
		super(new DamerauLevenshteinMatchStrategy(2));
	}
	
	@Test
	public void testSingleWordDistance1() throws Exception {
		cg.add("3º.");
		Match result = runOver("3.º");
		
		verifyMatch(result, 0, 3,
				span(0, 3, 0)
			);
	}
	
	@Test
	public void testMatchTillWhitespace() throws Exception {
		cg.add("3º.");
		Match result = runOver("3. º");
		
		verifyMatch(result, 0, 2,
				span(0, 2, 0)
			);
	}
	
	@Test
	public void testDontMatchEmpty() throws Exception {
		cg.add("o");
		assertNull(runOver(" o"));
	}

	@Test
	public void testDontMatchEmpty2() throws Exception {
		cg.add("em");
		assertNull(runOver(" em"));
	}

	@Test
	public void testDontMatchSingleChar() throws Exception {
		cg.add("o");
		assertNull(runOver("\""));
	}


	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		cg.add("sra.");
		cg.add("$.");

		Match result = runOver("sra.");
		
		verifyMatch(result, 0, 3,
				span(0, 3, 0)
			);
	}
}
