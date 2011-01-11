package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class WhitespaceMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	@Before
	public void setUp() {
		super.setUp(new WhitespaceMatchStrategy());
	}
	
	@Test
	public void testWhitespace() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList();
		matchAndApply(cg, "   \t\t   \t   abcd");

		verify(handler).characters("   \t\t   \t   ".toCharArray());
	}
	
	@Test
	public void testFinalWhitespace() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList();
		matchAndApply(cg, "   \t\t   \t   ");

		verify(handler).characters("   \t\t   \t   ".toCharArray());
	}
	
	@Test
	public void testDoubleQuotes() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList();
		matchAndApply(cg, "\"abcd");

		verify(handler).characters("\"".toCharArray());
	}
}
