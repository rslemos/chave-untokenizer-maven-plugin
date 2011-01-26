package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.MatchResult.result;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class QuotesMatchStrategy implements MatchStrategy {

	public MatchResult match0(String text, List<String> cg) {
		try {
			if (text.charAt(0) != '"')
				return null;
	
			String key0 = cg.size() > 0 ? cg.get(0) : null;
			if (key0 != null && "$\"".equals(key0)) {
				return new MatchResult(0, 1, result(0, 1, 0));
			} else {
				return new MatchResult(0, 1);
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Set<MatchResult> match(String text, List<String> cg) {
		MatchResult match0 = match0(text, cg);
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}

}
