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

public class UntokenizerUnitTest {
	private AnnotationSet originalMarkups;
	private Document document;
	private List<CGEntry> CG;
	private String TEXT;
	private Untokenizer untokenizer;

	@Before
	public void setUp() {
		char[] separator = new char[80];
		Arrays.fill(separator, '=');
		System.out.println(separator);
		
		untokenizer = new Untokenizer();
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
	public void testSimpleSentence1() {
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
		assertThatHasAnnotation(31, 32,  5, "d");
		assertThatHasAnnotation(32, 33,  6, "a");
		assertThatHasAnnotation(34, 44,  7, "Prefeitura");
		assertThatHasAnnotation(45, 47,  8, "de");
		assertThatHasAnnotation(48, 57,  9, "São Paulo");
		assertThatHasAnnotation(58, 59, 10, "a");
		assertThatHasAnnotation(60, 72, 11, "construtoras");
		assertThatHasAnnotation(73, 80, 12, "citadas");
		assertThatHasAnnotation(81, 82, 13, "n");
		assertThatHasAnnotation(82, 83, 14, "o");
		assertThatHasAnnotation(84, 88, 15, "caso");
		assertThatHasAnnotation(89, 98, 16, "Paubrasil");
		assertThatHasAnnotation(98, 99, 17, ".");
	}

	@Test
	public void testSimpleSentence2() {
		TEXT = "GILBERTO DIMENSTEIN\nO brasileiro paga muito ou pouco imposto?";
		
		CG = Arrays.asList(new CGEntry[] {
				entry("Gilberto=Dimenstein",	"[Gilberto=Dimenstein] PROP M S @NPHR"),
				entry("$¶",						"[$¶] PU <<<"),
				entry("O",						"[o] DET M S <artd> @>N"),
				entry("brasileiro",				"[brasileiro] N M S @SUBJ>"),
				entry("paga",					"[pagar] V PR 3S IND VFIN <fmc> @FMV"),
				entry("muito",					"[muito] DET M S <quant> @>N"),
				entry("ou",						"[ou] KC @CO"),
				entry("pouco",					"[pouco] DET M S <quant> @>N"),
				entry("imposto",				"[imposto] N M S @<ACC"),
				entry("$?",						"[$?] PU <<<"),
				entry("$¶",						"[$¶] PU <<<"),
			});
		
		untokenize();

		// "GILBERTO DIMENSTEIN\O brasileiro paga muito ou pouco imposto?";
		
		assertThatHasAnnotation( 0, 19,  0, "GILBERTO DIMENSTEIN");
		assertThatHasAnnotation(19, 20,  1, "\n");
		assertThatHasAnnotation(20, 21,  2, "O");
		assertThatHasAnnotation(22, 32,  3, "brasileiro");
		assertThatHasAnnotation(33, 37,  4, "paga");
		assertThatHasAnnotation(38, 43,  5, "muito");
		assertThatHasAnnotation(44, 46,  6, "ou");
		assertThatHasAnnotation(47, 52,  7, "pouco");
		assertThatHasAnnotation(53, 60,  8, "imposto");
		assertThatHasAnnotation(60, 61,  9, "?");
		assertThatHasAnnotation(61, 61, 10, "");
	}

	@Test
	public void testUndecidablePilcrow1() {
		TEXT = "podem ser demitidos.\n\"A decisão do";
		
		CG = Arrays.asList(new CGEntry[] {
				entry("podem",		"[poder] V PR 3P IND VFIN @FAUX"),
				entry("ser",		"[ser] V INF @IAUX @#ICL-AUX<"),
				entry("demitidos",	"[demitir] V PCP M P @IMV @#ICL-AUX<"),
				entry("$.",			"[$.] PU <<<"),
				entry("$¶",			"[$¶] PU <<<"),
				entry("A",			"[o] <*1> <artd> DET F S @>N"),
				entry("decisão",	"[decisão] N F S @SUBJ>"),
				entry("de",			"[de] <sam-> PRP @N<"),
				entry("o",			"[o] <artd> <-sam> DET M S @>N"),
			});
		
		untokenize();

		assertThatHasAnnotation( 0,  5,  0, "podem");
		assertThatHasAnnotation( 6,  9,  1, "ser");
		assertThatHasAnnotation(10, 19,  2, "demitidos");
		assertThatHasAnnotation(19, 20,  3, ".");
		assertThatHasAnnotation(20, 21,  4, "\n");
		assertThatHasAnnotation(22, 23,  5, "A");
		assertThatHasAnnotation(24, 31,  6, "decisão");
		assertThatHasAnnotation(32, 33,  7, "d");
		assertThatHasAnnotation(33, 34,  8, "o");
	}

	@Test
	public void testUndecidablePilcrow2() {
		TEXT = "pela média. \"O presidente já disse";
		
		CG = Arrays.asList(new CGEntry[] {
				entry("por",		"[por] PRP <sam-> @<ADVL"),
				entry("a",			"[o] DET F S <artd> <-sam> @>N"),
				entry("média",		"[média] N F S @P<"),
				entry("$.",			"[$.] PU <<<"),
				entry("$¶",			"[$¶] PU <<<"),
				entry("O",			"[o] DET M S <artd> <*1> @>N"),
				entry("presidente",	"[presidente] N M S @SUBJ>"),
				entry("já",			"[já] ADV @ADVL>"),
				entry("disse",		"[dizer] V PS 3S IND VFIN <fmc> @FMV"),
			});
		
		untokenize();

		assertThatHasAnnotation( 0,  3,  0, "pel");
		assertThatHasAnnotation( 3,  4,  1, "a");
		assertThatHasAnnotation( 5, 10,  2, "média");
		assertThatHasAnnotation(10, 11,  3, ".");
		assertThatHasAnnotation(13, 14,  5, "O");
		assertThatHasAnnotation(15, 25,  6, "presidente");
		assertThatHasAnnotation(26, 28,  7, "já");
		assertThatHasAnnotation(29, 34,  8, "disse");
		assertThatHasAnnotation(11, 11,  4, "");
	}

	@Test
	public void testGivesPriorityToLengthierMatch() throws Exception {
		TEXT = "deles";

		CG = Arrays.asList(new CGEntry[] {
				entry("de", " [de] PRP <sam-> @N<"),
				entry("eles", " [eles] PERS M 3P NOM/PIV <-sam> @P<"),
			});

		untokenize();

		assertThatHasAnnotation(0, 1, 0, "d");
		assertThatHasAnnotation(1, 5, 1, "eles");
	}

	@Test
	public void testSkipsUnmatchedCharacters() throws Exception {
		TEXT = ". Sendo";
		
		CG = Arrays.asList(new CGEntry[] {
				entry("Sendo", " [ser] V GER @IMV @#ICL-ADVL>"),
			});

		untokenize();
		
		assertThatHasAnnotation(2, 7, 0, "Sendo");
	}

	@Test
	public void testQuotesCanMatchKey() throws Exception {
		TEXT = "\"  ";
		
		CG = Arrays.asList(new CGEntry[] {
				entry("$\"", " [$\"] PU"),
			});

		untokenize();
		
		assertThatHasAnnotation(0, 1, 0, "\"");
	}
	

	@Test
	public void testQuotesCanMatchKey2() throws Exception {
		TEXT = "  \"";

		CG = Arrays.asList(new CGEntry[] {
				entry("$\"", " [$\"] PU"),
			});

		untokenize();
		
		assertThatHasAnnotation(2, 3, 0, "\"");
	}

	@Test
	public void testUngreedyMatchPunctuation() throws Exception {
		TEXT = "sra.";

		CG = Arrays.asList(new CGEntry[] {
				entry("sra.", " [sra.] N F S @P<"),
				entry("$.", " [$.] PU <<<"),
			});

		untokenize();
		
		assertThatHasAnnotation(0, 3, 0, "sra");
		assertThatHasAnnotation(3, 4, 1, ".");
	}
	
	private void untokenize() {
		createDocument();
		untokenizer.untokenize(document, CG);
	}

	private void createDocument() {
		document = untokenizer.createDocument(TEXT);
		assertThat(document, is(not(nullValue(Document.class))));
		
		originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
	}
	
	private void assertThatHasAnnotation(long startOffset, long endOffset, int index, String text) {
		if ((endOffset - startOffset) != text.length())
			throw new IllegalArgumentException();
		
		
		Annotation ann = null;
		if (endOffset > startOffset) {
			AnnotationSet set = originalMarkups.getContained(startOffset, endOffset);
			assertThat(set.size(), is(equalTo(1)));
			
			ann = set.iterator().next();
		} else {
			AnnotationSet set = originalMarkups.getContained(startOffset, endOffset + 1);
			
			for (Annotation annotation : set) {
				if (annotation.getEndNode().getOffset() == endOffset) {
					ann = annotation;
					break;
				}
			}
		}
		
		assertThat(ann, is(not(nullValue(Annotation.class))));
		assertThat(ann.getType(), is(equalTo("token")));
		assertThat(Utils.stringFor(document, ann), is(equalTo(text)));
		assertThat(ann.getFeatures(), hasEntry((Object)"index", (Object)index));
		assertThat(ann.getFeatures(), hasEntry((Object)"match", (Object)CG.get(index).getKey()));
		assertThat(ann.getFeatures(), hasEntry((Object)"cg", (Object)CG.get(index).getValue()));
	}

}
