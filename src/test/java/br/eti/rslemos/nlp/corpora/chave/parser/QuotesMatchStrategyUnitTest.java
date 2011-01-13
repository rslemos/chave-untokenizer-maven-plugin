package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class QuotesMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	@Before
	public void setUp() {
		super.setUp(new QuotesMatchStrategy());
	}
	
	@Test
	public void testDoubleQuotesMatchesKey() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("$\"", " [$\"] PU")
			);
		
		matchAndApply(cg, "\"abcd");

		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("\"".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testDoubleQuotesMatchesNoKey() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList();
		matchAndApply(cg, "\"abcd");

		InOrder order = inOrder(handler);
		
		order.verify(handler).characters("\"".toCharArray());
		
		verifyNoMoreInteractions(handler);
	}
}
