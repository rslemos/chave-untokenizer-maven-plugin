package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.MatchResult.result;

import java.util.List;

public class QuotesMatchStrategy extends AbstractStrategy implements MatchStrategy {

	public MatchResult match0(String text, List<String> cg) {
		try {
			if (text.charAt(0) != '"')
				return null;
	
			String key0 = cg.size() > 0 ? cg.get(0) : null;
			if (key0 != null && "$\"".equals(key0)) {
				return new MatchResult(0, 1, result(0, 1, 0));
			} else {
				return new MatchResult(0, 1);
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

}
