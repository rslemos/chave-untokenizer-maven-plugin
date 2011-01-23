package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

public class NewLineMatchStrategy implements MatchStrategy {

	public MatchResult match(CharBuffer buffer, List<String> cg, boolean noMoreData) throws BufferUnderflowException {
		if ("$¶".equals(cg.get(0))) {
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
			
			return new MatchResult(buffer, 0, l, 1, 0, l);
		} else
			return null;
	}

}
