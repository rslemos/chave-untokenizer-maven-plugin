package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.HashSet;
import java.util.Set;

public class EpsilonMatchStrategy extends AbstractMatchStrategy {

	@Override
	public Set<Match> matchAll(int from, int to, int start, int end) {
		Set<Match> result = new HashSet<Match>((to - from + 1) * (end - start));
		
		for (int i = from; i <= to; i++) {
			for (int j = start; j < end; j++) {
				result.add(match(i, i, this.getClass(), span(i, i, j)));
			}
		}
		
		if (result.size() > 1) {
			System.err.printf("\n%d matches between (%d, %d) for entries (%d, %d)\n", result.size(), from, to, start, end);
		}
		
		return result;
	}

}
