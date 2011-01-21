package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry.entry;
import static junit.framework.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class DirectMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	@Before
	public void setUp() {
		super.setUp(new DirectMatchStrategy());
	}
	
	@Test
	public void testSingleWord() throws Exception {
		cg.add(entry("feita", " [fazer] V PCP F S @IMV @#ICL-N<"));
		MatchResult result = match("feita");
		
		verifyTokensInSequence(result, "feita");
	}

	@Test
	public void testCompositeWord() throws Exception {
		cg.add(entry("Pesquisa=Datafolha", " [Pesquisa=Datafolha] PROP F S @SUBJ>"));
		MatchResult result = match("Pesquisa Datafolha");
		
		verifyTokensInSequence(result, "Pesquisa Datafolha");
	}

	@Test
	public void testCompositeWordWithMoreWhitespaces() throws Exception {
		cg.add(entry("Pesquisa=Datafolha", " [Pesquisa=Datafolha] PROP F S @SUBJ>"));
		MatchResult result = match("Pesquisa   \t\t \t  \t Datafolha");
		
		verifyTokensInSequence(result, "Pesquisa   \t\t \t  \t Datafolha");
	}

	@Test
	public void testPunctuationMark() throws Exception {
		cg.add(entry("$,", (String)null));
		MatchResult result = match(",");
		
		verifyTokensInSequence(result, ",");
	}

	@Test
	public void testEmptyInput() throws Exception {
		cg.add(entry("Pesquisa=Datafolha", " [Pesquisa=Datafolha] PROP F S @SUBJ>"));
		assertNull(match(""));

		verifyNoToken();
	}

	@Test
	public void testDirtyEntry() throws Exception {
		cg.add(entry("checks ALT xxxs", " [check] N M P <*1> <*1> @P<"));
		MatchResult result = match("checks");
		
		verifyTokensInSequence(result, "checks");
	}

	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		cg.add(entry("sra.", " [sra.] N F S @P<"));
		cg.add(entry("$.", " [$.] PU <<<"));

		MatchResult result = match("sra.");
		
		verifyTokensInSequence(result, "sra");
	}

}
