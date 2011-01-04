package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

public class ClumsySGMLFilterUnitTest {
	
	@Test
	public void testFilter() throws Exception {
		final String INTERESTING_TEXT =
			"\n" +
			"textexttext<WITH TAGS>andtext\n" +
			"<MORE TAGS>      <WHITESPACE TOO>and text\n" +
			"more text\n" +
			"\n" +
			"\n";
		final String TEXT = 
			"<A TAG>\n" + 
			"<ANOTHER ONE>" + 
			"<INTERESTING TAG>" + 
			INTERESTING_TEXT + 
			"</INTERESTING TAG><TAG><MORETAGS>\n";
		
		ClumsySGMLLexer parser = new ClumsySGMLLexer(reader(TEXT));
		
		parser.next();
		ClumsySGMLFilter.skipToTag(parser, "INTERESTING TAG");
		assertThat(parser.getLocalName(), is(equalTo("INTERESTING TAG")));
		
		Reader reader = ClumsySGMLFilter.readToTag(parser, "/INTERESTING TAG");
	}

	private static Reader reader(String text) {
		return new StringReader(text);
	}
}
