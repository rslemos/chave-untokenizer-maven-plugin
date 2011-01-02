package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Test;

public class ParserUnitTest {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private static final URL basedir = ParserUnitTest.class.getResource("/pt_BR/CHAVEFolha/1994/01/01/");
	
	@Test
	public void testChave19940101_001() throws Exception {
		Handler handler = mock(Handler.class);
		
		boolean b = new Parser(handler).parse(open("001.cg"), open("001.sgml"));
		assertTrue(b);
	}
	
	private static Reader open(String res) throws MalformedURLException, IOException {
		return open(new URL(basedir, res));
	}
	
	private static Reader open(URL url) throws IOException {
		return new InputStreamReader(url.openStream(), UTF8);
	}
}
