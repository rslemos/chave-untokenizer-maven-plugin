package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public abstract class AbstractContractionMatchStrategy implements MatchStrategy {

	private final String cg0;
	private final String[] cg1;
	private final String[] results;

	private final int[] prefixEnd;
	private final int[] suffixStart;
	
	private static int[] buildPrefixEnd(String[] cg1, int prefixLength) {
		int[] result = new int[cg1.length];
		Arrays.fill(result, prefixLength);
		return result;
	}

	private static int[] buildSuffixStart(String[] cg1, int prefixLength) {
		int[] result = new int[cg1.length];
		Arrays.fill(result, prefixLength);
		return result;
	}

	public AbstractContractionMatchStrategy(String cg0, String[] cg1, String[] results, int prefixLength) {
		this(cg0, cg1, results, buildPrefixEnd(cg1, prefixLength), buildSuffixStart(cg1, prefixLength));
	}

	public AbstractContractionMatchStrategy(String cg0, String[] cg1, String[] results, int[] prefixEnd, int[] suffixStart) {
		this.cg0 = cg0;
		this.cg1 = cg1;
		this.results = results;
		this.prefixEnd = prefixEnd;
		this.suffixStart = suffixStart;
	}



	public MatchResult match(final CharBuffer buffer, final List<Entry<String, String>> cg, boolean noMoreData) throws BufferUnderflowException {
		String key0 = cg.get(0).getKey().toLowerCase();
		
		if (!(cg0.equals(key0) || key0.endsWith("=" + cg0)))
			return null;
		
		String key1 = cg.get(1).getKey().toLowerCase();
		
		final int idx;
		if ((idx = Arrays.binarySearch(cg1, key1)) < 0)
			return null;
		
		
		final String currentKey;
		if (cg0.equals(key0))
			currentKey = results[idx];
		else // key0.endsWith("=" + cg0) 
			currentKey = key0.replaceAll("=" + cg0 + "$", "=" + results[idx]);
		
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
					char[] match = new char[k1];
					buffer.get(match);
					
					int pe = prefixEnd[idx];
					int ss = suffixStart[idx];
					
					char[] prefix = new char[k1 - (results[idx].length() - (pe))];
					System.arraycopy(match, 0, prefix, 0, prefix.length);
					
					char[] suffix;
					
//					char[] prefix = new char[k1 - (results[idx].length() - prefixLength)];
//					buffer.get(prefix);
//					
//					char[] suffix = new char[(results[idx].length() - prefixLength)];
//					buffer.get(suffix);

					
					if (ss >= pe) {
						suffix = new char[(results[idx].length() - ss)];
						System.arraycopy(match, match.length - suffix.length, suffix, 0, suffix.length);

						handler.startToken(cg.get(0).getValue());
						handler.characters(prefix);
						handler.endToken();
						
						handler.startToken(cg.get(1).getValue());
						handler.characters(suffix);
						handler.endToken();
					} else {
						suffix = new char[(results[idx].length() - ss - (pe - ss))];
						System.arraycopy(match, match.length - suffix.length, suffix, 0, suffix.length);

						handler.startToken(cg.get(1).getValue());
						handler.startToken(cg.get(0).getValue());
						handler.characters(prefix);
						handler.endToken();
						if (suffix.length > 0)
							handler.characters(suffix);
						handler.endToken();
					}
					cg.remove(0);
					cg.remove(0);
				}
			};
		} else {
			return null;
		}
		
	}
}
