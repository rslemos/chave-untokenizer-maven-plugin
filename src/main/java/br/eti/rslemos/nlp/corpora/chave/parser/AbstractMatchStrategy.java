package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;

public abstract class AbstractMatchStrategy implements MatchStrategy {

	protected final TextMatcher matcher = new TextMatcher();
	protected List<String> cg;

	public void setText(String text) {
		matcher.setText(text);
	}

	public void setCG(List<String> cg) {
		this.cg = cg;
	}
}
