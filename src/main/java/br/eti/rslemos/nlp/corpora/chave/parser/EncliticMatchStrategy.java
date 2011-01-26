package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.MatchResult.result;
import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EncliticMatchStrategy implements MatchStrategy {

	public MatchResult match0(String text, List<String> cg) {
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
				return new MatchResult(0, k, result(0, (k - key1.length()), 0), result((k - key1.length()), k, 1));
			} else {
				return null;
			}
		} else
			return null;
	}

	public Set<MatchResult> match(String text, List<String> cg) {
		MatchResult match0 = match0(text, cg);
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}

}
