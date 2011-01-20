package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class DirectMatchStrategy implements MatchStrategy {
	public MatchResult match(final CharBuffer buffer, final List<Entry<String, String>> cg, boolean noMoreData) {
		final Entry<String, String> currentEntry = cg.get(0);
		String currentKey = getKey(currentEntry);
		
		if (cg.size() > 1) {
			String nextKey = getKey(cg.get(1));
			
			if (Arrays.binarySearch(new String[] { ",", "-", ".", ":", ";" }, nextKey) >= 0) {
				if (currentKey.endsWith(nextKey)) {
					currentKey = currentKey.substring(0, currentKey.length() - nextKey.length());
				}
			}
		}
		
		int j = 0;
		int k = 0;
		int skip = 0;

		try {
			if (currentKey.charAt(j) != '=')
				while (toLowerCase(currentKey.charAt(j)) != toLowerCase(buffer.charAt(k))) {
					k++;
					skip++;
				}
			else
				while (!isWhitespace(buffer.charAt(k))) {
					k++;
					skip++;
				}
				
		} catch (IndexOutOfBoundsException e) {
			if (!noMoreData)
				throw new BufferUnderflowException();
		}
		
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
			final int skip1 = skip;

			return new MatchResult(buffer, cg, skip1, skip1 + k1, 1, skip1, skip1 + k1);
		} else {
			return null;
		}
	}

	private static String getKey(final Entry<String, String> entry) {
		String currentKey = entry.getKey();
		currentKey = currentKey.split(" ")[0];
		
		if (currentKey.startsWith("$"))
			currentKey = currentKey.substring(1);
		
		return currentKey;
	}
}