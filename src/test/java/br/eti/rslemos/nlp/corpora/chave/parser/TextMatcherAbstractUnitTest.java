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

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;

public abstract class TextMatcherAbstractUnitTest {

	protected abstract TextMatcher createTextMatcher(String text);

	protected abstract TextMatcher createTextMatcher(String text, boolean caseSensitive);

	protected abstract TextMatcher createTextMatcher(String text, boolean caseSensitive, boolean wordBoundaryCheck);

	@Test
	public void testMatchKey() throws Exception {
		String text = "Devido  às   quais";
		TextMatcher matcher = createTextMatcher(text);
		
		Set<Match> matches = matcher.matchKey(0, text.length(), "Devido=às=quais", span(0, "Devido=à".length(), 0), span("Devido=".length(), "Devido=às=quais".length(), 1));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "Devido  às   quais".length(),
						span(0, "Devido  à".length(), 0),
						span("Devido  ".length(), "Devido  às   quais".length(), 1)
					)
			));
	}

	@Test
	public void testMatchTrailingWhitespaceKey() throws Exception {
		String text = "Devido  às   quais";
		TextMatcher matcher = createTextMatcher(text);
		
		Set<Match> matches = matcher.matchKey(0, text.length(), "Devido=", span(0, "Devido=".length(), 0));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "Devido  ".length(),
						span(0, "Devido  ".length(), 0)
					)
			));
	}

	@Test
	public void testMatchKeyInsideText() throws Exception {
		final String PRE_TEXTO = " xxx ";
		final String POS_TEXTO = " xxx ";
		
		String text = PRE_TEXTO + "Devido  às   quais" + POS_TEXTO;
		
		TextMatcher matcher = createTextMatcher(text);
		
		Set<Match> matches = matcher.matchKey(0, text.length(), "Devido=às=quais", span(0, "Devido=à".length(), 0), span("Devido=".length(), "Devido=às=quais".length(), 1));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(PRE_TEXTO.length(), PRE_TEXTO.length() + "Devido  às   quais".length(),
						span(PRE_TEXTO.length(), PRE_TEXTO.length() + "Devido  à".length(), 0),
						span(PRE_TEXTO.length() + "Devido  ".length(), PRE_TEXTO.length() + "Devido  às   quais".length(), 1)
					)
			));
	}

	@Test
	public void testMatchKeyWithRange() throws Exception {
		final String PRE_TEXTO_RESOLVIDO = "< texto já resolvido usando Devido às quais > ";
		final String POS_TEXTO_RESOLVIDO = "< texto já resolvido usando Devido às quais > ";
		
		TextMatcher matcher = createTextMatcher(PRE_TEXTO_RESOLVIDO + "Devido  às   quais" + POS_TEXTO_RESOLVIDO);
		
		Set<Match> matches = matcher.matchKey(
				PRE_TEXTO_RESOLVIDO.length(), 
				PRE_TEXTO_RESOLVIDO.length() + "Devido  às   quais".length(), 
				"Devido=às=quais", 
					span(0, "Devido=à".length(), 0), 
					span("Devido=".length(), "Devido=às=quais".length(), 1)
			);
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(PRE_TEXTO_RESOLVIDO.length(), PRE_TEXTO_RESOLVIDO.length() + "Devido  às   quais".length(),
						span(PRE_TEXTO_RESOLVIDO.length(), PRE_TEXTO_RESOLVIDO.length() + "Devido  à".length(), 0),
						span(PRE_TEXTO_RESOLVIDO.length() + "Devido  ".length(), PRE_TEXTO_RESOLVIDO.length() + "Devido  às   quais".length(), 1)
					)
			));
	}

	@Test
	public void testCaseSensitive() throws Exception {
		String text = "CaseSensitive";
		TextMatcher matcher = createTextMatcher(text, true);
		
		Set<Match> matches = matcher.matchKey(0, text.length(), "CaseSensitive", span(0, "CaseSensitive".length(), 0));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "CaseSensitive".length(),
						span(0, "CaseSensitive".length(), 0)
					)
			));
		
		matches = matcher.matchKey(0, text.length(), "casesensitive", span(0, "casesensitive".length(), 0));
		
		assertThat(matches.size(), is(equalTo(0)));
	}

	@Test
	public void testCaseInsensitive() throws Exception {
		String text = "CaseInsensitive";
		TextMatcher matcher = createTextMatcher(text, false);
		
		Set<Match> matches = matcher.matchKey(0, text.length(), "caseinsensitive", span(0, "caseinsensitive".length(), 0));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "caseinsensitive".length(),
						span(0, "caseinsensitive".length(), 0)
					)
			));
	}

	@Test
	public void testWordBoundaryCheckOff() throws Exception {
		String text = "43min20";
		TextMatcher matcher = createTextMatcher(text, false, false);
		
		Set<Match> matches = matcher.matchKey(0, text.length(), "43", span(0, "43".length(), 0));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "43".length(),
						span(0, "43".length(), 0)
					)
			));
	}

	@Test
	public void testMatchWordEnd() throws Exception {
		String text = "Devido  às   quais?  ";
		TextMatcher matcher = createTextMatcher(text);
		
		Set<Match> matches = matcher.matchWordEndOrNewLine(0, text.length());
		
		assertThat(matches.size(), is(equalTo(4)));
		assertThat(matches, hasItems(
				match("Devido".length(), "Devido".length(),
						span("Devido".length(), "Devido".length(), 0)
					),
				match("Devido  às".length(), "Devido  às".length(),
						span("Devido  às".length(), "Devido  às".length(), 0)
					),
				match("Devido  às   quais".length(), "Devido  às   quais".length(),
						span("Devido  às   quais".length(), "Devido  às   quais".length(), 0)
					),
				match("Devido  às   quais?".length(), "Devido  às   quais?".length(),
						span("Devido  às   quais?".length(), "Devido  às   quais?".length(), 0)
					)
			));
	}

	@Test
	public void testMatchNewLine() throws Exception {
		String text = "Devido  às   quais\n\n\n  ";
		TextMatcher matcher = createTextMatcher(text);
		
		Set<Match> matches = matcher.matchWordEndOrNewLine(0, text.length());
		
		assertThat(matches.size(), is(equalTo(5)));
		assertThat(matches, hasItems(
				match("Devido".length(), "Devido".length(),
						span("Devido".length(), "Devido".length(), 0)
					),
				match("Devido  às".length(), "Devido  às".length(),
						span("Devido  às".length(), "Devido  às".length(), 0)
					),
				match("Devido  às   quais".length(), "Devido  às   quais\n".length(),
						span("Devido  às   quais".length(), "Devido  às   quais\n".length(), 0)
					),
				match("Devido  às   quais\n".length(), "Devido  às   quais\n\n".length(),
						span("Devido  às   quais\n".length(), "Devido  às   quais\n\n".length(), 0)
					),
				match("Devido  às   quais\n\n".length(), "Devido  às   quais\n\n\n".length(),
						span("Devido  às   quais\n\n".length(), "Devido  às   quais\n\n\n".length(), 0)
					)
			));
	}
}
