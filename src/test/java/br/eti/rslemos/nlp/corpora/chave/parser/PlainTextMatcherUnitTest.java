package br.eti.rslemos.nlp.corpora.chave.parser;

public class PlainTextMatcherUnitTest extends TextMatcherAbstractUnitTest {
	@Override
	protected PlainTextMatcher createTextMatcher(String text) {
		return new PlainTextMatcher(text);
	}

	@Override
	protected PlainTextMatcher createTextMatcher(String text, int from, int to) {
		return new PlainTextMatcher(text, from, to);
	}

	@Override
	protected PlainTextMatcher createTextMatcher(String text, boolean caseSensitive) {
		return new PlainTextMatcher(text, caseSensitive);
	}

	@Override
	protected PlainTextMatcher createTextMatcher(String text, boolean caseSensitive, boolean wordBoundaryCheck) {
		return new PlainTextMatcher(text, caseSensitive, wordBoundaryCheck);
	}
}
