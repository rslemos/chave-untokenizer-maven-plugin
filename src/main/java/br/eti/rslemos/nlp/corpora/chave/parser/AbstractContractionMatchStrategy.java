package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractContractionMatchStrategy extends AbstractMatchStrategy {

	private final String cg0;
	private final String[] cg1;
	private final String[] results;

	private final int[] prefixEnd;
	private final int[] suffixStart;

	private Map<String, Set<Match>> cache;
	
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

	@Override
	public void setData(TextMatcher matcher, List<String> cg) {
		super.setData(matcher, cg);
		cache = new HashMap<String, Set<Match>>();
	}

	public Set<Match> matchAll() {
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (int i = 0; i < cg.size() - 1; i++) {
			addMatches(i, result);
		}
		
		return result;
	}

	private void addMatches(int i, Set<Match> result) {
		String key0 = cg.get(i).toLowerCase();
		String key1 = cg.get(i + 1).toLowerCase();
		
		if ((!cg0.equals(key0) && !key0.endsWith("=" + cg0)))
			return;
		
		int idx;
		if ((idx = Arrays.binarySearch(cg1, key1.split("=")[0])) < 0)
			return;

		int[][] marks = { { 0, 0 }, { 0, 0 } }; 
		
		String toMatch = buildMatchString(key0, key1, idx, marks);
		
		for (Match match : matchAndUpdateCache(toMatch, marks)) {
			result.add(match.adjust(0, i));
		}
	}

	private Set<Match> matchAndUpdateCache(String toMatch, int[][] marks) {
		if (!cache.containsKey(toMatch)) {
			cache.put(toMatch, matcher.matchKey(toMatch, span(marks[0][0], marks[0][1], 0), span(marks[1][0], marks[1][1], 1)));
		}
		
		return cache.get(toMatch);
	}

	private String buildMatchString(String key0, String key1, int idx, int[][] marks) {
		StringBuilder toMatchBuilder = new StringBuilder();

		marks[0][0] = 0;
		marks[0][1] = prefixEnd[idx];
		marks[1][0] = suffixStart[idx];
		marks[1][1] = results[idx].length();

		if (key0.endsWith("=" + cg0)) {
			String left = key0.replaceAll("=" + cg0 + "$", "=");
			
			toMatchBuilder.append(left);
			marks[0][1] += left.length();
			marks[1][0] += left.length();
			marks[1][1] += left.length();
		}
		
		toMatchBuilder.append(results[idx]);
		
		if (key1.startsWith(cg1[idx] + "=")) {
			String right = key1.replaceAll("^" + cg1[idx] + "=", "=");
			
			toMatchBuilder.append(right);
			marks[1][1] += right.length();
		}
		
		return toMatchBuilder.toString();
	}
}
