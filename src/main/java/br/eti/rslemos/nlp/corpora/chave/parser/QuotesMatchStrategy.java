package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

public class QuotesMatchStrategy implements MatchStrategy {

	public MatchResult match(CharBuffer buffer, List<String> cg, boolean noMoreData) throws BufferUnderflowException {
		try {
			if (buffer.charAt(0) != '"')
				return null;
	
			String key0 = cg.size() > 0 ? cg.get(0) : null;
			if (key0 != null && "$\"".equals(key0)) {
				return new MatchResult(buffer, 0, 1, 1, 0, 1);
			} else {
				return new MatchResult(buffer, 0, 1, 0);
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

}
