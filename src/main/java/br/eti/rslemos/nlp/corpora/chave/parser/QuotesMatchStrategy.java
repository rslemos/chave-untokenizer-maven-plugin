package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

public class QuotesMatchStrategy extends OldStyleMatchStrategy {

	public Match match0() {
		try {
			if (matcher.charAt(0) != '"')
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
}
