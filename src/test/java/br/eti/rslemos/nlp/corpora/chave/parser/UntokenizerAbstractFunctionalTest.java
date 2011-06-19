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

import org.junit.experimental.theories.Theory;

public abstract class UntokenizerAbstractFunctionalTest {

	protected abstract void untokenize(Document document, List<CGEntry> cgLines);

	private static final Charset UTF8 = Charset.forName("UTF-8");

	@Theory
	public void testCanParse(String basename) throws Exception {
		System.out.println(basename);
		
		List<CGEntry> cgLines = CGEntry.loadFromReader(open(basename + ".cg"));
		
		Document document = GateLoader.load(UntokenizerAbstractFunctionalTest.class.getResource(basename + ".sgml"), "UTF-8");
		
		untokenize(document, cgLines);
		
		AnnotationSet originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
		AnnotationSet tokens = originalMarkups.get("token");
		assertThat(tokens.size(), is(equalTo(cgLines.size())));
	}

	protected static Reader open(String res) throws Exception {
		return open(UntokenizerAbstractFunctionalTest.class.getResource(res));
	}

	private static Reader open(URL url) throws Exception {
		return new InputStreamReader(url.openStream(), UTF8);
	}

}
