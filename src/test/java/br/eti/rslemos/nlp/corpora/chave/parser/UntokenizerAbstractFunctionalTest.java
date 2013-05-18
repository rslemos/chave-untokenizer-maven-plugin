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
import static org.junit.Assert.assertThat;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.GateConstants;
import gate.util.InvalidOffsetException;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		
		Document document;
		try {
			cgLines = CGEntry.loadFromReader(open(basename + ".cg"));
			System.out.printf(",%d", cgLines.size());
			
			document = GateLoader.load(UntokenizerAbstractFunctionalTest.class.getResource(basename + ".sgml"), "UTF-8");
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
		
		try {
			assertThat(tokens.size(), is(equalTo(cgLines.size())));
		} catch (AssertionError e) {
			Iterator<Annotation> itann = sort(tokens).iterator();
			Iterator<CGEntry> itcg = cgLines.iterator();
			
			int i = 0;
			while (itann.hasNext() || itcg.hasNext()) {
				
				try {
					String key0;
					String key1;
					
					key0 = itcg.hasNext() ? itcg.next().getKey() : "-- no pair --";
					if (itann.hasNext()) {
						Annotation ann = itann.next();
						long startOffset = ann.getStartNode().getOffset();
						long endOffset = ann.getEndNode().getOffset();
						key1 = document.getContent().getContent(startOffset, endOffset).toString();
						key1 += "[" + startOffset + ", " + endOffset + "]";
					} else
						key1 = "-- no pair --";
					
					System.out.printf("%4d. %40s = %40s\n", ++i, key0, key1);
				} catch (InvalidOffsetException e1) {
					throw new RuntimeException(e1);
				}
				
			}
			
			System.out.println("Trechos não cobertos por nenhuma anotação:");
			
			StringBuilder run = new StringBuilder();
			String fulltext = document.getContent().toString();
			
			i = 0;
			for (long j = 1; j < fulltext.length(); j++) {
				if (tokens.get(j-1, j).isEmpty()) {
					run.append(fulltext.charAt((int)j - 1));
				} else {
					i++;
					String string = run.toString();
					run.setLength(0);
					if (string.trim().length() > 0) {
						System.out.printf("%3d. [%5d - %5d] \"%s\"\n", i, j - string.length(), j, string);
					}
				}
			}
			
			throw e;
		}
	}
	
	private List<Annotation> sort(Set<Annotation> tokens) {
		List<Annotation> result = new ArrayList<Annotation>(tokens);
		
		Collections.sort(result, new Comparator<Annotation>() {

			@Override
			public int compare(Annotation o1, Annotation o2) {
				long o1StartOffset = o1.getStartNode().getOffset();
				long o2StartOffset = o2.getStartNode().getOffset();
				
				if (o1StartOffset < o2StartOffset)
					return -1;
				
				if (o1StartOffset > o2StartOffset)
					return 1;
				
				long o1EndOffset = o1.getEndNode().getOffset();
				long o2EndOffset = o2.getEndNode().getOffset();
				
				return (int)(o1EndOffset - o2EndOffset);
			}});
		
		return result;
	}

	protected static Reader open(String res) throws Exception {
		return open(UntokenizerAbstractFunctionalTest.class.getResource(res));
	}

	private static Reader open(URL url) throws Exception {
		return new InputStreamReader(url.openStream(), UTF8);
	}

}
