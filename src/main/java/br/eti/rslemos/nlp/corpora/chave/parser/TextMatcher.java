package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Set;


public interface TextMatcher {

	public abstract Set<Match> matchKey(int from, int to, String key, Span... inSpans);

	// ugly kid joe
	public abstract Set<Match> matchWordEndOrNewLine(int from, int to);
}