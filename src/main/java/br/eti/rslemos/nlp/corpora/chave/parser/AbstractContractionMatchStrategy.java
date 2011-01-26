package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.result;
import static java.lang.Character.toLowerCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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



	public Match match0(String text, List<String> cg) {
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
			Set<Match> resultSet = DM.match(text, Collections.singletonList(fauxKey));
			if (resultSet.isEmpty())
				return null;
			
			Match result = resultSet.iterator().next();
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
			Set<Match> resultSet = DM.match(text.substring(k), Collections.singletonList(fauxKey));
			if (resultSet.isEmpty())
				return null;
			
			Match result = resultSet.iterator().next();
			cright += result.getMatchLength();
		}

		if (cmiddle == 0) {
			return new Match(cskip, cleft + cmiddle + cright + cskip, result(cskip, (cskip + cleft), 0), result((cskip + cleft), (cskip + cleft + cright), 1));
		} else if (cleft == 0) {
			return new Match(cskip, cleft + cmiddle + cright + cskip, result(cskip, (cskip + cmiddle), 0), result(cskip, (cskip + cmiddle + cright), 1));
		} else if (cright == 0) {
			return new Match(cskip, cleft + cmiddle + cright + cskip, result(cskip, (cskip + cleft + cmiddle), 0), result((cskip + cleft), (cskip + cleft + cmiddle), 1));
		} else {
			return new Match(cskip, cleft + cmiddle + cright + cskip, result(cskip, (cskip + cleft + cmiddle), 0), result((cskip + cleft), (cskip + cleft + cmiddle + cright), 1));
		}
	}

	public Set<Match> match(String text, List<String> cg) {
		Match match0 = match0(text, cg);
		if (match0 == null)
			return Collections.emptySet();
		else
			return Collections.singleton(match0);
	}
}
