package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class ParserUnitTest {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private static final URL basedir = ParserUnitTest.class.getResource("/pt_BR/CHAVEFolha/1994/01/01/");

	private Handler handler;
	private Parser parser;
	
	@Before
	public void setUp() {
		handler = mock(Handler.class);
		parser = new Parser(handler);
	}

	@Test
	public void testSingleWord() throws Exception {
		String sgmlText = "word";
		String cgLine = "word\t lorem ipsum\n";
		verifyParse(buildCG(reader(cgLine)), buildSGML(sgmlText));
	}

	@Test
	public void testChave19940101_001() throws Exception {
		verifyParse("001.cg", "001.sgml");
	}

	private void verifyParse(String cg, String sgml) throws IOException, MalformedURLException {
		verifyParse(open(cg), open(sgml));
	}

	private void verifyParse(Reader cg, Reader sgml) throws IOException {
		assertTrue(parser.parse(cg, sgml));
	}
	
	private static Reader open(String res) throws MalformedURLException, IOException {
		return open(new URL(basedir, res));
	}
	
	private static Reader open(URL url) throws IOException {
		return new InputStreamReader(url.openStream(), UTF8);
	}

	private static Reader reader(String text) {
		return new StringReader(text);
	}
	
	private static Reader buildSGML(String... sentences) {
		Reader[] readers = new Reader[sentences.length];

		for (int i = 0; i < sentences.length; i++) {
			readers[i] = reader(sentences[i] + "\n");
		}
		
		return buildSGML0(concat(readers));
	}

	private static Reader buildSGML0(Reader contents) {
		Reader header = reader(
				"<DOC>\n" +
				"<DOCNO>FSP940101-001</DOCNO>\n" +
				"<DOCID>FSP940101-001</DOCID>\n" +
				"<DATE>940101</DATE>\n" +
				"<TEXT>\n"
			);
		
		Reader trailer = reader(
				"</TEXT>\n" +
				"</DOC>"
			);
		
		return concat(header, contents, trailer);
	}

	private static Reader buildCG(Reader... sentences) {
		Reader[] readers = new Reader[sentences.length * 4];
		
		for (int i = 0; i < sentences.length; i++) {
			readers[i*4] = reader("<s>\n");
			readers[i*4+1] = sentences[i];
			readers[i*4+2] = reader("$¶\t [$¶] PU <<<\n");
			readers[i*4+3] = reader("</s>\n");
		}
		
		return buildCG0(concat(readers));
	}
	
	private static Reader buildCG0(Reader contents) {
		Reader header = reader(
				"<DOC>\n" +
				"<DOCNO.valor=FSP940101-001>\n" +
				"<DOCID.valor=FSP940101-001>\n" +
				"<DATE.valor=940101>\n" +
				"<TEXT>\n"
			);
		
		Reader trailer = reader(
				"</TEXT>\n" +
				"</DOC>"
			);
		
		return concat(header, contents, trailer);
	}

	private static Reader concat(Reader... readers) {
		return concat(Arrays.asList(readers));
	}

	private static Reader concat(Iterable<Reader> readers) {
		return concat(readers.iterator());
	}

	private static Reader concat(final Iterator<Reader> readers) {
		return new Reader() {
			private Reader current;
			
			{
				if (readers.hasNext())
					current = readers.next();
			}
			
			@Override
			public int read(char[] cbuf, int off, int len) throws IOException {
				int read = -1;
				
				while (current != null && (read = current.read(cbuf, off, len)) == -1) {
					current.close();
					if (readers.hasNext())
						current = readers.next();
					else
						current = null;
				}
				
				return read;
			}

			@Override
			public void close() throws IOException {
				while (current != null) {
					current.close();
					if (readers.hasNext())
						current = readers.next();
					else
						current = null;
				}
			}
			
		};
	}

}
