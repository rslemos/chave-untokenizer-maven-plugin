package br.eti.rslemos.nlp.corpora.chave.parser;

public interface Handler {

	void printf(String string, Object... args);

	void startToken(String attributes);

	void characters(char[] chars);

	void endToken();

}
