package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;

public class SentenceMarkerMatchStrategy implements MatchStrategy {

	public MatchResult match(String text, List<String> cg) {
		String key = cg.get(0);
		
		if ("<s>".equals(key) || "</s>".equals(key)) {
			return new MatchResult(0, 0, 1);
		} else 
			return null;
	}

}
