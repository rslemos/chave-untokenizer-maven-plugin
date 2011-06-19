package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.LinkedHashSet;
import java.util.Set;

public class NewLineMatchStrategy extends AbstractMatchStrategy {

	public Set<Match> matchAll(int start, int end) {
		Set<Match> matches = new LinkedHashSet<Match>();

		Set<Match> plainMatches = matcher.matchKey("\n", span(0, 1, 0));
		
		for (int i = start; i < end; i++) {
			if ("$¶".equals(cg.get(i))) {
				for (Match match : plainMatches) {
					matches.add(match.adjust(0, i));
				}
			}
		}
		
		return matches;
	}
}
