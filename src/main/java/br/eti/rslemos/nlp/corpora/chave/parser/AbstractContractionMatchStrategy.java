package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.toLowerCase;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public abstract class AbstractContractionMatchStrategy implements MatchStrategy {

	private static final DirectMatchStrategy DM = new DirectMatchStrategy();
	
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
		
		final String key1 = cg.get(1).getKey().toLowerCase();
		
		final int idx;
		if ((idx = Arrays.binarySearch(cg1, key1.split("=")[0])) < 0)
			return null;

		CharBuffer buffer0 = buffer.slice();
		
		int tmpleft = 0;
		if (key0.endsWith("=" + cg0)) {
			Entry<String, String> fauxEntry = new Entry<String, String>(key0.replaceAll("=" + cg0 + "$", "="), null);
			List<Entry<String, String>> fauxCG = new ArrayList<Parser.Entry<String,String>>(0);
			fauxCG.add(fauxEntry);
			MatchResult result = DM.match(buffer0, fauxCG, noMoreData);
			if (result == null)
				return null;
			
			result.apply(null);
			tmpleft += result.getMatchLength();
		}
		
		int tmpmiddle = 0;
		int tmpright = 0;

		int i = 0;
		while (i < results[idx].length() && toLowerCase(buffer0.get()) == results[idx].charAt(i))
			i++;
		
		if (i != results[idx].length())
			return null;
		
		if (prefixEnd[idx] <= suffixStart[idx]) {
			tmpleft += prefixEnd[idx];
			tmpright += results[idx].length() - suffixStart[idx];
		} else {
			tmpleft += suffixStart[idx];
			tmpmiddle += prefixEnd[idx] - suffixStart[idx];
			tmpright += results[idx].length() - prefixEnd[idx];
		}
		
		if (key1.startsWith(cg1[idx] + "=")) {
			Entry<String, String> fauxEntry = new Entry<String, String>(key1.replaceAll("^" + cg1[idx] + "=", "="), null);
			List<Entry<String, String>> fauxCG = new ArrayList<Parser.Entry<String,String>>(0);
			fauxCG.add(fauxEntry);
			MatchResult result = DM.match(buffer0, fauxCG, noMoreData);
			if (result == null)
				return null;
			
			result.apply(null);
			tmpright += result.getMatchLength();
		}

		final int cleft = tmpleft;
		final int cmiddle = tmpmiddle;
		final int cright = tmpright;
		
		return new MatchResult() {
			
			public int getMatchLength() {
				return cleft + cmiddle + cright;
			}
			
			public void apply(Handler handler) {
				char[] left = new char[cleft];
				char[] middle = new char[cmiddle];
				char[] right = new char[cright];
				
				buffer.get(left);
				buffer.get(middle);
				buffer.get(right);
				
				if (cmiddle == 0) {
					handler.startToken(cg.get(0).getValue());
					handler.characters(left);
					handler.endToken();
					
					handler.startToken(cg.get(1).getValue());
					handler.characters(right);
					handler.endToken();
				} else if (cleft == 0) {
					handler.startToken(cg.get(1).getValue());
					handler.startToken(cg.get(0).getValue());
					handler.characters(middle);
					handler.endToken();
					handler.characters(right);
					handler.endToken();
				} else if (cright == 0) {
					handler.startToken(cg.get(0).getValue());
					handler.characters(left);
					handler.startToken(cg.get(1).getValue());
					handler.characters(middle);
					handler.endToken();
					handler.endToken();
				} else {
					handler.startPseudoToken(cg.get(0).getValue());
					handler.characters(left);
					handler.startPseudoToken(cg.get(1).getValue());
					handler.characters(middle);
					handler.endPseudoToken();
					handler.characters(right);
					handler.endPseudoToken();
				}
				
				cg.remove(0);
				cg.remove(0);
			}
		};
	}
}
