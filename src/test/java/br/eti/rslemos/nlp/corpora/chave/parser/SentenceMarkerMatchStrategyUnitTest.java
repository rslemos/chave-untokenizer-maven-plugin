package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class SentenceMarkerMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	@Before
	public void setUp() {
		super.setUp(new SentenceMarkerMatchStrategy());
	}
	
	@Test
	public void testStartSentenceMarker() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("<s>", null));
		matchAndApply(cg, "");

		verifyNoMoreInteractions(handler);
	}
	
	@Test
	public void testEndSentenceMarker() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("</s>", null));
		matchAndApply(cg, "");

		verifyNoMoreInteractions(handler);
	}
}
