package br.eti.rslemos.nlp.corpora.chave.parser;

public interface MatchResult {
	/**
	 * Carry all actions to consume a previously successful match.
	 */
	void apply(Handler handler);
}