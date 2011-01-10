package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class SentenceMarkerMatchStrategy implements MatchStrategy {

	public MatchResult match(CharBuffer buffer, final List<Entry<String, String>> cg) throws BufferUnderflowException {
		String key = cg.get(0).getKey();
		
		if ("<s>".equals(key) || "</s>".equals(key)) {
			return new MatchResult() {
				public void apply(Handler handler) {
					cg.remove(0);
				}
			};
		} else 
			return null;
	}

}
