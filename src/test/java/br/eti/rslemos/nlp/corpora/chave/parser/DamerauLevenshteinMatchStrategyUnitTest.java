package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class DamerauLevenshteinMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	@Before
	public void setUp() {
		super.setUp(new DamerauLevenshteinMatchStrategy(2));
	}
	
	@Test
	public void testSingleWordDistance1() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("3º.", " [3º.] ADJ M/F S <NUM-ord> @N<PRED"));
		matchAndApply(cg, "3.º");
		
		verify(handler).startToken(cg.get(0).getValue());
		verify(handler).characters("3.º".toCharArray());
		verify(handler).endToken();
	}
	
	@Test
	public void testMatchTillWhitespace() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("3º.", " [3º.] ADJ M/F S <NUM-ord> @N<PRED"));
		matchAndApply(cg, "3. º");
		
		verify(handler).startToken(cg.get(0).getValue());
		verify(handler).characters("3.".toCharArray());
		verify(handler).endToken();
	}
	
	@Test
	public void testDontMatchEmpty() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("o", " [o] DET M S <artd> @>N"));
		noMatch(cg, " o");

		verifyNoMoreInteractions(handler);
	}

	@Test
	public void testDontMatchEmpty2() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("em", " [em] PRP @N<"));
		noMatch(cg, " em");

		verifyNoMoreInteractions(handler);
	}

	@Test
	public void testDontMatchSingleChar() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("o", " [o] DET M S <artd> @>N"));
		noMatch(cg, "\"");

		verifyNoMoreInteractions(handler);
	}


	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("sra.", " [sra.] N F S @P<"),
				new Parser.Entry<String, String>("$.", " [$.] PU <<<")
			);

		matchAndApply(cg, "sra.");
		
		verify(handler).startToken(cg.get(0).getValue());
		verify(handler).characters("sra".toCharArray());
		verify(handler).endToken();
	}
}
