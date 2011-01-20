package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class QuotesMatchStrategy implements MatchStrategy {

	public MatchResult match(final CharBuffer buffer, List<Entry<String, String>> cg, boolean noMoreData) throws BufferUnderflowException {
		try {
			if (buffer.charAt(0) != '"')
				return null;
	
			final Entry<String, String> entry0 = cg.size() > 0 ? cg.get(0) : null;
			if (entry0 != null && "$\"".equals(entry0.getKey())) {
				return new MatchResult(buffer, cg, 0, 1, 1, 0, 1);
			} else {
				return new MatchResult(buffer, cg, 0, 1, 0);
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

}
