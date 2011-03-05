package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class OldStyleMatchStrategy implements MatchStrategy {

	public abstract Match match0(String text, List<String> cg);

	public final Set<Match> match(String text, List<String> cg) {
		Match match0 = match0(text, cg);
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}

}
