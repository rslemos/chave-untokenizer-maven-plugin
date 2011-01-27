package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class NewLineMatchStrategy implements MatchStrategy {

	public Match match0(String text, List<String> cg) {
		if ("$¶".equals(cg.get(0))) {
			int l;
			try {
				if ('\n' == text.charAt(0)) {
					l = 1;
				} else {
					l = 0;
				}
			} catch (IndexOutOfBoundsException e) {
				l = 0;
			}
			
			return new Match(0, l, span(0, l, 0));
		} else
			return null;
	}

	public Set<Match> match(String text, List<String> cg) {
		Match match0 = match0(text, cg);
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}

}
