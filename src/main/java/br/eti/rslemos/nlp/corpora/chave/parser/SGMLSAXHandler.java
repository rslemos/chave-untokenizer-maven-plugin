package br.eti.rslemos.nlp.corpora.chave.parser;


public interface SGMLSAXHandler {
	public void tag(String tag);
	public void whitespace(String whitespace);
	public void text(String text);
}
