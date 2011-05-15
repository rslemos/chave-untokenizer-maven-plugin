package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;

public abstract class AbstractMatchStrategy implements MatchStrategy {

	protected final TextMatcher matcher = new TextMatcher();
	protected List<String> cg;

	public void setData(String text, List<String> cg) {
		matcher.setText(text);
		this.cg = cg;
	}
}
