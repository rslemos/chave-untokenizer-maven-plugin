package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;

public abstract class AbstractMatchStrategy implements MatchStrategy {

	protected TextMatcher matcher;
	protected List<String> cg;

	public void setData(TextMatcher matcher, List<String> cg) {
		this.matcher = matcher;
		this.cg = cg;
	}
}
