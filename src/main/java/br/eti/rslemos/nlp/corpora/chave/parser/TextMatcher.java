package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Set;


public interface TextMatcher {

	public abstract Set<Match> matchKey(String key, Span... inSpans);

	// ugly kid joe
	public abstract Set<Match> matchWordEndOrNewLine();
}