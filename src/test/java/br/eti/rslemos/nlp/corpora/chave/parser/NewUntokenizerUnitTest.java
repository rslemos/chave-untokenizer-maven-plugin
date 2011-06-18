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
