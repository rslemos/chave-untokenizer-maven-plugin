package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.hamcrest.text.StringEndsWith.endsWith;
import static org.hamcrest.text.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.DocumentContent;
import gate.FeatureMap;
import gate.GateConstants;

import java.net.URL;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class GateLoaderUnitTest {
	private static Document document;
	private static URL url;

	@BeforeClass
	public static void setUp() throws Exception {
		url = GateLoaderUnitTest.class.getResource("/pt_BR/CHAVEFolha/1994/01/01/001.sgml");
		document = GateLoader.load(url, "UTF-8");
		assertThat(document, is(not(nullValue(Document.class))));
	}
	
	@Test
	public void testSourceURL() throws Exception {
		URL url = document.getSourceUrl();
		assertThat(url, is(equalTo(GateLoaderUnitTest.url)));
	}
	
	@Test
	public void testDocumentFeatures() throws Exception {
		FeatureMap features = document.getFeatures();
		
		String docid = (String) features.get("DOCID");
		String docno = (String) features.get("DOCNO");
		String date = (String) features.get("DATE");
		String sourceURL = (String) features.get("gate.SourceURL");
		
		assertThat(docid, is(equalTo("FSP940101-001")));
		assertThat(docno, is(equalTo("FSP940101-001")));
		assertThat(date, is(equalTo("940101")));
		assertThat(sourceURL, is(equalTo(url.toString())));
	}
	
	@Test
	public void testDocumentContents() throws Exception {
		DocumentContent content = document.getContent();
		
		assertThat(content.size(), is(equalTo(552L)));
		assertThat(content.toString(), startsWith("Pesquisa Datafolha feita nas dez "));
		assertThat(content.toString(), endsWith("das no caso Paubrasil. Brasil e Cotidiano"));
	}

	@Test
	public void testAnnotationSets() throws Exception {
		Set<String> names = document.getAnnotationSetNames();
		
		assertThat(names, hasItem(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME));
		assertThat(names.size(), is(equalTo(1)));
	}
	
	@Test
	public void testOrininalMarkups() throws Exception {
		AnnotationSet originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
		
		assertThat(originalMarkups.size(), is(equalTo(2)));
		
		AnnotationSet docAnnotations = originalMarkups.get("DOC");
		assertThat(docAnnotations.size(), is(equalTo(1)));
		
		Annotation docAnnotation = docAnnotations.iterator().next();
		assertThat(docAnnotation.getStartNode().getOffset(), is(equalTo(0L)));
		assertThat(docAnnotation.getEndNode().getOffset(), is(equalTo(552L)));
		assertThat(docAnnotation.getFeatures().isEmpty(), is(true));

		AnnotationSet textAnnotations = originalMarkups.get("TEXT");
		assertThat(textAnnotations.size(), is(equalTo(1)));
		
		Annotation textAnnotation = textAnnotations.iterator().next();
		assertThat(textAnnotation.getStartNode().getOffset(), is(equalTo(0L)));
		assertThat(textAnnotation.getEndNode().getOffset(), is(equalTo(552L)));
		assertThat(textAnnotation.getFeatures().isEmpty(), is(true));
		
		assertThat(docAnnotation.coextensive(textAnnotation), is(true));
		assertThat(textAnnotation.coextensive(docAnnotation), is(true));
	}
}
