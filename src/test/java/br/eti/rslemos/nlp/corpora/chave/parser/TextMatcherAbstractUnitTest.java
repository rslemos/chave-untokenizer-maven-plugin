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

	protected abstract TextMatcher createTextMatcher(String text, int from, int to);

	protected abstract TextMatcher createTextMatcher(String text, boolean caseSensitive);

	protected abstract TextMatcher createTextMatcher(String text, boolean caseSensitive, boolean wordBoundaryCheck);

	@Test
	public void testMatchKey() throws Exception {
		TextMatcher matcher = createTextMatcher("Devido  às   quais");
		
		Set<Match> matches = matcher.matchKey("Devido=às=quais", span(0, "Devido=à".length(), 0), span("Devido=".length(), "Devido=às=quais".length(), 1));
		
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
		TextMatcher matcher = createTextMatcher("Devido  às   quais");
		
		Set<Match> matches = matcher.matchKey("Devido=", span(0, "Devido=".length(), 0));
		
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
		
		TextMatcher matcher = createTextMatcher(PRE_TEXTO + "Devido  às   quais" + POS_TEXTO);
		
		Set<Match> matches = matcher.matchKey("Devido=às=quais", span(0, "Devido=à".length(), 0), span("Devido=".length(), "Devido=às=quais".length(), 1));
		
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
		
		TextMatcher matcher = createTextMatcher(PRE_TEXTO_RESOLVIDO + "Devido  às   quais" + POS_TEXTO_RESOLVIDO,
				PRE_TEXTO_RESOLVIDO.length(), PRE_TEXTO_RESOLVIDO.length() + "Devido  às   quais".length()
			);
		
		Set<Match> matches = matcher.matchKey("Devido=às=quais", span(0, "Devido=à".length(), 0), span("Devido=".length(), "Devido=às=quais".length(), 1));
		
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
		TextMatcher matcher = createTextMatcher("CaseSensitive", true);
		
		Set<Match> matches = matcher.matchKey("CaseSensitive", span(0, "CaseSensitive".length(), 0));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "CaseSensitive".length(),
						span(0, "CaseSensitive".length(), 0)
					)
			));
		
		matches = matcher.matchKey("casesensitive", span(0, "casesensitive".length(), 0));
		
		assertThat(matches.size(), is(equalTo(0)));
	}

	@Test
	public void testCaseInsensitive() throws Exception {
		TextMatcher matcher = createTextMatcher("CaseInsensitive", false);
		
		Set<Match> matches = matcher.matchKey("caseinsensitive", span(0, "caseinsensitive".length(), 0));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "caseinsensitive".length(),
						span(0, "caseinsensitive".length(), 0)
					)
			));
	}

	@Test
	public void testWordBoundaryCheckOff() throws Exception {
		TextMatcher matcher = createTextMatcher("43min20", false, false);
		
		Set<Match> matches = matcher.matchKey("43", span(0, "43".length(), 0));
		
		assertThat(matches.size(), is(equalTo(1)));
		assertThat(matches, hasItems(
				match(0, "43".length(),
						span(0, "43".length(), 0)
					)
			));
	}

	@Test
	public void testMatchWordEnd() throws Exception {
		TextMatcher matcher = createTextMatcher("Devido  às   quais?  ");
		
		Set<Match> matches = matcher.matchWordEndOrNewLine();
		
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
		TextMatcher matcher = createTextMatcher("Devido  às   quais\n\n\n  ");
		
		Set<Match> matches = matcher.matchWordEndOrNewLine();
		
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
