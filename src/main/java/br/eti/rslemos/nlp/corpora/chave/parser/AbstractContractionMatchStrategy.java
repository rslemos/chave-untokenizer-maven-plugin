package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class AbstractContractionMatchStrategy extends AbstractMatchStrategy {

	public static class ContractionTemplate {
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
	}
	
	private final ContractionTemplate[][] templates;
	
	private final String[] prefixes;
	private final String[][] suffixes;
	
	
	private Map<String, Set<Match>> cache;
	
	public AbstractContractionMatchStrategy(ContractionTemplate... templates) {
		Map<String, List<ContractionTemplate>> templateByPrefix = new TreeMap<String, List<ContractionTemplate>>();
		
		for (ContractionTemplate template : templates) {
			List<ContractionTemplate> templateList = templateByPrefix.get(template.prefix);
			if (templateList == null) {
				templateList = new ArrayList<ContractionTemplate>();
				templateByPrefix.put(template.prefix, templateList);
			}
			
			templateList.add(template);
		}
		
		this.prefixes = templateByPrefix.keySet().toArray(new String[templateByPrefix.size()]);
		this.suffixes = new String[prefixes.length][];
		this.templates = new ContractionTemplate[prefixes.length][];
		
		
		for (int i = 0; i < prefixes.length; i++) {
			List<ContractionTemplate> templateList = templateByPrefix.get(this.prefixes[i]);
			
			this.suffixes[i] = new String[templateList.size()];
			this.templates[i] = new ContractionTemplate[templateList.size()];
			
			for (int j = 0; j < this.suffixes[i].length; j++) {
				this.templates[i][j] = templateList.get(j);
				this.suffixes[i][j] = this.templates[i][j].suffix;
			}
		}
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
		
		int idx0;
		if ((idx0 = Arrays.binarySearch(prefixes, prefix)) < 0)
			return;

		int idx1;
		if ((idx1 = Arrays.binarySearch(suffixes[idx0], suffix)) < 0)
			return;
		
		int[][] marks = { { 0, 0 }, { 0, 0 } }; 
		
		String toMatch = buildMatchString(key0, key1, templates[idx0][idx1], marks);
		
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

	private static String buildMatchString(String key0, String key1, ContractionTemplate template, int[][] marks) {
		StringBuilder toMatchBuilder = new StringBuilder();

		marks[0][0] = 0;
		marks[0][1] = template.prefixEnd;
		marks[1][0] = template.suffixStart;
		marks[1][1] = template.contraction.length();

		if (key0.endsWith("=" + template.prefix)) {
			String left = key0.replaceAll("=" + template.prefix + "$", "=");
			
			toMatchBuilder.append(left);
			marks[0][1] += left.length();
			marks[1][0] += left.length();
			marks[1][1] += left.length();
		}
		
		toMatchBuilder.append(template.contraction);
		
		if (key1.startsWith(template.suffix + "=")) {
			String right = key1.replaceAll("^" + template.suffix + "=", "=");
			
			toMatchBuilder.append(right);
			marks[1][1] += right.length();
		}
		
		return toMatchBuilder.toString();
	}
}
