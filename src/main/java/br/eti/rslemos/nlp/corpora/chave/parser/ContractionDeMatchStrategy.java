package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.toLowerCase;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class ContractionDeMatchStrategy implements MatchStrategy {

	public MatchResult match(final CharBuffer buffer, final List<Entry<String, String>> cg) throws BufferUnderflowException {
		if (!"de".equals(cg.get(0).getKey().toLowerCase()))
			return null;
		
		String artigo = cg.get(1).getKey().toLowerCase();
		
		int idx;
		if ((idx = Arrays.binarySearch(new String[] {"a", "as", "o", "os"}, artigo)) < 0)
			return null;
		
		final String word = new String[] {"da", "das", "do", "dos"} [idx];
		
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != toLowerCase(buffer.charAt(i)))
				return null;
		}
		
		return new MatchResult() {
			public void apply(Handler handler) {
				char[] d = new char[1];
				buffer.get(d);
				
				char[] art = new char[word.length() - 1];
				buffer.get(art);
				
				handler.startToken(cg.get(0).getValue());
				handler.characters(d);
				handler.endToken();
				
				handler.startToken(cg.get(1).getValue());
				handler.characters(art);
				handler.endToken();

				cg.remove(0);
				cg.remove(0);
			}
		};
	}

}
