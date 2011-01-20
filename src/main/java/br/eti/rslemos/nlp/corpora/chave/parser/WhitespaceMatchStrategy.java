package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class WhitespaceMatchStrategy implements MatchStrategy {

	public MatchResult match(final CharBuffer buffer, List<Entry<String, String>> cg, boolean noMoreData) throws BufferUnderflowException {
		int k = 0;
		
		try {
			while(isWhitespace(buffer.charAt(k)))
				k++;
		} catch (IndexOutOfBoundsException e) {}
		
		if (k > 0) {
			return new MatchResult(buffer, cg, 0, k, 0);
		} else
			return null;
	}

}
