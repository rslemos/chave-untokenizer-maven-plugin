package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class DirectMatchStrategy implements MatchStrategy {
	public MatchResult match(final CharBuffer buffer, final List<Entry<String, String>> cg, boolean noMoreData) {
		final Entry<String, String> currentEntry = cg.get(0);
		String currentKey = currentEntry.getKey();
		
		if (currentKey.startsWith("$"))
			currentKey = currentKey.substring(1);
		
		int j = 0;
		int k = 0;
		try {
			while (true) {
				if (currentKey.charAt(j) == '=') {
					while (isWhitespace(buffer.charAt(k)))
						k++;
					j++;
					
					continue;
				}
				
				if (toLowerCase(currentKey.charAt(j)) != toLowerCase(buffer.charAt(k))) {
					break;
				}
				
				j++;
				k++;
			}
		} catch (StringIndexOutOfBoundsException e) {
		} catch (IndexOutOfBoundsException e) {
			if (!noMoreData)
				throw new BufferUnderflowException();
		}
		
		if (j == currentKey.length()) {
			// full match
			final int k1 = k; 

			return new MatchResult() {
				public void apply(Handler handler) {
					char[] cs = new char[k1];
					buffer.get(cs);
					
					handler.startToken(currentEntry.getValue());
					handler.characters(cs);
					handler.endToken();
					
					cg.remove(0);
				}
			};
		} else {
			return null;
		}
	}
}