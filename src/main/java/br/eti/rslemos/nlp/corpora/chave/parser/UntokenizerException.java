package br.eti.rslemos.nlp.corpora.chave.parser;

import br.eti.rslemos.nlp.corpora.chave.parser.Untokenizer.AbstractWork;

public class UntokenizerException extends Exception {

	public final AbstractWork state;

	public UntokenizerException(AbstractWork state) {
		super("from: " + state.from + "; to: " + state.to + "; start: " + state.start + "; end: " + state.end);
		this.state = state;
	}

}
