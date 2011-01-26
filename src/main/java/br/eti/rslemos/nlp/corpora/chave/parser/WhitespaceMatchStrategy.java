package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;

import java.util.List;

public class WhitespaceMatchStrategy extends AbstractStrategy implements MatchStrategy {

	public MatchResult match0(String text, List<String> cg) {
		int k = 0;
		
		try {
			while(isWhitespace(text.charAt(k)))
				k++;
		} catch (IndexOutOfBoundsException e) {}
		
		if (k > 0) {
			return new MatchResult(0, k);
		} else
			return null;
	}

}
