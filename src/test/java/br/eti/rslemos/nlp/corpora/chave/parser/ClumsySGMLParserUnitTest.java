package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

public class ClumsySGMLParserUnitTest {
	
	@Test
	public void testCharactersOnly() throws Exception {
		final String TEXT = "this is a text-only SGML";
		ClumsySGMLParser parser = new ClumsySGMLParser(reader(TEXT));
		
		assertThat(parser.hasNext(), is(equalTo(true)));
		assertThat(parser.next(), is(equalTo(ClumsySGMLParser.Event.CHARACTERS)));
		assertThat(parser.getCharacters(), is(equalTo(TEXT)));
		assertThat(parser.hasNext(), is(equalTo(false)));
	}

	@Test
	public void testTagOnly() throws Exception {
		final String TEXT = "<A TAG>";
		
		ClumsySGMLParser parser = new ClumsySGMLParser(reader(TEXT));
		
		assertThat(parser.hasNext(), is(equalTo(true)));
		assertThat(parser.next(), is(equalTo(ClumsySGMLParser.Event.TAG)));
		assertThat(parser.getLocalName(), is(equalTo("A TAG")));
		assertThat(parser.hasNext(), is(equalTo(false)));
	}

	@Test
	public void testWhitespaceOnly() throws Exception {
		final String TEXT = "  \t\n\n\t\t\t\t\n\n\n\r\r\r\n\t\n\t\n    \t  \t \n \t\t\n   \r\r\n\r     ";
		
		ClumsySGMLParser parser = new ClumsySGMLParser(reader(TEXT));
		
		assertThat(parser.hasNext(), is(equalTo(true)));
		assertThat(parser.next(), is(equalTo(ClumsySGMLParser.Event.WHITESPACE)));
		assertThat(parser.getCharacters(), is(equalTo(TEXT)));
		assertThat(parser.hasNext(), is(equalTo(false)));
	}
	
	@Test
	public void testTagAndText() throws Exception {
		final String TEXT = "<A TAG>then text</A TAG>";
		
		ClumsySGMLParser parser = new ClumsySGMLParser(reader(TEXT));
		
		assertThat(parser.hasNext(), is(equalTo(true)));
		assertThat(parser.next(), is(equalTo(ClumsySGMLParser.Event.TAG)));
		assertThat(parser.getLocalName(), is(equalTo("A TAG")));
		assertThat(parser.hasNext(), is(equalTo(true)));
		assertThat(parser.next(), is(equalTo(ClumsySGMLParser.Event.CHARACTERS)));
		assertThat(parser.getCharacters(), is(equalTo("then text")));
		assertThat(parser.hasNext(), is(equalTo(true)));
		assertThat(parser.next(), is(equalTo(ClumsySGMLParser.Event.TAG)));
		assertThat(parser.getLocalName(), is(equalTo("/A TAG")));
		assertThat(parser.hasNext(), is(equalTo(false)));
	}
	
	private static Reader reader(String text) {
		return new StringReader(text);
	}
}
