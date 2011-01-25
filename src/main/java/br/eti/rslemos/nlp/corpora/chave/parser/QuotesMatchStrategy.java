package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;

public class QuotesMatchStrategy extends AbstractStrategy implements MatchStrategy {

	public MatchResult match0(String text, List<String> cg) {
		try {
			if (text.charAt(0) != '"')
				return null;
	
			String key0 = cg.size() > 0 ? cg.get(0) : null;
			if (key0 != null && "$\"".equals(key0)) {
				return new MatchResult(0, 1, 1, 0, 1);
			} else {
				return new MatchResult(0, 1, 0);
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

}
