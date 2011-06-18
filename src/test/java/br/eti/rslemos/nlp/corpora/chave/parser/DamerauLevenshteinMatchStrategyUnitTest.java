package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class DamerauLevenshteinMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public DamerauLevenshteinMatchStrategyUnitTest() {
		super(new DamerauLevenshteinMatchStrategy(2));
	}
	
	@Test
	public void testSingleWordDistance1() throws Exception {
		cg.add("3º.");
		runOver("3.º");
		
		verifyMatches(match(0, 3, span(0, 3, 0)));
	}
	
	@Test
	public void testMatchTillWhitespace() throws Exception {
		cg.add("3º.");
		runOver("3. º");
		
		verifyMatches(match(0, 2, span(0, 2, 0)));
	}
	
	@Test
	public void testDontMatchEmpty() throws Exception {
		cg.add("o");
		runOver(" o");
		verifyMatches();
	}

	@Test
	public void testDontMatchEmpty2() throws Exception {
		cg.add("em");
		runOver(" em");
		verifyMatches();
	}

	@Test
	public void testDontMatchSingleChar() throws Exception {
		cg.add("o");
		runOver("\"");
		verifyMatches();
	}


	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		cg.add("sra.");
		cg.add("$.");

		runOver("sra.");
		
		verifyMatches(match(0, 3, span(0, 3, 0)));
	}
}
