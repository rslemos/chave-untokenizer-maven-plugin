package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private static final Pattern PATTERN_CG_ENTRY = Pattern.compile("^([^\t]*)(?:\t(.*))?$");
	private static final Pattern PATTERN_CG_S = Pattern.compile("^<s>$");
	private static final Pattern PATTERN_CG_S_S = Pattern.compile("^</s>$");

	private Entry<String, String> innerEntry = null;

	private Handler out;
	
	private Iterator<Entry<String, String>> it;
	private Entry<String, String> entry;
	private String key;
	private int j;
	private int next;
	private char[] cs;
	private String line;
	private int i;

	public Parser(Handler out) {
		this.out = out;
	}

	public boolean parse(Reader cgText, Reader sgmlText) throws IOException {
		try {
			parse0(cgText, sgmlText);
			return true;
		} catch (ParserException e) {
			e.printStackTrace();
			
			return false;
		}
	}

	private void parse0(Reader cgText, Reader sgmlText0) throws IOException, ParserException {
		List<Entry<String, String>> cgLines = preParseCG(cgText);

		ClumsySGMLLexer sgmlLexer = new ClumsySGMLLexer(sgmlText0);
		sgmlLexer.next();
		
		ClumsySGMLFilter.skipToTag(sgmlLexer, "TEXT");
		sgmlLexer.next();
		sgmlLexer.next();

		Reader sgmlText = ClumsySGMLFilter.readToTag(sgmlLexer, "/TEXT");
		
		parse0(cgLines, sgmlText);

		ClumsySGMLFilter.skipToTag(sgmlLexer, "");
	}

	private void parse0(List<Entry<String, String>> cgLines, Reader sgmlText) throws IOException, ParserException {
		BufferedReader sgmlLines = new BufferedReader(sgmlText);
		
		it = cgLines.iterator();
		while( (line = sgmlLines.readLine()) != null) {
			match((entry = it.next()).getKey(), PATTERN_CG_S);
			
			cs = line.toCharArray();
			next = 0;
			
			fetchNextKey();
			
			while(isWhitespace(cs[next]) || cs[next] == '"') {
				out.printf("%c", cs[next]);
				next++;
			}
			
			for (i = next; i < cs.length; i++) {
				if (j == key.length() || isWhitespace(key.charAt(j))) {
					emitToken();
					
					while(isWhitespace(cs[i]) || cs[i] == '"') {
						out.printf("%c", cs[i]);
						i++;
					}
					next = i;
					
					fetchNextKey();
				}
				
				if (key.charAt(j) == '=') {
					while(isWhitespace(cs[i]))
						i++;
					
					j++;
				}
				
				if (toLowerCase(cs[i]) == toLowerCase(key.charAt(j)))
					j++;
				else if (toLowerCase(cs[i]) == 'à' && toLowerCase(key.charAt(j)) == 'a' && innerEntry != null)
					j++;
				else if (toLowerCase(cs[i]) == 'à' && ("a".equals(key.toLowerCase()) || key.toLowerCase().endsWith("=a"))) {
					// double-match
					innerEntry = entry;
					
					i--;
					
					fetchNextKey();
				} else if(toLowerCase(cs[i]) == 'n' && "em".equals(key.toLowerCase())) {
					// match!
					i++;
					emitToken();
					next = i;
					i--;
					
					fetchNextKey();
				} else if("de".equals(key.toLowerCase()) || key.toLowerCase().endsWith("=de")) {
					emitToken();
					next = i;
					i--;
					
					fetchNextKey();
				} else if("por".equals(key.toLowerCase()) && toLowerCase(cs[i]) == 'e' && toLowerCase(cs[i+1]) == 'l') {
					// match!
					i++; i++;
					emitToken();
					next = i;
					i--;
					
					fetchNextKey();
					
				} else if (key.charAt(j) == '¶') {
					emitToken("");
					match(fetchNextKey().getKey(), PATTERN_CG_S_S);
					match(fetchNextKey().getKey(), PATTERN_CG_S);
					i--;
					
					fetchNextKey();
				} else {
					bailOut(cs, next, key, j, i);
				}
				
			}

			if (j == key.length()) {
				// match!
				emitToken();
			} else {
				bailOut(cs, next, key, j, cs.length);
			}

			fetchNextKey();
			emitToken("\n");
			
			match(fetchNextKey().getKey(), PATTERN_CG_S_S);
			
		}
		
		sgmlText.close();
	}

	private Entry<String, String> fetchNextKey() {
		key = (entry = it.next()).getKey();
		j = 0;

		if (key.startsWith("$"))
			key = key.substring(1);
		
		return entry;
	}

	private List<Entry<String, String>> preParseCG(Reader cgText) throws IOException {
		ClumsySGMLLexer lexer = new ClumsySGMLLexer(cgText);
		
		lexer.next();
		ClumsySGMLFilter.skipToTag(lexer, "TEXT");
		lexer.next();
		lexer.next();
		BufferedReader reader = new BufferedReader(ClumsySGMLFilter.readToTag(lexer, "/TEXT"));
		
		ArrayList<Entry<String, String>> result = new ArrayList<Entry<String, String>>();
		
		String line;
		while ((line = reader.readLine()) != null) {
			Matcher matcher = PATTERN_CG_ENTRY.matcher(line);
			if (!matcher.matches()) 
				throw new Error();
			
			result.add(new Entry<String, String>(matcher.group(1), matcher.group(2)));
		}
		reader.close();
		
		ClumsySGMLFilter.skipToTag(lexer, "");
		
		result.trimToSize();
		
		return result;
	}

	private void bailOut(char[] cs, int from, String key, int j, int to) throws ParserException {
		StringBuilder builder = new StringBuilder();
		builder.append("Mismatch at (").append(to).append(", ").append(j).append(")\n");
		builder.append("text = ").append(new String(cs)).append("\n");
		builder.append("key = ").append(new String(key)).append("\n");
		builder.append("next = ").append(from).append("\n");
		ParserException up = new ParserException(builder.toString());
		throw up; // ha ha
	}

	private void emitToken(String text) {
		emitToken(text, 0, text.length(), entry);
	}

	private void emitToken() {
		emitToken(line, next, i, entry);
	}

	private void emitToken(String text, int from, int to, Entry<String, String> entry) {
		String attributes = entry.getValue();
		if (innerEntry == null)
			out.printf("<token a=\"%s\">%s</token>", escape(attributes), text.substring(from, to));
		else {
			int prefixLength = innerEntry.getKey().length();
			out.printf("<token a=\"%s\"><token a=\"%s\">%s</token>%s</token>", escape(attributes), escape(innerEntry.getValue()), text.substring(from, from + prefixLength), text.substring(from + prefixLength, to));
			innerEntry = null;
		}
	}

	private static String escape(String s) {
		if (s == null)
			return "";
		
		s = s.replaceAll("\"", "&quot;");
		s = s.replaceAll(">", "&gt;");
		s = s.replaceAll("<", "&lt;");
		
		return s;
	}

	private static Matcher match(String line, Pattern pattern) {
		Matcher matcher = pattern.matcher(line);
		boolean matches = matcher.matches();
		if (!matches)
			throw new RuntimeException();
		
		assert matches;
		return matcher;
	}
	
	private static class Entry<K, V> {

		private K key;
		private V value;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		public K getKey() {
			return key;
		}
		
	}
}
