package br.eti.rslemos.nlp.corpora.chave.parser;

public interface Handler {

	void startToken(String attributes);

	void characters(char[] chars);

	void endToken();

}
