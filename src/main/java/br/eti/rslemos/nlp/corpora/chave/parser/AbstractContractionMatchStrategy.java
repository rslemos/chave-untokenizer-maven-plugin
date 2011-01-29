package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	public Set<Match> match(String text, List<String> cg) {
		Map<String, Set<Match>> cache = new HashMap<String, Set<Match>>();
		
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (int i = 0; i < cg.size() - 1; i++) {
			String key0 = cg.get(i).toLowerCase();
			
			if ((!cg0.equals(key0) && !key0.endsWith("=" + cg0)))
				continue;
			
			String key1 = cg.get(i + 1).toLowerCase();

			int idx;
			if ((idx = Arrays.binarySearch(cg1, key1.split("=")[0])) < 0)
				continue;
			
			int span0_start = 0;
			int span0_end = prefixEnd[idx];
			int span1_start = suffixStart[idx];
			int span1_end = results[idx].length();
			
			StringBuilder toMatchBuilder = new StringBuilder();
			
			if (key0.endsWith("=" + cg0)) {
				String left = key0.replaceAll("=" + cg0 + "$", "=");
				
				toMatchBuilder.append(left);
				span0_end += left.length();
				span1_start += left.length();
				span1_end += left.length();
			}
			
			toMatchBuilder.append(results[idx]);
			
			if (key1.startsWith(cg1[idx] + "=")) {
				String right = key1.replaceAll("^" + cg1[idx] + "=", "=");
				
				toMatchBuilder.append(right);
				span1_end += right.length();
			}
			
			String toMatch = toMatchBuilder.toString();

			if (!cache.containsKey(toMatch)) {
				cache.put(toMatch, match(text, toMatch, span0_start, span0_end, span1_start, span1_end));
			}

			
			Set<Match> matches = new LinkedHashSet<Match>();

			for (Match match : cache.get(toMatch)) {
				matches.add(match.adjust(0, i));
			}
			
			result.addAll(matches);
		}
		
		return result;
	}

	private Set<Match> match(String text, String toMatch, int span0_start, int span0_end, int span1_start, int span1_end) {
		return DM.matchTwo(text, toMatch, span0_start, span0_end, span1_start, span1_end);
	}
}
