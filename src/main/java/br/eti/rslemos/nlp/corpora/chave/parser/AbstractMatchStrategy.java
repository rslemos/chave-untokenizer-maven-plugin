package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;
import java.util.Set;

public abstract class AbstractMatchStrategy implements MatchStrategy {

	protected TextMatcher matcher;
	protected List<String> cg;

	public void setData(TextMatcher matcher, List<String> cg) {
		this.matcher = matcher;
		this.cg = cg;
	}

	public Set<Match> matchAll(int from, int to) {
		return matchAll(from, to, 0, cg.size());
	}
}
