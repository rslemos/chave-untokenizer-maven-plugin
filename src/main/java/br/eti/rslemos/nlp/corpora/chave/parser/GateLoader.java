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

import static java.lang.Character.isWhitespace;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.DocumentContent;
import gate.DocumentFormat;
import gate.FeatureMap;
import gate.GateConstants;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.corpora.SgmlDocumentFormat;
import gate.util.GateException;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

public final class GateLoader {

	private static DocumentFormat format;

	static {
		format = new SgmlDocumentFormat();
		try {
			format.init();
		} catch (GateException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private GateLoader() {}
	
	public static Document load(URL url, String encoding) throws GateException, IOException {
		Document doc = loadFromURL(url, encoding);
		
		parseSGML(doc);
		
		FeatureMap features = doc.getFeatures();

		doc.setSourceUrl(url);
		features.put("gate.SourceURL", url.toString());
		
		return doc;
	}

	private static void parseSGML(Document doc) throws GateException {
		String contents = doc.getContent().toString();
		contents = contents.replaceAll("\\&", "\\&amp;");
		doc.setContent(new DocumentContentImpl(contents));
		
		format.unpackMarkup(doc);
		
		stripAnnotations(doc);
		
		trim(doc);
	}

	private static void trim(Document doc) throws GateException {
		String fullContents = doc.getContent().toString();
		
		int i;
		i = fullContents.length();
		while (i > 1 && isWhitespace(fullContents.charAt(i-1)))
			i--;

		doc.edit((long) i, (long) fullContents.length(), null);

		i = 0;
		while (i < fullContents.length() && isWhitespace(fullContents.charAt(i)))
			i++;
		
		doc.edit(0L, (long) i, null);
	}

	private static void stripAnnotations(Document doc) throws GateException {
		FeatureMap features = doc.getFeatures();

		AnnotationSet originalMarkup = doc.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
		
		DocumentContent content = doc.getContent();
		HashSet<String> types = new HashSet<String>();
		types.add("DOCNO");
		types.add("DOCID");
		types.add("DATE");
		
		AnnotationSet set;
		while (!(set = originalMarkup.get(types)).isEmpty()) {
			Annotation annotation = set.iterator().next();
			String type = annotation.getType();
			Long start = annotation.getStartNode().getOffset();
			Long end = annotation.getEndNode().getOffset();
			
			String text = content.getContent(start, end).toString();
			
			features.put(type, text);
			doc.edit(start, end, null);
		}
	}

	private static Document loadFromURL(URL url, String encoding) throws GateException, IOException {
		Document doc = new DocumentImpl();
		doc.init();
		
		DocumentContent content = new DocumentContentImpl(url, encoding, null, null);
		doc.setContent(content);
		
		return doc;
	}

}
