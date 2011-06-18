package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.CGEntry.entry;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.GateConstants;
import gate.Utils;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NewUntokenizerUnitTest {
	private AnnotationSet originalMarkups;
	private Document document;
	private List<CGEntry> CG;
	private String TEXT;
	private NewUntokenizer untokenizer;

	@Before
	public void setUp() {
		char[] separator = new char[80];
		Arrays.fill(separator, '=');
		System.out.println(separator);
		
		untokenizer = new NewUntokenizer();
	}
	
	@Test
	public void testSingleWord() {
		TEXT = "construtoras";
		
		CG = Arrays.asList(new CGEntry[] {
				entry("construtoras",	"[construtora] N F P @P<"),
			});
		
		untokenize();

		assertThatHasAnnotation(0, 12, 0, "construtoras");
	}
	
	@Test
	public void testSimpleSentence() {
		TEXT = "A PF vai investigar pagamentos da Prefeitura de São Paulo a construtoras citadas no caso Paubrasil.";
		
		CG = Arrays.asList(new CGEntry[] {
			entry("A",				"[o] DET F S <artd> @>N"),
			entry("PF",				"[PF] PROP F S @SUBJ>"),
			entry("vai",			"[ir] V PR 3S IND VFIN <fmc> @FAUX"),
			entry("investigar",		"[investigar] V INF @IMV @#ICL-AUX<"),
			entry("pagamentos",		"[pagamento] N M P @<ACC"),
			entry("de",				"[de] PRP <sam-> @N<"),
			entry("a",				"[o] DET F S <artd> <-sam> @>N"),
			entry("Prefeitura",		"[prefeitura] <prop> N F S @P<"),
			entry("de",				"[de] PRP @N<"),
			entry("São=Paulo",		"[São=Paulo] PROP M S @P<"),
			entry("a",				"[a] PRP @<ADVL"),
			entry("construtoras",	"[construtora] N F P @P<"),
			entry("citadas",		"[citar] V PCP F P @IMV @#ICL-N<"),
			entry("em",				"[em] PRP <sam-> @<ADVL"),
			entry("o",				"[o] DET M S <artd> <-sam> @>N"),
			entry("caso",			"[caso] N M S @P<"),
			entry("Paubrasil",		"[Paubrasil] PROP M S @N<"),
			entry("$.",				"[$.] PU <<<"),
		});
		
		untokenize();

		assertThatHasAnnotation( 0,  1,  0, "A");
		assertThatHasAnnotation( 2,  4,  1, "PF");
		assertThatHasAnnotation( 5,  8,  2, "vai");
		assertThatHasAnnotation( 9, 19,  3, "investigar");
		assertThatHasAnnotation(20, 30,  4, "pagamentos");
//		assertThatHasAnnotation(31, 32,  5, "d");
//		assertThatHasAnnotation(32, 33,  6, "a");
		assertThatHasAnnotation(34, 44,  7, "Prefeitura");
		assertThatHasAnnotation(45, 47,  8, "de");
		assertThatHasAnnotation(48, 57,  9, "São Paulo");
		assertThatHasAnnotation(58, 59, 10, "a");
		assertThatHasAnnotation(60, 72, 11, "construtoras");
		assertThatHasAnnotation(73, 80, 12, "citadas");
//		assertThatHasAnnotation(81, 82, 13, "n");
//		assertThatHasAnnotation(82, 83, 14, "o");
		assertThatHasAnnotation(84, 88, 15, "caso");
		assertThatHasAnnotation(89, 98, 16, "Paubrasil");
		assertThatHasAnnotation(98, 99, 17, ".");

	}

	private void untokenize() {
		createDocument();
		untokenizer.untokenize(document, CG);
		
		System.out.println(document.toXml());
	}

	private void createDocument() {
		document = untokenizer.createDocument(TEXT);
		assertThat(document, is(not(nullValue(Document.class))));
		
		originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
	}
	
	private void assertThatHasAnnotation(long startOffset, long endOffset, int index, String text) {
		if ((endOffset - startOffset) != text.length())
			throw new IllegalArgumentException();
		
		AnnotationSet set = originalMarkups.getContained(startOffset, endOffset);
		assertThat(set.size(), is(equalTo(1)));
		
		Annotation ann = set.iterator().next();
		assertThat(ann.getType(), is(equalTo("token")));
		assertThat(Utils.stringFor(document, ann), is(equalTo(text)));
		assertThat(ann.getFeatures(), hasEntry((Object)"index", (Object)index));
		assertThat(ann.getFeatures(), hasEntry((Object)"match", (Object)CG.get(index).getKey()));
		assertThat(ann.getFeatures(), hasEntry((Object)"cg", (Object)CG.get(index).getValue()));
	}

}
