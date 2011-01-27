package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;
import static junit.framework.Assert.assertNull;

import org.junit.Test;

public class DirectMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public DirectMatchStrategyUnitTest () {
		super(new DirectMatchStrategy());
	}
	
	@Test
	public void testSingleWord() throws Exception {
		cg.add("feita");
		Match result = runOver("feita");
		
		verifyMatch(result, 0, "feita".length(), 
				span(0, "feita".length(), 0)
			);
	}

	@Test
	public void testCompositeWord() throws Exception {
		cg.add("Pesquisa=Datafolha");
		Match result = runOver("Pesquisa Datafolha");
		
		verifyMatch(result, 0, "Pesquisa Datafolha".length(), 
				span(0, "Pesquisa Datafolha".length(), 0)
			);
	}

	@Test
	public void testCompositeWordWithMoreWhitespaces() throws Exception {
		cg.add("Pesquisa=Datafolha");
		Match result = runOver("Pesquisa   \t\t \t  \t Datafolha");
		
		verifyMatch(result, 0, "Pesquisa   \t\t \t  \t Datafolha".length(), 
				span(0, "Pesquisa   \t\t \t  \t Datafolha".length(), 0)
			);
	}

	@Test
	public void testPunctuationMark() throws Exception {
		cg.add("$,");
		Match result = runOver(",");
		
		verifyMatch(result, 0, 1, 
				span(0, 1, 0)
			);
	}

	@Test
	public void testEmptyInput() throws Exception {
		cg.add("Pesquisa=Datafolha");
		assertNull(runOver(""));
	}

	@Test
	public void testDirtyEntry() throws Exception {
		cg.add("checks ALT xxxs");
		Match result = runOver("checks");
		
		verifyMatch(result, 0, "checks".length(), 
				span(0, "checks".length(), 0)
			);
	}

	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		cg.add("sra.");
		cg.add("$.");

		Match result = runOver("sra.");
		
		verifyMatch(result, 0, "sra".length(), 
				span(0, "sra".length(), 0)
			);
	}

}
