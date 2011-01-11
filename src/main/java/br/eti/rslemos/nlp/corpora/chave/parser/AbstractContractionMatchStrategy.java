package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.toLowerCase;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public abstract class AbstractContractionMatchStrategy implements MatchStrategy {

	private final String prefix;
	private final String[] suffices;
	private final String[] results;
	private final int prefixLength;

	public AbstractContractionMatchStrategy(String prefix, String[] suffices, String[] results, int prefixLength) {
		this.prefix = prefix;
		this.suffices = suffices;
		this.results = results;
		this.prefixLength = prefixLength;
	}

	public MatchResult match(final CharBuffer buffer, final List<Entry<String, String>> cg, boolean noMoreData) throws BufferUnderflowException {
		if (!prefix.equals(cg.get(0).getKey().toLowerCase()))
			return null;
		
		String artigo = cg.get(1).getKey().toLowerCase();
		
		int idx;
		if ((idx = Arrays.binarySearch(suffices, artigo)) < 0)
			return null;
		
		final String word = results[idx];
		
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != toLowerCase(buffer.charAt(i)))
				return null;
		}
		
		return new MatchResult() {
	
			public void apply(Handler handler) {
				char[] prefix = new char[prefixLength];
				buffer.get(prefix);
				
				char[] suffix = new char[word.length() - prefixLength];
				buffer.get(suffix);
				
				handler.startToken(cg.get(0).getValue());
				handler.characters(prefix);
				handler.endToken();
				
				handler.startToken(cg.get(1).getValue());
				handler.characters(suffix);
				handler.endToken();
	
				cg.remove(0);
				cg.remove(0);
			}
		};
	}
}
