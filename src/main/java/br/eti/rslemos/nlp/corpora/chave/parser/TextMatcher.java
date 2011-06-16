package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;

public interface TextMatcher {

	public abstract Set<Match> matchKey(String key, Span... inSpans);

}