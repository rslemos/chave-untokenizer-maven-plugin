package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class AbstractStrategy implements MatchStrategy {

	public Set<MatchResult> match(String text, List<String> cg) {
		MatchResult match0 = match0(text, cg);
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}

	protected abstract MatchResult match0(String text, List<String> cg);
}
