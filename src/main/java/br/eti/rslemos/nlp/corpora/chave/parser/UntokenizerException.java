package br.eti.rslemos.nlp.corpora.chave.parser;

import br.eti.rslemos.nlp.corpora.chave.parser.Untokenizer.NarrowWork;

public class UntokenizerException extends Exception {

	private NarrowWork state;

	public UntokenizerException(NarrowWork state) {
		this.state = state;
		// TODO Auto-generated constructor stub
	}

}
