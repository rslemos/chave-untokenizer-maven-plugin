package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.verify;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class DirectMatchStrategyUnitTest {

	@Mock
	private Handler handler;
	private DirectMatchStrategy strategy;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		strategy = new DirectMatchStrategy();
	}
	
	@Test
	public void testSingleWord() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("feita", " [fazer] V PCP F S @IMV @#ICL-N<"));
		matchAndApply(cg, "feita");
		
		verify(handler).startToken(cg.get(0).getValue());
		verify(handler).characters("feita".toCharArray());
		verify(handler).endToken();
	}

	@Test
	public void testCompositeWord() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("Pesquisa=Datafolha", " [Pesquisa=Datafolha] PROP F S @SUBJ>"));
		matchAndApply(cg, "Pesquisa Datafolha");
		
		verify(handler).startToken(cg.get(0).getValue());
		verify(handler).characters("Pesquisa Datafolha".toCharArray());
		verify(handler).endToken();
	}

	@Test
	public void testCompositeWordWithMoreWhitespaces() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(new Parser.Entry<String, String>("Pesquisa=Datafolha", " [Pesquisa=Datafolha] PROP F S @SUBJ>"));
		matchAndApply(cg, "Pesquisa   \t\t \t  \t Datafolha");
		
		verify(handler).startToken(cg.get(0).getValue());
		verify(handler).characters("Pesquisa   \t\t \t  \t Datafolha".toCharArray());
		verify(handler).endToken();
	}

	private void matchAndApply(List<Entry<String, String>> cg, String sgml) {
		strategy.match(CharBuffer.wrap(sgml), new LinkedList<Entry<String, String>>(cg)).apply(handler);
	}

}
