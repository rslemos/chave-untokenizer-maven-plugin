package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import java.util.LinkedHashSet;
import java.util.Set;

public class QuotesMatchStrategy extends AbstractMatchStrategy {

	public Set<Match> matchAll() {
		Set<Match> basicSet = matcher.matchKey("\"");

		Set<Match> result = new LinkedHashSet<Match>(basicSet);

		for (int i = 0; i < cg.size(); i++) {
			String key = cg.get(i);
			if (key != null && "$\"".equals(key)) {
				for (Match match : basicSet) {
					result.add(new Match(match.getFrom(), match.getTo(), span(match.getFrom(), match.getTo(), i)));
				}
			}
		}
		
		return result;
	}
}
