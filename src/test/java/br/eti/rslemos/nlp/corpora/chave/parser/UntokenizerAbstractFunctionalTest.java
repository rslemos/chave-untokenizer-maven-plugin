package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gate.AnnotationSet;
import gate.Document;
import gate.GateConstants;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;

public abstract class UntokenizerAbstractFunctionalTest extends TestCase {

	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	private Untokenizer untokenizer;

	@Before
	public void setUp() {
		untokenizer = new Untokenizer();
	}
	
	protected void untokenize(Document document, List<CGEntry> cgLines) {
		untokenizer.untokenize(document, cgLines);
	}

	void _() {
		String basename = getName().replaceFirst("^test", "").replace("$", "/");
		System.out.printf("%s", basename);
		
		AnnotationSet tokens;
		List<CGEntry> cgLines;
		
		try {
			cgLines = CGEntry.loadFromReader(open(basename + ".cg"));
			System.out.printf(",%d", cgLines.size());
			
			Document document = GateLoader.load(UntokenizerAbstractFunctionalTest.class.getResource(basename + ".sgml"), "UTF-8");
			System.out.printf(",%d", document.getContent().size());
			
			long before = System.nanoTime();
			untokenize(document, cgLines);
			long after = System.nanoTime();
			
			System.out.printf(",%d", after - before);
			
			AnnotationSet originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
			tokens = originalMarkups.get("token");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println();
		}
		assertThat(tokens.size(), is(equalTo(cgLines.size())));
	}
	
	protected static Reader open(String res) throws Exception {
		return open(UntokenizerAbstractFunctionalTest.class.getResource(res));
	}

	private static Reader open(URL url) throws Exception {
		return new InputStreamReader(url.openStream(), UTF8);
	}

}
