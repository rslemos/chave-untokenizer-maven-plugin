package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.LinkedHashSet;
import java.util.Set;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;

public class QuotesMatchStrategy extends AbstractMatchStrategy {

	public Set<Match> matchAll() {
		Set<Match> basicSet = matcher.matchKey("\"");

		Set<Match> result = new LinkedHashSet<Match>(basicSet);

		for (int i = 0; i < cg.size(); i++) {
			String key = cg.get(i);
			if (key != null && "$\"".equals(key)) {
				for (Match match : basicSet) {
					result.add(match(match.getFrom(), match.getTo(), span(match.getFrom(), match.getTo(), i)));
				}
			}
		}
		
		return result;
	}
}
