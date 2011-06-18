package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;

public class WhitespaceMatchStrategy implements MatchStrategy {

	private char[] text;

	public Set<Match> matchAll() {
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (int k = 0; k < text.length; k++) {
			if (isWhitespace(text[k])) {
				int k1 = k;
				while (k1 < text.length && isWhitespace(text[k1]))
					k1++;

				result.add(match(k, k1));
				
				k = k1;
			}
		}
		
		return result;
	}

	public void setData(String text, List<String> cg) {
		this.text = text.toCharArray();
	}

	public void setData(TextMatcher matcher, List<String> cg) {
		this.text = ((DamerauLevenshteinTextMatcher)matcher).getChars();
	}
}
