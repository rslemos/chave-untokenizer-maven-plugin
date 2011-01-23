package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

public class EncliticMatchStrategy implements MatchStrategy {

	public MatchResult match(CharBuffer buffer, List<String> cg, boolean noMoreData) throws BufferUnderflowException {
		if (cg.size() < 2)
			return null;
		
		String key0 = cg.get(0).toLowerCase();
		String key1 = cg.get(1).toLowerCase();
		
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
				return new MatchResult(buffer, 0, k, 2, 0, k - key1.length(), k - key1.length(), k);
			} else {
				return null;
			}
		} else
			return null;
	}

}
