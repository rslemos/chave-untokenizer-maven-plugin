package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class NewLineMatchStrategy implements MatchStrategy {

	public MatchResult match(final CharBuffer buffer, final List<Entry<String, String>> cg) throws BufferUnderflowException {
		if ("$¶".equals(cg.get(0).getKey())) {
			final int l;
			if ('\n' == buffer.charAt(0)) {
				l = 1;
			} else {
				l = 0;
			}
			
			return new MatchResult() {
				public void apply(Handler handler) {
					char[] c = new char[l];
					buffer.get(c);
					
					handler.startToken(cg.get(0).getValue());
					handler.characters(c);
					handler.endToken();
					
					cg.remove(0);
				}
			};
		} else
			return null;
	}

}
