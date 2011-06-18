package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

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
		runOver("), ");
		
		verifyMatches(match(1, 2, span(1, 2, 0)));
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
		
		verifyMatches(
				match(0, "sra".length(), span(0, "sra".length(), 0)),
				match("sra".length(), "sra.".length(), span("sra".length(), "sra.".length(), 1))
			);
	}

	@Test
	public void testManyMatchesOfSingleWord() throws Exception {
		cg.add("feita");
		runOver("feita feita feita feita");
		
		verifyMatches(
				match("feita ".length()*0, "feita ".length()*1 - 1, span("feita ".length()*0, "feita ".length()*1 - 1, 0)),
				match("feita ".length()*1, "feita ".length()*2 - 1, span("feita ".length()*1, "feita ".length()*2 - 1, 0)),
				match("feita ".length()*2, "feita ".length()*3 - 1, span("feita ".length()*2, "feita ".length()*3 - 1, 0)),
				match("feita ".length()*3, "feita ".length()*4 - 1, span("feita ".length()*3, "feita ".length()*4 - 1, 0))
			);
	}

	@Test
	public void testManyMatchesOfTwoWords() throws Exception {
		cg.add("feita");
		cg.add("velho");

		runOver("velho feita velho feita");
		
		verifyMatches(
				match("feit_ ".length()*0, "feit_ ".length()*1 - 1, span("feit_ ".length()*0, "feit_ ".length()*1 - 1, 1)),
				match("feit_ ".length()*1, "feit_ ".length()*2 - 1, span("feit_ ".length()*1, "feit_ ".length()*2 - 1, 0)),
				match("feit_ ".length()*2, "feit_ ".length()*3 - 1, span("feit_ ".length()*2, "feit_ ".length()*3 - 1, 1)),
				match("feit_ ".length()*3, "feit_ ".length()*4 - 1, span("feit_ ".length()*3, "feit_ ".length()*4 - 1, 0))
			);
	}
}
