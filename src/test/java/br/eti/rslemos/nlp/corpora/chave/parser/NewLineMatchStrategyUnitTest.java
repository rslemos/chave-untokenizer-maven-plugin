package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class NewLineMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	@Before
	public void setUp() {
		super.setUp(new NewLineMatchStrategy());
	}
	
	@Test
	public void testNewLine() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("$¶", " [$¶] PU <<<"));
		matchAndApply(cg, "\n");
		
		verify(handler).startToken(cg.get(0).getValue());
		verify(handler).characters("\n".toCharArray());
		verify(handler).endToken();
	}

	@Test
	public void testOptionalNewLine() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("$¶", " [$¶] PU <<<"));
		matchAndApply(cg, "abcd");
		
		verify(handler).startToken(cg.get(0).getValue());
		verify(handler).characters(new char[0]);
		verify(handler).endToken();
	}
}
