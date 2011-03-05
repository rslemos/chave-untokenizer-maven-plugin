package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;

public abstract class AbstractMatchStrategy implements MatchStrategy {

	protected String text;
	protected List<String> cg;

	public void setText(String text) {
		this.text = text;
	}

	public void setCG(List<String> cg) {
		this.cg = cg;
	}
}
