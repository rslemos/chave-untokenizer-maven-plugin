package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WhitespaceMatchStrategy implements MatchStrategy {

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

	public Set<MatchResult> match(String text, List<String> cg) {
		MatchResult match0 = match0(text, cg);
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}

}
