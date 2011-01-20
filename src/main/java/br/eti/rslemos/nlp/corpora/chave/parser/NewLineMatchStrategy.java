package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class NewLineMatchStrategy implements MatchStrategy {

	public MatchResult match(final CharBuffer buffer, final List<Entry<String, String>> cg, boolean noMoreData) throws BufferUnderflowException {
		if ("$¶".equals(cg.get(0).getKey())) {
			int l;
			try {
				if ('\n' == buffer.charAt(0)) {
					l = 1;
				} else {
					l = 0;
				}
			} catch (IndexOutOfBoundsException e) {
				l = 0;
			}
			
			final int l1 = l;
			
			return new MatchResult(buffer, cg, 0, l1, 1, 0, l1);
		} else
			return null;
	}

}
