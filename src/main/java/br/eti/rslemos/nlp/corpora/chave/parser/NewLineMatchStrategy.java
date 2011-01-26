package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.MatchResult.result;

import java.util.List;

public class NewLineMatchStrategy extends AbstractStrategy implements MatchStrategy {

	public MatchResult match0(String text, List<String> cg) {
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
			
			return new MatchResult(0, l, result(0, l, 0));
		} else
			return null;
	}

}
