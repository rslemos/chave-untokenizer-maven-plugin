package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.CGEntry.entry;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.GateConstants;
import gate.Utils;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UntokenizerUnitTest {
	private Untokenizer parser;
	
	private List<CGEntry> cg;
	private Document document;
	
	private AnnotationSet originalMarkups;
	
	@Before
	public void setUp() {
		parser = new Untokenizer();
		cg = new ArrayList<CGEntry>(2);
	}

	@Test
	public void testGivesPriorityToLengthierMatch() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("eles", " [eles] PERS M 3P NOM/PIV <-sam> @P<"));

		parse("deles");

		assertThatHasAnnotation(0, 1, "d", 0);
		assertThatHasAnnotation(1, 5, "eles", 1);
	}

	@Test
	public void testSkipsUnmatchedCharacters() throws Exception {
		cg.add(entry("Sendo", " [ser] V GER @IMV @#ICL-ADVL>"));

		parse(". Sendo");
		
		assertThatHasAnnotation(2, 7, "Sendo", 0);
	}

	@Test
	public void testQuotesCanMatchKey() throws Exception {
		cg.add(entry("$\"", " [$\"] PU"));

		parse("\"  ");
		
		assertThatHasAnnotation(0, 1, "\"", 0);
	}
	

	@Test
	public void testQuotesCanMatchKey2() throws Exception {
		cg.add(entry("$\"", " [$\"] PU"));

		parse("  \"");
		assertThatHasAnnotation(2, 3, "\"", 0);
	}

	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		cg.add(entry("sra.", " [sra.] N F S @P<"));
		cg.add(entry("$.", " [$.] PU <<<"));

		parse("sra.");
		
		assertThatHasAnnotation(0, 3, "sra", 0);
		assertThatHasAnnotation(3, 4, ".", 1);
	}
	
	private void parse(String text) throws IOException, ParserException {
		document = parser.parse(cg, document(text));
		originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
	}

	private void assertThatHasAnnotation(long startOffset, long endOffset, String text, int index) {
		if ((endOffset - startOffset) != text.length())
			throw new IllegalArgumentException();
		
		AnnotationSet set = originalMarkups.getContained(startOffset, endOffset);
		assertThat(set.size(), is(equalTo(1)));
		
		Annotation ann = set.iterator().next();
		assertThat(ann.getType(), is(equalTo("token")));
		assertThat(Utils.stringFor(document, ann), is(equalTo(text)));
		assertThat(ann.getFeatures(), hasEntry((Object)"cg", (Object)cg.get(index).getValue()));
		assertThat(ann.getFeatures(), hasEntry((Object)"match", (Object)cg.get(index).getKey()));
	}

	private static Document document(String text) {
		Document result = new DocumentImpl();
		result.setContent(new DocumentContentImpl(text));
		return result;
	}
}
