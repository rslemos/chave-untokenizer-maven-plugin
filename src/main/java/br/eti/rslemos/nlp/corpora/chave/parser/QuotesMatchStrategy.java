package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class QuotesMatchStrategy implements MatchStrategy {

	public Match match0(String text, List<String> cg) {
		try {
			if (text.charAt(0) != '"')
				return null;
	
			String key0 = cg.size() > 0 ? cg.get(0) : null;
			if (key0 != null && "$\"".equals(key0)) {
				return new Match(0, 1, span(0, 1, 0));
			} else {
				return new Match(0, 1);
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Set<Match> match(String text, List<String> cg) {
		Match match0 = match0(text, cg);
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}

}
