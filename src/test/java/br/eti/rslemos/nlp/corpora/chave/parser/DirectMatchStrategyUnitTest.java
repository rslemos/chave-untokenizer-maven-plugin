package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class DirectMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public DirectMatchStrategyUnitTest () {
		super(new DirectMatchStrategy());
	}
	
	@Test
	public void testSingleWord() throws Exception {
		cg.add("feita");
		runOver("feita");
		
		verifyMatches(match(0, "feita".length(), span(0, "feita".length(), 0)));
	}

	@Test
	public void testCompositeWord() throws Exception {
		cg.add("Pesquisa=Datafolha");
		runOver("Pesquisa Datafolha");
		
		verifyMatches(match(0, "Pesquisa Datafolha".length(), span(0, "Pesquisa Datafolha".length(), 0)));
	}

	@Test
	public void testCompositeWordWithMoreWhitespaces() throws Exception {
		cg.add("Pesquisa=Datafolha");
		runOver("Pesquisa   \t\t \t  \t Datafolha");
		
		verifyMatches(match(0, "Pesquisa   \t\t \t  \t Datafolha".length(), span(0, "Pesquisa   \t\t \t  \t Datafolha".length(), 0)));
	}

	@Test
	public void testPunctuationMark() throws Exception {
		cg.add("$,");
		runOver(",");
		
		verifyMatches(match(0, 1, span(0, 1, 0)));
	}

	@Test
	public void testEmptyInput() throws Exception {
		cg.add("Pesquisa=Datafolha");
		runOver("");
		verifyMatches();
	}

	@Test
	public void testDirtyEntry() throws Exception {
		cg.add("checks ALT xxxs");
		runOver("checks");
		
		verifyMatches(match(0, "checks".length(), span(0, "checks".length(), 0)));
	}

	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		cg.add("sra.");
		cg.add("$.");

		runOver("sra.");
		
		verifyMatches(match(0, "sra".length(), span(0, "sra".length(), 0)));
	}

}
