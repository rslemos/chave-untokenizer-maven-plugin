package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class ContractionMatchStrategy extends AbstractMatchStrategy {
	
	private static final ContractionTemplate[] A_TEMPLATES = {
		new ContractionTemplate("a", "a", "à", 1, 0),
		new ContractionTemplate("a", "aquela", "àquela", 1, 0),
		new ContractionTemplate("a", "aquelas", "àquelas", 1, 0),
		new ContractionTemplate("a", "aquele", "àquele", 1, 0),
		new ContractionTemplate("a", "aqueles", "àqueles", 1, 0),
		new ContractionTemplate("a", "aquilo", "àquilo", 1, 0),
		new ContractionTemplate("a", "as", "às", 1, 0),
		new ContractionTemplate("a", "o", "ao", 1, 1),
		new ContractionTemplate("a", "os", "aos", 1, 1),
	};
	
	private static final ContractionTemplate[] COM_TEMPLATES = {
		new ContractionTemplate("com", "mim", "comigo", 3, 2),
		new ContractionTemplate("com", "nos", "conosco", 3, 2),
		new ContractionTemplate("com", "si", "consigo", 3, 3),
		new ContractionTemplate("com", "ti", "contigo", 3, 3),
		new ContractionTemplate("com", "vos", "convosco", 3, 3),
	};

	private static final ContractionTemplate[] DE_TEMPLATES = {
		new ContractionTemplate("de", "a", "da", 1, 1),
		new ContractionTemplate("de", "ali", "dali", 1, 1),
		new ContractionTemplate("de", "aquela", "daquela", 1, 1),
		new ContractionTemplate("de", "aquelas", "daquelas", 1, 1),
		new ContractionTemplate("de", "aquele", "daquele", 1, 1),
		new ContractionTemplate("de", "aqueles", "daqueles", 1, 1),
		new ContractionTemplate("de", "aqui", "daqui", 1, 1),
		new ContractionTemplate("de", "aquilo", "daquilo", 1, 1),
		new ContractionTemplate("de", "as", "das", 1, 1),
		new ContractionTemplate("de", "aí", "daí", 1, 1),
		new ContractionTemplate("de", "ela", "dela", 1, 1),
		new ContractionTemplate("de", "elas", "delas", 1, 1),
		new ContractionTemplate("de", "ele", "dele", 1, 1),
		new ContractionTemplate("de", "eles", "deles", 1, 1),
		new ContractionTemplate("de", "entre", "dentre", 1, 1),
		new ContractionTemplate("de", "essa", "dessa", 1, 1),
		new ContractionTemplate("de", "essas", "dessas", 1, 1),
		new ContractionTemplate("de", "esse", "desse", 1, 1),
		new ContractionTemplate("de", "esses", "desses", 1, 1),
		new ContractionTemplate("de", "esta", "desta", 1, 1),
		new ContractionTemplate("de", "estas", "destas", 1, 1),
		new ContractionTemplate("de", "este", "deste", 1, 1),
		new ContractionTemplate("de", "estes", "destes", 1, 1),
		new ContractionTemplate("de", "isso", "disso", 1, 1),
		new ContractionTemplate("de", "isto", "disto", 1, 1),
		new ContractionTemplate("de", "o", "do", 1, 1),
		new ContractionTemplate("de", "os", "dos", 1, 1),
		new ContractionTemplate("de", "um", "dum", 1, 1),
		new ContractionTemplate("de", "uma", "duma", 1, 1),
		new ContractionTemplate("de", "umas", "dumas", 1, 1),
		new ContractionTemplate("de", "uns", "duns", 1, 1),
	};
	
	private static final ContractionTemplate[] EM_TEMPLATES = {
		new ContractionTemplate("em", "a", "na", 1, 1),
		new ContractionTemplate("em", "aquela", "naquela", 1, 1),
		new ContractionTemplate("em", "aquelas", "naquelas", 1, 1),
		new ContractionTemplate("em", "aquele", "naquele", 1, 1),
		new ContractionTemplate("em", "aqueles", "naqueles", 1, 1),
		new ContractionTemplate("em", "aquilo", "naquilo", 1, 1),
		new ContractionTemplate("em", "as", "nas", 1, 1),
		new ContractionTemplate("em", "ela", "nela", 1, 1),
		new ContractionTemplate("em", "elas", "nelas", 1, 1),
		new ContractionTemplate("em", "ele", "nele", 1, 1),
		new ContractionTemplate("em", "eles", "neles", 1, 1),
		new ContractionTemplate("em", "essa", "nessa", 1, 1),
		new ContractionTemplate("em", "essas", "nessas", 1, 1),
		new ContractionTemplate("em", "esse", "nesse", 1, 1),
		new ContractionTemplate("em", "esses", "nesses", 1, 1),
		new ContractionTemplate("em", "esta", "nesta", 1, 1),
		new ContractionTemplate("em", "estas", "nestas", 1, 1),
		new ContractionTemplate("em", "este", "neste", 1, 1),
		new ContractionTemplate("em", "estes", "nestes", 1, 1),
		new ContractionTemplate("em", "isso", "nisso", 1, 1),
		new ContractionTemplate("em", "isto", "nisto", 1, 1),
		new ContractionTemplate("em", "o", "no", 1, 1),
		new ContractionTemplate("em", "os", "nos", 1, 1),
		new ContractionTemplate("em", "um", "num", 1, 1),
		new ContractionTemplate("em", "uma", "numa", 1, 1),
		new ContractionTemplate("em", "umas", "numas", 1, 1),
		new ContractionTemplate("em", "uns", "nuns", 1, 1),		
	};

	private static final ContractionTemplate[] PARA_TEMPLATES = {
		new ContractionTemplate("para", "a", "pra", 3, 2),
		new ContractionTemplate("para", "aquela", "praquela", 3, 2),
		new ContractionTemplate("para", "aquelas", "praquelas", 3, 2),
		new ContractionTemplate("para", "aquele", "praquele", 3, 2),
		new ContractionTemplate("para", "aqueles", "praqueles", 3, 2),
		new ContractionTemplate("para", "aquilo", "praquilo", 3, 2),
		new ContractionTemplate("para", "as", "pras", 3, 2),
		new ContractionTemplate("para", "o", "pro", 2, 2),
		new ContractionTemplate("para", "os", "pros", 2, 2),
	};

	private static final ContractionTemplate[] POR_TEMPLATES = {
		new ContractionTemplate("por", "a", "pela", 3, 3),
		new ContractionTemplate("por", "as", "pelas", 3, 3),
		new ContractionTemplate("por", "o", "pelo", 3, 3),
		new ContractionTemplate("por", "os", "pelos", 3, 3),
	};

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
	
	public ContractionMatchStrategy() {
		this(
			union(ContractionTemplate.class,
				allCases(A_TEMPLATES),
				allCases(COM_TEMPLATES),
				allCases(DE_TEMPLATES),
				allCases(EM_TEMPLATES),
				allCases(PARA_TEMPLATES),
				allCases(POR_TEMPLATES)
			)
		);
	}

	private ContractionMatchStrategy(ContractionTemplate[] templates) {
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
	
	public Set<Match> matchAll(int from, int to, int start, int end) {
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (int i = start; i < end - 1; i++) {
			addMatches(from, to, i, result);
		}
		
		return result;
	}

	private void addMatches(int from, int to, int i, Set<Match> result) {
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
		
		for (Match match : matchKey(from, to, toMatch, marks)) {
			result.add(match.adjust(0, i));
		}
	}

	private static <T> T getFirst(T[] array) {
		return array[0];
	}
	
	private static <T> T getLast(T[] array) {
		return array[array.length - 1];
	}

	private Set<Match> matchKey(int from, int to, String toMatch, int[][] marks) {
		return matcher.matchKey(from, to, toMatch, span(marks[0][0], marks[0][1], 0), span(marks[1][0], marks[1][1], 1));
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
	
	public static <T> T[] union (Class<T> clazz, T[]... sets) {
		int size = 0;
		
		for (T[] set : sets) {
			size += set.length;
		}
		
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(clazz, size);
		
		int i = 0;
		for (T[] set : sets) {
			System.arraycopy(set, 0, result, i, set.length);
			i += set.length;
		}
		
		return result;
	}
	
	private static ContractionTemplate[] allCases(ContractionTemplate[] ts) {
		return union(ContractionTemplate.class, toUpperCase(ts), toTitleCase(ts), ts);
	}

	public static ContractionTemplate[] toUpperCase(ContractionTemplate[] templates) {
		ContractionTemplate[] result = new ContractionTemplate[templates.length];
		
		for (int i = 0; i < templates.length; i++) {
			ContractionTemplate t = templates[i];
			
			result[i] = new ContractionTemplate(t.prefix.toUpperCase(), t.suffix.toUpperCase(), t.contraction.toUpperCase(), t.prefixEnd, t.suffixStart);
		}
		
		return result;
	}
	
	public static ContractionTemplate[] toTitleCase(ContractionTemplate[] templates) {
		ContractionTemplate[] result = new ContractionTemplate[templates.length];
		
		for (int i = 0; i < templates.length; i++) {
			ContractionTemplate t = templates[i];
			
			result[i] = new ContractionTemplate(toTitleCase(t.prefix), t.suffix, toTitleCase(t.contraction), t.prefixEnd, t.suffixStart);
		}
		
		return result;
	}

	private static String toTitleCase(String str) {
		char[] cs = str.toCharArray();
		
		cs[0] = Character.toUpperCase(cs[0]);
		
		return new String(cs);
	}
	
}
