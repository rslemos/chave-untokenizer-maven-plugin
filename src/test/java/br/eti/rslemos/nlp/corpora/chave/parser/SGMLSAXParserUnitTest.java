package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SGMLSAXParserUnitTest {
	@Mock
	private SGMLSAXHandler handler;
	private SGMLSAXParser parser;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		parser = new SGMLSAXParser(handler);
		
	}
	
	@Test
	public void testTextOnly() throws Exception {
		final String TEXT = "this is a text-only SGML";
		
		parser.parse(new StringReader(TEXT));

		verify(handler).text(TEXT);
		verifyNoMoreInteractions(handler);
	}

	@Test
	public void testTagOnly() throws Exception {
		final String TEXT = "<A TAG>";
		
		parser.parse(new StringReader(TEXT));

		verify(handler).tag("A TAG");
		verifyNoMoreInteractions(handler);
	}

	@Test
	public void testWhitespaceOnly() throws Exception {
		final String TEXT = "  \t\n\n\t\t\t\t\n\n\n\r\r\r\n\t\n\t\n    \t  \t \n \t\t\n   \r\r\n\r     ";
		
		parser.parse(new StringReader(TEXT));

		verify(handler).whitespace(TEXT);
		verifyNoMoreInteractions(handler);
	}
	
	@Test
	public void testTagAndText() throws Exception {
		final String TEXT = "<A TAG>then text</A TAG>";
		
		parser.parse(new StringReader(TEXT));

		InOrder order = inOrder(handler);
		
		order.verify(handler).tag("A TAG");
		order.verify(handler).text("then text");
		order.verify(handler).tag("/A TAG");
		
		verifyNoMoreInteractions(handler);
	}
}
