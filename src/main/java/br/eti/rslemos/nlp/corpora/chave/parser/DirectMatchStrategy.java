package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.util.Arrays;
import java.util.List;

public class DirectMatchStrategy extends AbstractStrategy implements MatchStrategy {
	
	public MatchResult match0(String text, List<String> cg) {
		String currentEntry = cg.get(0);
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
				while (toLowerCase(currentKey.charAt(j)) != toLowerCase(text.charAt(k))) {
					k++;
					skip++;
				}
			else
				while (!isWhitespace(text.charAt(k))) {
					k++;
					skip++;
				}
				
		} catch (IndexOutOfBoundsException e) {
		}
		
		try {
			while (true) {
				if (currentKey.charAt(j) == '=') {
					while (isWhitespace(text.charAt(k)))
						k++;
					j++;
					
					continue;
				}
				
				if (toLowerCase(currentKey.charAt(j)) != toLowerCase(text.charAt(k))) {
					break;
				}
				
				j++;
				k++;
			}
		} catch (StringIndexOutOfBoundsException e) {
		} catch (IndexOutOfBoundsException e) {
		}
		
		if (j == currentKey.length()) {
			return new MatchResult(skip, skip + k, 1, skip, skip + k);
		} else {
			return null;
		}
	}

	private static String getKey(String currentKey) {
		currentKey = currentKey.split(" ")[0];
		
		if (currentKey.startsWith("$"))
			currentKey = currentKey.substring(1);
		
		return currentKey;
	}
}