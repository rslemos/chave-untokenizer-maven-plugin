package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EncliticMatchStrategy implements MatchStrategy {

	private static final DirectMatchStrategy DM = new DirectMatchStrategy();

	public Set<Match> match(String text, List<String> cg) {
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (int i = 0; i < cg.size() - 1; i++) {
			String key0 = cg.get(i).toLowerCase();
			String key1 = cg.get(i + 1).toLowerCase();
			
			if (Arrays.asList("la", "las", "lo", "los").contains(key1)) {
				String toMatch = null;
				
				if (key0.endsWith("ar-"))
					toMatch = key0.replaceAll("ar-$", "á-");
		
				if (key0.endsWith("er-"))
					toMatch = key0.replaceAll("er-$", "ê-");
				
				if (key0.endsWith("ir-"))
					toMatch = key0.replaceAll("ir-$", "i-");
				
				if (key0.endsWith("por-"))
					toMatch = key0.replaceAll("por-$", "po-");
				
				if (key0.endsWith("pôr-"))
					toMatch = key0.replaceAll("pôr-$", "pô-");
				
				if (toMatch != null) {
					toMatch += key1;
					
					result.addAll(DM.matchKey(text, toMatch,
							span(0, toMatch.length() - key1.length(), i),
							span(toMatch.length() - key1.length(), toMatch.length(), i+1)
						));
				}
			}
		}
		
		return result;
	}

}
