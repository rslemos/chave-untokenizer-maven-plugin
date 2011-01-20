package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class ParserUnitTest {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private static final URL basedir = ParserUnitTest.class.getResource("/pt_BR/CHAVEFolha/1994/01/01/");

	private Handler handler;
	private Parser parser;
	
	@Before
	public void setUp() {
		//handler = mock(Handler.class);
		handler = new Handler() {

			public void startToken(String attributes) {
				System.out.printf("startToken(%s)\n", attributes);
			}

			public void characters(char[] chars) {
				System.out.printf("characters(%s)\n", new String(chars));
			}

			public void endToken() {
				System.out.printf("endToken()\n");
			}

			public void startPseudoToken(String attributes) {
				System.out.printf("startPseudoToken(%s)\n", attributes);
			}

			public void endPseudoToken() {
				System.out.printf("endPseudoToken()\n");
			}
			
		};
		parser = new Parser(handler);
	}

	@Test
	public void testGivesPriorityToLengthierMatch() throws Exception {
		handler = mock(Handler.class);
		parser = new Parser(handler);
		
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("eles", " [eles] PERS M 3P NOM/PIV <-sam> @P<")
			);

		parser.parse1(cg, reader("deles"));
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("eles".toCharArray());
		order.verify(handler).endToken();
		
	}

	@Test
	public void testSkipsUnmatchedCharacters() throws Exception {
		handler = mock(Handler.class);
		parser = new Parser(handler);
		
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("Sendo", " [ser] V GER @IMV @#ICL-ADVL>")
			);

		parser.parse1(cg, reader(". Sendo"));
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).characters(". ".toCharArray());

		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("Sendo".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testQuotesCanMatchKey() throws Exception {
		handler = mock(Handler.class);
		parser = new Parser(handler);
		
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("$\"", " [$\"] PU")
			);

		parser.parse1(cg, reader("\"  "));
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("\"".toCharArray());
		order.verify(handler).endToken();
	}
	

	@Test
	public void testQuotesCanMatchKey2() throws Exception {
		handler = mock(Handler.class);
		parser = new Parser(handler);
		
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("$\"", " [$\"] PU")
			);

		parser.parse1(cg, reader("  \""));
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).characters("  ".toCharArray());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("\"".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testChave19940101_001() throws Exception {
		handler = new Handler() {
			
			public void startToken(String attributes) {
				if (attributes != null)
					System.out.printf("<token attributes=\"%s\">", escape(attributes));
				else
					System.out.printf("<token>");
			}
			
			public void endToken() {
				System.out.print("</token>");
			}
			
			public void characters(char[] chars) {
				System.out.printf("%s", new String(chars));
			}
			
			private String escape(String in) {
				return in.replaceAll("<", "\\&lt;").replaceAll(">", "\\&gt;").replaceAll("\"", "\\&quot;");
			}

			public void startPseudoToken(String attributes) {
				if (attributes != null)
					System.out.printf("<ptoken attributes=\"%s\">", escape(attributes));
				else
					System.out.printf("<ptoken>");
			}

			public void endPseudoToken() {
				System.out.print("</ptoken>");
			}
		};
		parser = new Parser(handler);
		
		verifyParse("131.cg", "131.sgml");
	}
	
	@Test
	public void testChave19940101() throws Exception {

		//verifyParse("012.cg", "012.sgml"); // 202-th entry: $.; buffer at 6590\nDump remaining buffer (5668): Kathryn Tolbert,
		//verifyParse("038.cg", "038.sgml"); // 220-th entry: país=o'=fuzzy ALT xxx; buffer at 1887\nDump remaining buffer (905): país o 'fuzzy logic', sistema
		//verifyParse("039.cg", "039.sgml"); // 1432-th entry: criará-; buffer at 7781\nDump remaining buffer (955): criar-se-á imensa expectativa
		//verifyParse("055.cg", "055.sgml"); // 89-th entry: $.; buffer at 2485\nDump remaining buffer (2079): º de janeiro
		//verifyParse("063.cg", "063.sgml"); // 22-th entry: $.; buffer at 2851\nDump remaining buffer (2761): Da Redação
		//verifyParse("076.cg", "076.sgml"); // 222-th entry: Sesc=Pinheiros-Av.; buffer at 1189\nDump remaining buffer (116): Sesc Pinheiros- av. Rebouças, 2876.
		//verifyParse("081.cg", "081.sgml"); // 233-th entry: $.; buffer at 5806\nDump remaining buffer (4706): Folha - Qual
		//verifyParse("087.cg", "087.sgml"); // 70-th entry: $.; buffer at 2609\nDump remaining buffer (2282): º de março.
		//verifyParse("094.cg", "094.sgml"); // 3-th entry: dÁgua ALT xxxua; buffer at 3439\nDump remaining buffer (3432): d'Água'
		//verifyParse("099.cg", "099.sgml"); // 2-th entry: $.; buffer at 848\nDump remaining buffer (846): Brunner trabalhou na Ferrari
		//verifyParse("105.cg", "105.sgml"); // 260-th entry: $.; buffer at 2036\nDump remaining buffer (884): São João,
		//verifyParse("110.cg", "110.sgml"); // 37-th entry: $.; buffer at 1442\nDump remaining buffer (1302): Reuniu terça-feira
		//verifyParse("112.cg", "112.sgml"); // 1-th entry: Produtor=de'Concubina; buffer at 5285\nDump remaining buffer (5285): Produtor de 'Concubina'
		//verifyParse("114.cg", "114.sgml"); // 85-th entry: $.; buffer at 1980\nDump remaining buffer (1567): Time Warner Inc.
		//verifyParse("118.cg", "118.sgml"); // 273-th entry: Uma=Fortuna=Perigosa=Follett; buffer at 1813\nDump remaining buffer (557): Uma Fortuna Perigosa"
		//verifyParse("119.cg", "119.sgml"); // 332-th entry: O=Onus...>; buffer at 1834\nDump remaining buffer (404): O Onus...' até que nã
		//verifyParse("122.cg", "122.sgml"); // 366-th entry: mudou; buffer at 3365\nDump remaining buffer (1581): as mulheres se esqueceram de como é ser jovem.
		//verifyParse("125.cg", "125.sgml"); // 280-th entry: a; buffer at 1873\nDump remaining buffer (676): gente comprar
		//verifyParse("126.cg", "126.sgml"); // 37-th entry: repetiu=o'=boom ALT xxx; buffer at 1041\nDump remaining buffer (868): repetiu o
		//verifyParse("129.cg", "129.sgml"); // 33-th entry: $.; buffer at 1726\nDump remaining buffer (1635): Direção: Ettore Scola.
		//verifyParse("130.cg", "130.sgml"); // 29-th entry: $.; buffer at 3340\nDump remaining buffer (3276): Direção: David A. Prior. Com Dan Haggerty, Brian O'Connor.
		
		verifyParse("001.cg", "001.sgml");
		verifyParse("002.cg", "002.sgml");
		verifyParse("003.cg", "003.sgml");
		verifyParse("004.cg", "004.sgml");
		verifyParse("005.cg", "005.sgml");
		verifyParse("006.cg", "006.sgml");
		verifyParse("007.cg", "007.sgml");
		verifyParse("008.cg", "008.sgml");
		verifyParse("009.cg", "009.sgml");
		verifyParse("010.cg", "010.sgml");
		verifyParse("011.cg", "011.sgml"); 
		verifyParse("013.cg", "013.sgml");
		verifyParse("014.cg", "014.sgml");
		verifyParse("015.cg", "015.sgml");
		verifyParse("016.cg", "016.sgml");
		verifyParse("017.cg", "017.sgml");
		verifyParse("018.cg", "018.sgml");
		verifyParse("019.cg", "019.sgml");
		verifyParse("020.cg", "020.sgml");
		verifyParse("021.cg", "021.sgml");
		verifyParse("022.cg", "022.sgml");
		verifyParse("023.cg", "023.sgml");
		verifyParse("024.cg", "024.sgml");
		verifyParse("025.cg", "025.sgml");
		verifyParse("026.cg", "026.sgml");
		verifyParse("027.cg", "027.sgml");
		verifyParse("028.cg", "028.sgml");
		verifyParse("029.cg", "029.sgml");
		verifyParse("030.cg", "030.sgml");
		verifyParse("031.cg", "031.sgml");
		verifyParse("032.cg", "032.sgml");
		verifyParse("033.cg", "033.sgml");
		verifyParse("034.cg", "034.sgml");
		verifyParse("035.cg", "035.sgml");
		verifyParse("036.cg", "036.sgml");
		verifyParse("037.cg", "037.sgml");
		verifyParse("040.cg", "040.sgml");
		verifyParse("041.cg", "041.sgml");
		verifyParse("042.cg", "042.sgml");
		verifyParse("043.cg", "043.sgml");
		verifyParse("044.cg", "044.sgml");
		verifyParse("045.cg", "045.sgml");
		verifyParse("046.cg", "046.sgml");
		verifyParse("047.cg", "047.sgml");
		verifyParse("048.cg", "048.sgml");
		verifyParse("049.cg", "049.sgml");
		verifyParse("050.cg", "050.sgml");
		verifyParse("051.cg", "051.sgml");
		verifyParse("052.cg", "052.sgml");
		verifyParse("053.cg", "053.sgml");
		verifyParse("054.cg", "054.sgml");
		verifyParse("056.cg", "056.sgml");
		verifyParse("057.cg", "057.sgml");
		verifyParse("058.cg", "058.sgml");
		verifyParse("059.cg", "059.sgml");
		verifyParse("060.cg", "060.sgml");
		verifyParse("061.cg", "061.sgml");
		verifyParse("062.cg", "062.sgml");
		verifyParse("064.cg", "064.sgml");
		verifyParse("065.cg", "065.sgml");
		verifyParse("066.cg", "066.sgml");
		verifyParse("067.cg", "067.sgml");
		verifyParse("068.cg", "068.sgml");
		verifyParse("069.cg", "069.sgml");
		verifyParse("070.cg", "070.sgml"); 
		verifyParse("071.cg", "071.sgml");
		verifyParse("072.cg", "072.sgml");
		verifyParse("073.cg", "073.sgml");
		verifyParse("074.cg", "074.sgml");
		verifyParse("075.cg", "075.sgml");
		verifyParse("077.cg", "077.sgml");
		verifyParse("078.cg", "078.sgml");
		verifyParse("079.cg", "079.sgml");
		verifyParse("080.cg", "080.sgml");
		verifyParse("083.cg", "083.sgml");
		verifyParse("082.cg", "082.sgml");
		verifyParse("084.cg", "084.sgml");
		verifyParse("085.cg", "085.sgml");
		verifyParse("086.cg", "086.sgml");
		verifyParse("088.cg", "088.sgml");
		verifyParse("089.cg", "089.sgml");
		verifyParse("090.cg", "090.sgml");
		verifyParse("091.cg", "091.sgml");
		verifyParse("092.cg", "092.sgml");
		verifyParse("093.cg", "093.sgml");
		verifyParse("095.cg", "095.sgml");
		verifyParse("096.cg", "096.sgml");
		verifyParse("097.cg", "097.sgml");
		verifyParse("098.cg", "098.sgml");
		verifyParse("100.cg", "100.sgml");
		verifyParse("101.cg", "101.sgml");
		verifyParse("102.cg", "102.sgml");
		verifyParse("103.cg", "103.sgml");
		verifyParse("104.cg", "104.sgml");
		verifyParse("106.cg", "106.sgml");
		verifyParse("107.cg", "107.sgml");
		verifyParse("108.cg", "108.sgml");
		verifyParse("109.cg", "109.sgml");
		verifyParse("111.cg", "111.sgml");
		verifyParse("113.cg", "113.sgml");
		verifyParse("115.cg", "115.sgml");
		verifyParse("116.cg", "116.sgml");
		verifyParse("117.cg", "117.sgml");
		verifyParse("120.cg", "120.sgml");
		verifyParse("121.cg", "121.sgml");
		verifyParse("123.cg", "123.sgml");
		verifyParse("124.cg", "124.sgml");
		verifyParse("127.cg", "127.sgml");
		verifyParse("128.cg", "128.sgml");
		verifyParse("131.cg", "131.sgml");
		verifyParse("132.cg", "132.sgml");
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

	private static String buildCGLine(String key, String attributes) {
		return key + "\t " + attributes;
	}
	
	private static Reader buildCGSentence(String... lines) {
		Reader[] readers = new Reader[lines.length];
		
		for (int i = 0; i < lines.length; i++) {
			readers[i] = reader(lines[i] + "\n");
		}
		
		return concat(readers);
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

	private static void printReader(Reader reader, PrintStream stream) {
		try {
			char[] buffer = new char[8192];
			int size;
			
			while ((size = reader.read(buffer)) != -1)
				stream.append(new String(buffer, 0, size));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
