package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Collections;
import java.util.Set;

public abstract class OldStyleMatchStrategy extends AbstractMatchStrategy {

	public abstract Match match0();

	public final Set<Match> matchAll() {
		Match match0 = match0();
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}

}
