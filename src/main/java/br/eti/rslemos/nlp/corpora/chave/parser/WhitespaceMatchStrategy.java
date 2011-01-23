package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;

import java.util.List;

public class WhitespaceMatchStrategy implements MatchStrategy {

	public MatchResult match(String text, List<String> cg) {
		int k = 0;
		
		try {
			while(isWhitespace(text.charAt(k)))
				k++;
		} catch (IndexOutOfBoundsException e) {}
		
		if (k > 0) {
			return new MatchResult(0, k, 0);
		} else
			return null;
	}

}
