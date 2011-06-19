package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractContractionMatchStrategy extends AbstractMatchStrategy {

	public static class ContractionTemplate implements Comparable<ContractionTemplate> {
		public final String prefix;
		public final String suffix;
		public final String contraction;
		public final int prefixEnd;
		public final int suffixStart;
		
		public ContractionTemplate(String prefix, String suffix, String contraction, int prefixEnd, int suffixStart) {
			this.prefix = prefix;
			this.suffix = suffix;
			this.contraction = contraction;
			this.prefixEnd = prefixEnd;
			this.suffixStart = suffixStart;
		}

		// for lookup purposes only
		private ContractionTemplate(String prefix, String suffix) {
			this(prefix, suffix, null, -1, -1);
		}

		public int compareTo(ContractionTemplate o) {
			int prefixCompare = prefix.compareTo(o.prefix);
			
			return prefixCompare != 0 ? prefixCompare : suffix.compareTo(o.suffix);
		}

		@Override
		public int hashCode() {
			int hashCode = 0;
			
			hashCode += prefix != null ? prefix.hashCode() : 0;
			hashCode *= 3;
			hashCode += suffix != null ? suffix.hashCode() : 0;
			
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			
			if (obj == null)
				return false;
			
			if (!(obj instanceof ContractionTemplate))
				return false;
			
			ContractionTemplate other = (ContractionTemplate) obj;

			return (prefix != null ? prefix.equals(other.prefix) : other.prefix == null) &&
					(suffix != null ? suffix.equals(other.suffix) : other.suffix == null);
		}
		
		public String toString() {
			Formatter out = new Formatter();
			out.format("new ContractionTemplate(\"%s\", \"%s\", \"%s\", %d, %d),",
					prefix, suffix, contraction, prefixEnd, suffixStart);
			return out.toString();
		}
	}
	
	private final ContractionTemplate[] templates;
	
	private Map<String, Set<Match>> cache;
	
	@Deprecated
	private static int[] buildPrefixEnd(String[] cg1, int prefixLength) {
		int[] result = new int[cg1.length];
		Arrays.fill(result, prefixLength);
		return result;
	}

	@Deprecated
	private static int[] buildSuffixStart(String[] cg1, int prefixLength) {
		int[] result = new int[cg1.length];
		Arrays.fill(result, prefixLength);
		return result;
	}

	@Deprecated
	private static ContractionTemplate[] buildTemplates(String cg0, String[] cg1, String[] results, int[] prefixEnd, int[] suffixStart) {
		ContractionTemplate[] templates = new ContractionTemplate[cg1.length];
		
		for (int i = 0; i < cg1.length; i++) {
			templates[i] = new ContractionTemplate(cg0, cg1[i], results[i], prefixEnd[i], suffixStart[i]);
		}
		
		return templates;
	}

	@Deprecated
	public AbstractContractionMatchStrategy(String cg0, String[] cg1, String[] results, int prefixLength) {
		this(cg0, cg1, results, buildPrefixEnd(cg1, prefixLength), buildSuffixStart(cg1, prefixLength));
	}

	@Deprecated
	public AbstractContractionMatchStrategy(String cg0, String[] cg1, String[] results, int[] prefixEnd, int[] suffixStart) {
		this(buildTemplates(cg0, cg1, results, prefixEnd, suffixStart));
	}

	public AbstractContractionMatchStrategy(ContractionTemplate... templates) {
		this.templates = templates.clone();
		Arrays.sort(templates);
	}
	
	@Override
	public void setData(TextMatcher matcher, List<String> cg) {
		super.setData(matcher, cg);
		cache = new HashMap<String, Set<Match>>();
	}

	public Set<Match> matchAll(int start, int end) {
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (int i = start; i < end - 1; i++) {
			addMatches(i, result);
		}
		
		return result;
	}

	private void addMatches(int i, Set<Match> result) {
		String key0 = cg.get(i);
		String key1 = cg.get(i + 1);
		
		String prefix = getLast(key0.split("="));
		String suffix = getFirst(key1.split("="));
		
		int idx;
		if ((idx = Arrays.binarySearch(templates, new ContractionTemplate(prefix, suffix))) < 0)
			return;

		int[][] marks = { { 0, 0 }, { 0, 0 } }; 
		
		String toMatch = buildMatchString(key0, key1, idx, marks);
		
		for (Match match : matchAndUpdateCache(toMatch, marks)) {
			result.add(match.adjust(0, i));
		}
	}

	private static <T> T getFirst(T[] array) {
		return array[0];
	}
	
	private static <T> T getLast(T[] array) {
		return array[array.length - 1];
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
		marks[0][1] = templates[idx].prefixEnd;
		marks[1][0] = templates[idx].suffixStart;
		marks[1][1] = templates[idx].contraction.length();

		if (key0.endsWith("=" + templates[idx].prefix)) {
			String left = key0.replaceAll("=" + templates[idx].prefix + "$", "=");
			
			toMatchBuilder.append(left);
			marks[0][1] += left.length();
			marks[1][0] += left.length();
			marks[1][1] += left.length();
		}
		
		toMatchBuilder.append(templates[idx].contraction);
		
		if (key1.startsWith(templates[idx].suffix + "=")) {
			String right = key1.replaceAll("^" + templates[idx].suffix + "=", "=");
			
			toMatchBuilder.append(right);
			marks[1][1] += right.length();
		}
		
		return toMatchBuilder.toString();
	}
}
