package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class EncliticMatchStrategy implements MatchStrategy {

	public MatchResult match(final CharBuffer buffer, final List<Entry<String, String>> cg, boolean noMoreData) throws BufferUnderflowException {
		if (cg.size() < 2)
			return null;
		
		final Entry<String, String> entry0 = cg.get(0);
		final Entry<String, String> entry1 = cg.get(1);
		
		String key0 = entry0.getKey().toLowerCase();
		final String key1 = entry1.getKey().toLowerCase();
		
		if (Arrays.asList("la", "las", "lo", "los").contains(key1)) {
			String currentKey = null;
			
			if (key0.endsWith("ar-"))
				currentKey = (key0.replaceAll("ar-$", "á-") + key1);

			if (key0.endsWith("er-"))
				currentKey = (key0.replaceAll("er-$", "ê-") + key1);
			
			if (key0.endsWith("ir-"))
				currentKey = (key0.replaceAll("ir-$", "i-") + key1);
			
			if (key0.endsWith("por-"))
				currentKey = (key0.replaceAll("por-$", "po-") + key1);
			
			if (key0.endsWith("pôr-"))
				currentKey = (key0.replaceAll("pôr-$", "pô-") + key1);
			
			if (currentKey == null)
				return null;

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
				return new MatchResult(buffer, cg, 0, k1, 2, 0, k1 - key1.length(), k1 - key1.length(), k1);
			} else {
				return null;
			}
		} else
			return null;
	}

}
