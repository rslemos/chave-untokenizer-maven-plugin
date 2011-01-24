package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.CGEntry.entry;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.GateConstants;
import gate.Utils;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UntokenizerUnitTest {
	private Untokenizer parser;
	
	@Before
	public void setUp() {
		parser = new Untokenizer();
	}

	@Test
	public void testGivesPriorityToLengthierMatch() throws Exception {
		List<CGEntry> cg = Arrays.asList(
				entry("de", " [de] PRP <sam-> @N<"),
				entry("eles", " [eles] PERS M 3P NOM/PIV <-sam> @P<")
			);

		Document document = parser.parse(cg, document("deles"));
		AnnotationSet set = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);

		Annotation ann0 = set.get("token", Utils.featureMap("cg", cg.get(0).getValue())).iterator().next();
		Annotation ann1 = set.get("token", Utils.featureMap("cg", cg.get(1).getValue())).iterator().next();
		
		assertThat(Utils.stringFor(document, ann0), is(equalTo("d")));
		assertThat(Utils.stringFor(document, ann1), is(equalTo("eles")));
	}

	@Test
	public void testSkipsUnmatchedCharacters() throws Exception {
		List<CGEntry> cg = Arrays.asList(
				entry("Sendo", " [ser] V GER @IMV @#ICL-ADVL>")
			);

		Document document = parser.parse(cg, document(". Sendo"));
		AnnotationSet set = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
		
		Annotation ann0 = set.get("token", Utils.featureMap("cg", cg.get(0).getValue())).iterator().next();
		
		assertThat(Utils.stringFor(document, ann0), is(equalTo("Sendo")));
	}

	@Test
	public void testQuotesCanMatchKey() throws Exception {
		List<CGEntry> cg = Arrays.asList(
				entry("$\"", " [$\"] PU")
			);

		Document document = parser.parse(cg, document("\"  "));
		AnnotationSet set = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
		
		Annotation ann0 = set.get("token", Utils.featureMap("cg", cg.get(0).getValue())).iterator().next();
		
		assertThat(Utils.stringFor(document, ann0), is(equalTo("\"")));
	}
	

	@Test
	public void testQuotesCanMatchKey2() throws Exception {
		List<CGEntry> cg = Arrays.asList(
				entry("$\"", " [$\"] PU")
			);

		Document document = parser.parse(cg, document("  \""));
		AnnotationSet set = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);

		Annotation ann0 = set.get("token", Utils.featureMap("cg", cg.get(0).getValue())).iterator().next();
		
		assertThat(Utils.stringFor(document, ann0), is(equalTo("\"")));
	}

	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		List<CGEntry> cg = Arrays.asList(
				entry("sra.", " [sra.] N F S @P<"),
				entry("$.", " [$.] PU <<<")
			);

		Document document = parser.parse(cg, document("sra."));
		AnnotationSet set = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);

		Annotation ann0 = set.get("token", Utils.featureMap("cg", cg.get(0).getValue())).iterator().next();
		Annotation ann1 = set.get("token", Utils.featureMap("cg", cg.get(1).getValue())).iterator().next();
		
		assertThat(Utils.stringFor(document, ann0), is(equalTo("sra")));
		assertThat(Utils.stringFor(document, ann1), is(equalTo(".")));
	}
	
	private static Document document(String text) {
		Document result = new DocumentImpl();
		result.setContent(new DocumentContentImpl(text));
		return result;
	}
}
