package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

import br.eti.rslemos.nlp.corpora.chave.parser.ClumsySGMLLexer.Event;

public class ClumsySGMLLexerUnitTest {
	
	private ClumsySGMLLexer parser;

	@Test
	public void testCharactersOnly() throws Exception {
		final String TEXT = "this is a text-only SGML";
		parser = new ClumsySGMLLexer(reader(TEXT));

		assertNextIsCharacters(TEXT);
		assertNextIsnot();
	}

	@Test
	public void testTagOnly() throws Exception {
		final String TEXT = "<A TAG>";
		
		parser = new ClumsySGMLLexer(reader(TEXT));
		
		assertNextIsTag("A TAG");
		assertNextIsnot();
	}

	@Test
	public void testEmptyTag() throws Exception {
		// the only chance for a tag to be empty is to open it at EOF
		final String TEXT = "<";
		
		parser = new ClumsySGMLLexer(reader(TEXT));
		
		assertNextIsTag("");
		assertNextIsnot();
	}

	@Test
	public void testWhitespaceOnly() throws Exception {
		final String TEXT = "  \t\n\n\t\t\t\t\n\n\n\r\r\r\n\t\n\t\n    \t  \t \n \t\t\n   \r\r\n\r     ";
		
		parser = new ClumsySGMLLexer(reader(TEXT));
		
		assertNextIsWhitespace(TEXT);
		assertNextIsnot();
	}

	@Test
	public void testTagAndText() throws Exception {
		final String TEXT = "<A TAG>then text</A TAG>";
		
		parser = new ClumsySGMLLexer(reader(TEXT));
		
		assertNextIsTag("A TAG");
		assertNextIsCharacters("then text");
		assertNextIsTag("/A TAG");
		assertNextIsnot();
	}

	@Test
	public void testReallisticSGML() throws Exception {
		final String TEXT = "<A TAG>then text</A TAG>\n" +
				"<ANOTHER TAG>with text</ANOTHER TAG>\n" +
				"<A CLOSED TAG/>\n" +
				"<TAG1><TAG2>  \t<TAG3>\n" +
				"textextext</TAG3></TAG2></TAG1>";
		
		parser = new ClumsySGMLLexer(reader(TEXT));

		assertNextIsTag("A TAG");
		assertNextIsCharacters("then text");
		assertNextIsTag("/A TAG");
		assertNextIsWhitespace("\n");
		assertNextIsTag("ANOTHER TAG");
		assertNextIsCharacters("with text");
		assertNextIsTag("/ANOTHER TAG");
		assertNextIsWhitespace("\n");
		assertNextIsTag("A CLOSED TAG/");
		assertNextIsWhitespace("\n");
		assertNextIsTag("TAG1");
		assertNextIsTag("TAG2");
		assertNextIsWhitespace("  \t");
		assertNextIsTag("TAG3");
		assertNextIsWhitespace("\n");
		assertNextIsCharacters("textextext");
		assertNextIsTag("/TAG3");
		assertNextIsTag("/TAG2");
		assertNextIsTag("/TAG1");
		assertNextIsnot();

	}

	@Test
	public void testReallisticSGMLWithReallySmallBuffer() throws Exception {
		final String TEXT = "<A TAG>then text</A TAG>\n" +
				"<ANOTHER TAG>with text</ANOTHER TAG>\n" +
				"<A CLOSED TAG/>\n" +
				"<TAG1><TAG2>  \t<TAG3>\n" +
				"textextext</TAG3></TAG2></TAG1>";
		
		parser = new ClumsySGMLLexer(reader(TEXT), 1);

		assertNextIsTag("A TAG");
		assertNextIsCharacters("then text");
		assertNextIsTag("/A TAG");
		assertNextIsWhitespace("\n");
		assertNextIsTag("ANOTHER TAG");
		assertNextIsCharacters("with text");
		assertNextIsTag("/ANOTHER TAG");
		assertNextIsWhitespace("\n");
		assertNextIsTag("A CLOSED TAG/");
		assertNextIsWhitespace("\n");
		assertNextIsTag("TAG1");
		assertNextIsTag("TAG2");
		assertNextIsWhitespace("  \t");
		assertNextIsTag("TAG3");
		assertNextIsWhitespace("\n");
		assertNextIsCharacters("textextext");
		assertNextIsTag("/TAG3");
		assertNextIsTag("/TAG2");
		assertNextIsTag("/TAG1");
		assertNextIsnot();

	}

	@Test
	public void testReallisticSGMLSkippingWhitspace() throws Exception {
		final String TEXT = "<A TAG>then text</A TAG>\n" +
				"<ANOTHER TAG>with text</ANOTHER TAG>\n" +
				"<A CLOSED TAG/>\n" +
				"<TAG1><TAG2>  \t<TAG3>\n" +
				"textextext</TAG3></TAG2></TAG1>";
		
		parser = new ClumsySGMLLexer(reader(TEXT));
		parser.filter(Event.WHITESPACE);
		
		assertNextIsTag("A TAG");
		assertNextIsCharacters("then text");
		assertNextIsTag("/A TAG");
		assertNextIsTag("ANOTHER TAG");
		assertNextIsCharacters("with text");
		assertNextIsTag("/ANOTHER TAG");
		assertNextIsTag("A CLOSED TAG/");
		assertNextIsTag("TAG1");
		assertNextIsTag("TAG2");
		assertNextIsTag("TAG3");
		assertNextIsCharacters("textextext");
		assertNextIsTag("/TAG3");
		assertNextIsTag("/TAG2");
		assertNextIsTag("/TAG1");
		assertNextIsnot();

	}

	@Test
	public void testFilterLastEvent() throws Exception {
		final String TEXT = "<A TAG>\n";
		
		parser = new ClumsySGMLLexer(reader(TEXT));
		parser.filter(Event.WHITESPACE);
		
		assertNextIsTag("A TAG");
		assertNextIsnot();

	}
	
	private void assertNextIsCharacters(String characters) throws IOException {
		assertNextIs(ClumsySGMLLexer.Event.CHARACTERS);
		assertThat(parser.getCharacters(), is(equalTo(characters)));
	}

	private void assertNextIsTag(String localName) throws IOException {
		assertNextIs(ClumsySGMLLexer.Event.TAG);
		assertThat(parser.getLocalName(), is(equalTo(localName)));
	}
	
	private void assertNextIsWhitespace(String whitespace) throws IOException {
		assertNextIs(ClumsySGMLLexer.Event.WHITESPACE);
		assertThat(parser.getCharacters(), is(equalTo(whitespace)));
	}
	
	private void assertNextIs(Event event) throws IOException {
		assertThat(parser.hasNext(), is(equalTo(true)));
		assertThat(parser.next(), is(equalTo(event)));
	}

	private void assertNextIsnot() throws IOException {
		assertThat(parser.hasNext(), is(equalTo(false)));
	}

	private static Reader reader(String text) {
		return new StringReader(text);
	}
}
