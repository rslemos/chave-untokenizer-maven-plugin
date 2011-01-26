package br.eti.rslemos.nlp.corpora.chave.parser;

import static junit.framework.Assert.assertNull;

import org.junit.Test;

public class DirectMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public DirectMatchStrategyUnitTest () {
		super(new DirectMatchStrategy());
	}
	
	@Test
	public void testSingleWord() throws Exception {
		cg.add("feita");
		Match result = match("feita");
		
		verifyTokensInSequence(result, "feita");
	}

	@Test
	public void testCompositeWord() throws Exception {
		cg.add("Pesquisa=Datafolha");
		Match result = match("Pesquisa Datafolha");
		
		verifyTokensInSequence(result, "Pesquisa Datafolha");
	}

	@Test
	public void testCompositeWordWithMoreWhitespaces() throws Exception {
		cg.add("Pesquisa=Datafolha");
		Match result = match("Pesquisa   \t\t \t  \t Datafolha");
		
		verifyTokensInSequence(result, "Pesquisa   \t\t \t  \t Datafolha");
	}

	@Test
	public void testPunctuationMark() throws Exception {
		cg.add("$,");
		Match result = match(",");
		
		verifyTokensInSequence(result, ",");
	}

	@Test
	public void testEmptyInput() throws Exception {
		cg.add("Pesquisa=Datafolha");
		assertNull(match(""));
	}

	@Test
	public void testDirtyEntry() throws Exception {
		cg.add("checks ALT xxxs");
		Match result = match("checks");
		
		verifyTokensInSequence(result, "checks");
	}

	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		cg.add("sra.");
		cg.add("$.");

		Match result = match("sra.");
		
		verifyTokensInSequence(result, "sra");
	}

}
