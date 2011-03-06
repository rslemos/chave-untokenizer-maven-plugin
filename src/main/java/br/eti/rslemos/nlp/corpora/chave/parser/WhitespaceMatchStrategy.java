package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;

public class WhitespaceMatchStrategy extends OldStyleMatchStrategy {

	@Override
	public Match match0() {
		int k = 0;
		
		try {
			while(isWhitespace(matcher.charAt(k)))
				k++;
		} catch (IndexOutOfBoundsException e) {}
		
		if (k > 0) {
			return new Match(0, k);
		} else
			return null;
	}

}
