/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "chave-untokenizer-maven-plugin"
 * Copyright 2013  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
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

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class GateLoaderUnitTest {

	@DataPoint public static TestData FSP_19940101_001 = new TestData(
			GateLoaderUnitTest.class.getResource("/pt_BR/CHAVEFolha/1994/01/01/001.sgml"),
			"FSP940101-001",
			"FSP940101-001",
			"940101",
			552L,
			"Pesquisa Datafolha feita nas dez ",
			"das no caso Paubrasil. Brasil e Cotidiano"
		);

	@DataPoint public static TestData FSP_19940713_038 = new TestData(
			GateLoaderUnitTest.class.getResource("/pt_BR/CHAVEFolha/1994/07/13/038.sgml"),
			"FSP940713-038",
			"FSP940713-038",
			"940713",
			2343L,
			"Bancos puxam para cima taxas no mercado futuro",
			"(que é o juro) acabou recuando."
		);

	private Document loadDocument(TestData data) throws Exception {
		Document document = GateLoader.load(data.url, "UTF-8");
		assertThat(document, is(not(nullValue(Document.class))));
		
		return document;
	}
	
	@Theory
	public void testSourceURL(TestData data) throws Exception {
		Document document = loadDocument(data);

		URL url = document.getSourceUrl();
		assertThat(url, is(equalTo(data.url)));
	}

	@Theory
	public void testDocumentFeatures(TestData data) throws Exception {
		Document document = loadDocument(data);

		FeatureMap features = document.getFeatures();
		
		String docid = (String) features.get("DOCID");
		String docno = (String) features.get("DOCNO");
		String date = (String) features.get("DATE");
		String sourceURL = (String) features.get("gate.SourceURL");
		
		assertThat(docid, is(equalTo(data.docid)));
		assertThat(docno, is(equalTo(data.docno)));
		assertThat(date, is(equalTo(data.date)));
		assertThat(sourceURL, is(equalTo(data.url.toString())));
	}

	@Theory
	public void testDocumentContents(TestData data) throws Exception {
		Document document = loadDocument(data);

		DocumentContent content = document.getContent();
		
		assertThat(content.toString(), startsWith(data.textStart));
		assertThat(content.toString(), endsWith(data.textEnd));
		assertThat(content.size(), is(equalTo(data.textLength)));
	}

	@Theory
	public void testAnnotationSets(TestData data) throws Exception {
		Document document = loadDocument(data);

		Set<String> names = document.getAnnotationSetNames();
		
		assertThat(names, hasItem(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME));
		assertThat(names.size(), is(equalTo(1)));
	}

	@Theory
	public void testOriginalMarkups(TestData data) throws Exception {
		Document document = loadDocument(data);

		AnnotationSet originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
		
		assertThat(originalMarkups.size(), is(equalTo(2)));
		
		AnnotationSet docAnnotations = originalMarkups.get("DOC");
		assertThat(docAnnotations.size(), is(equalTo(1)));
		
		Annotation docAnnotation = docAnnotations.iterator().next();
		assertThat(docAnnotation.getStartNode().getOffset(), is(equalTo(0L)));
		assertThat(docAnnotation.getEndNode().getOffset(), is(equalTo(data.textLength)));
		assertThat(docAnnotation.getFeatures().isEmpty(), is(true));
		
		AnnotationSet textAnnotations = originalMarkups.get("TEXT");
		assertThat(textAnnotations.size(), is(equalTo(1)));
		
		Annotation textAnnotation = textAnnotations.iterator().next();
		assertThat(textAnnotation.getStartNode().getOffset(), is(equalTo(0L)));
		assertThat(textAnnotation.getEndNode().getOffset(), is(equalTo(data.textLength)));
		assertThat(textAnnotation.getFeatures().isEmpty(), is(true));
		
		assertThat(docAnnotation.coextensive(textAnnotation), is(true));
		assertThat(textAnnotation.coextensive(docAnnotation), is(true));
	}

	public static class TestData {
		public final URL url;
		
		public final String docid;
		public final String docno;
		public final String date;
		public final long textLength;
		public final String textStart;
		public final String textEnd;
		
		public TestData(URL url, String docid, String docno, String date, long textLength, String textStart, String textEnd) {
			this.url = url;
			this.docid = docid;
			this.docno = docno;
			this.date = date;
			this.textLength = textLength;
			this.textStart = textStart;
			this.textEnd = textEnd;
		}
	}
}
