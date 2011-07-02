package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Set;

public class TextMatcherDecorator implements TextMatcher {

	private final TextMatcher concreteTextMatcher;

	public TextMatcherDecorator(TextMatcher concreteTextMatcher) {
		this.concreteTextMatcher = concreteTextMatcher;
	}

	public Set<Match> matchKey(int from, int to, String key, Span... inSpans) {
		return concreteTextMatcher.matchKey(from, to, key, inSpans);
	}

	public Set<Match> matchWordEndOrNewLine(int from, int to) {
		return concreteTextMatcher.matchWordEndOrNewLine(from, to);
	}

}
