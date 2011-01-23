package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.toLowerCase;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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



	public MatchResult match(CharBuffer buffer, List<String> cg, boolean noMoreData) throws BufferUnderflowException {
		String key0 = cg.get(0).toLowerCase();
		
		if (!(cg0.equals(key0) || key0.endsWith("=" + cg0)))
			return null;
		
		String key1 = cg.get(1).toLowerCase();
		
		final int idx;
		if ((idx = Arrays.binarySearch(cg1, key1.split("=")[0])) < 0)
			return null;

		CharBuffer buffer0 = buffer.slice();
		
		int cleft = 0;
		int cskip = 0;
		if (key0.endsWith("=" + cg0)) {
			String fauxKey = key0.replaceAll("=" + cg0 + "$", "=");
			MatchResult result = DM.match(buffer0, Collections.singletonList(fauxKey), noMoreData);
			if (result == null)
				return null;
			
			buffer0.position(buffer0.position() + result.getMatchLength() + result.getSkipLength());
			cleft += result.getMatchLength();
			cskip = result.getSkipLength();
		}
		
		int cmiddle = 0;
		int cright = 0;

		int i = 0;
		while (i < results[idx].length() && toLowerCase(buffer0.get()) == results[idx].charAt(i))
			i++;
		
		if (i != results[idx].length())
			return null;
		
		if (prefixEnd[idx] <= suffixStart[idx]) {
			cleft += prefixEnd[idx];
			cright += results[idx].length() - suffixStart[idx];
		} else {
			cleft += suffixStart[idx];
			cmiddle += prefixEnd[idx] - suffixStart[idx];
			cright += results[idx].length() - prefixEnd[idx];
		}
		
		if (key1.startsWith(cg1[idx] + "=")) {
			String fauxKey = key1.replaceAll("^" + cg1[idx] + "=", "=");
			MatchResult result = DM.match(buffer0, Collections.singletonList(fauxKey), noMoreData);
			if (result == null)
				return null;
			
			result.apply(null, null);
			cright += result.getMatchLength();
		}

		if (cmiddle == 0) {
			return new MatchResult(buffer, cskip, cleft + cmiddle + cright + cskip, 2, cskip, cskip + cleft, cskip + cleft, cskip + cleft + cright);
		} else if (cleft == 0) {
			return new MatchResult(buffer, cskip, cleft + cmiddle + cright + cskip, 2, cskip, cskip + cmiddle, cskip, cskip + cmiddle + cright);
		} else if (cright == 0) {
			return new MatchResult(buffer, cskip, cleft + cmiddle + cright + cskip, 2, cskip, cskip + cleft + cmiddle, cskip + cleft, cskip + cleft + cmiddle);
		} else {
			return new MatchResult(buffer, cskip, cleft + cmiddle + cright + cskip, 2, cskip, cskip + cleft + cmiddle, cskip + cleft, cskip + cleft + cmiddle + cright);
		}
	}
}
