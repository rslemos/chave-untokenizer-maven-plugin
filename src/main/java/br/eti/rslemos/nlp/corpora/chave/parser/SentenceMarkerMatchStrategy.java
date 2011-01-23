package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

public class SentenceMarkerMatchStrategy implements MatchStrategy {

	public MatchResult match(CharBuffer buffer, List<String> cg, boolean noMoreData) throws BufferUnderflowException {
		String key = cg.get(0);
		
		if ("<s>".equals(key) || "</s>".equals(key)) {
			return new MatchResult(buffer, 0, 0, 1);
		} else 
			return null;
	}

}
