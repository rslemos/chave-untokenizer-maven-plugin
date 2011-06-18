package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;

public class NewLineMatchStrategy extends OldStyleMatchStrategy {

	public Match match0() {
		if ("$¶".equals(cg.get(0))) {
			int l;
			try {
				if ('\n' == ((DamerauLevenshteinTextMatcher)matcher).charAt(0)) {
					l = 1;
				} else {
					l = 0;
				}
			} catch (IndexOutOfBoundsException e) {
				l = 0;
			}
			
			return match(0, l, span(0, l, 0));
		} else
			return null;
	}
}
