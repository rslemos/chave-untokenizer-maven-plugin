package br.eti.rslemos.nlp.corpora.chave.parser;

import br.eti.rslemos.nlp.corpora.chave.parser.Untokenizer.AbstractWork;

public class UntokenizerException extends Exception {

	private AbstractWork state;

	public UntokenizerException(AbstractWork state) {
		this.state = state;
		// TODO Auto-generated constructor stub
	}

}
