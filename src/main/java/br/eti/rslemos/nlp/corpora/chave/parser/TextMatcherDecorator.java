package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Set;

public class TextMatcherDecorator implements TextMatcher {

	private final TextMatcher concreteTextMatcher;

	public TextMatcherDecorator(TextMatcher concreteTextMatcher) {
		this.concreteTextMatcher = concreteTextMatcher;
	}

	@Override
	public Set<Match> matchKey(String key, Span... inSpans) {
		return concreteTextMatcher.matchKey(key, inSpans);
	}

	@Override
	public Set<Match> matchWordEndOrNewLine() {
		return concreteTextMatcher.matchWordEndOrNewLine();
	}

}
