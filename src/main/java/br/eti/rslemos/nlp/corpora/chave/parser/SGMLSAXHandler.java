package br.eti.rslemos.nlp.corpora.chave.parser;


public interface SGMLSAXHandler {
	public void tag(char[] buffer, int start, int len);
	public void whitespace(char[] buffer, int start, int len);
	public void text(char[] buffer, int start, int len);
}
