package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.toLowerCase;

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



	public MatchResult match(String text, List<String> cg) {
		String key0 = cg.get(0).toLowerCase();
		
		if (!(cg0.equals(key0) || key0.endsWith("=" + cg0)))
			return null;
		
		String key1 = cg.get(1).toLowerCase();
		
		final int idx;
		if ((idx = Arrays.binarySearch(cg1, key1.split("=")[0])) < 0)
			return null;

		int cleft = 0;
		int cskip = 0;
		int k = 0;
		if (key0.endsWith("=" + cg0)) {
			String fauxKey = key0.replaceAll("=" + cg0 + "$", "=");
			MatchResult result = DM.match(text, Collections.singletonList(fauxKey));
			if (result == null)
				return null;
			
			k += result.getMatchLength() + result.getSkipLength();
			cleft += result.getMatchLength();
			cskip = result.getSkipLength();
		}
		
		int cmiddle = 0;
		int cright = 0;

		int i = 0;
		while (i < results[idx].length() && toLowerCase(text.charAt(k++)) == results[idx].charAt(i)) {
			i++;
		}
		
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
			MatchResult result = DM.match(text.substring(k), Collections.singletonList(fauxKey));
			if (result == null)
				return null;
			
			cright += result.getMatchLength();
		}

		if (cmiddle == 0) {
			return new MatchResult(cskip, cleft + cmiddle + cright + cskip, 2, cskip, cskip + cleft, cskip + cleft, cskip + cleft + cright);
		} else if (cleft == 0) {
			return new MatchResult(cskip, cleft + cmiddle + cright + cskip, 2, cskip, cskip + cmiddle, cskip, cskip + cmiddle + cright);
		} else if (cright == 0) {
			return new MatchResult(cskip, cleft + cmiddle + cright + cskip, 2, cskip, cskip + cleft + cmiddle, cskip + cleft, cskip + cleft + cmiddle);
		} else {
			return new MatchResult(cskip, cleft + cmiddle + cright + cskip, 2, cskip, cskip + cleft + cmiddle, cskip + cleft, cskip + cleft + cmiddle + cright);
		}
	}
}
