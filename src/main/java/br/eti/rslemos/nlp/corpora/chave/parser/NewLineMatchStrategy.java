package br.eti.rslemos.nlp.corpora.chave.parser;

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
			
			return new MatchResult(0, l, 1, 0, l);
		} else
			return null;
	}

}
