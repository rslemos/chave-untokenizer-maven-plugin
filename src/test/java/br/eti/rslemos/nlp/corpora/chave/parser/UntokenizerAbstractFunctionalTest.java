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
import gate.AnnotationSet;
import gate.Document;
import gate.GateConstants;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import junit.framework.TestCase;

public abstract class UntokenizerAbstractFunctionalTest extends TestCase {

	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	protected void untokenize(Document document, List<CGEntry> cgLines) throws UntokenizerException {
		Untokenizer untokenizer = new Untokenizer(document, cgLines);
		untokenizer.untokenize();
	}

	void _() {
		String basename = getName().replaceFirst("^test", "").replace("$", "/");
		System.out.printf("%s", basename);
		
		AnnotationSet tokens;
		List<CGEntry> cgLines;
		
		try {
			cgLines = CGEntry.loadFromReader(open(basename + ".cg"));
			System.out.printf(",%d", cgLines.size());
			
			Document document = GateLoader.load(UntokenizerAbstractFunctionalTest.class.getResource(basename + ".sgml"), "UTF-8");
			System.out.printf(",%d", document.getContent().size());

			try {
				long before = System.nanoTime();
				untokenize(document, cgLines);
				long after = System.nanoTime();
				
				System.out.printf(",%d", after - before);
			} catch (UntokenizerException e) {
				System.out.println();
				System.out.println(e.getMessage());
				
				final int from = e.state.from;
				final int to = e.state.to;
				final int start = e.state.start;
				final int end = e.state.end;
				
				System.out.printf("%s>%s<%s\n", 
						document.getContent().getContent((long)Math.max(from - 20, 0), (long)from).toString(),
						document.getContent().getContent((long)from, (long)to).toString(),
						document.getContent().getContent((long)to, (long)Math.min(to + 20, document.getContent().size())).toString()
					);
				for (int i = Math.max(start - 1, 0); i < start; i++) {
					System.out.printf("  %4d. %s (%s)\n", i, cgLines.get(i).getKey(), cgLines.get(i).getValue());
				}
				for (int i = start; i < end; i++) {
					System.out.printf("! %4d. %s (%s) : %s\n", i, cgLines.get(i).getKey(), cgLines.get(i).getValue(), e.state.spansByEntry[i]);
				}
				for (int i = end; i < Math.min(end + 1, cgLines.size()); i++) {
					System.out.printf("  %4d. %s (%s)\n", i, cgLines.get(i).getKey(), cgLines.get(i).getValue());
				}
			}
			
			AnnotationSet originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
			tokens = originalMarkups.get("token");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println();
		}
		assertThat(tokens.size(), is(equalTo(cgLines.size())));
	}
	
	protected static Reader open(String res) throws Exception {
		return open(UntokenizerAbstractFunctionalTest.class.getResource(res));
	}

	private static Reader open(URL url) throws Exception {
		return new InputStreamReader(url.openStream(), UTF8);
	}

}
