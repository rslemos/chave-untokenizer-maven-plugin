package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private static final Pattern PATTERN_CG_ENTRY = Pattern.compile("^([^\t]*)(?:\t(.*))?$");
	private static final Pattern PATTERN_CG_S = Pattern.compile("^<s>$");
	private static final Pattern PATTERN_CG_S_S = Pattern.compile("^</s>$");

	private static final Pattern PATTERN_CG_PENTRY = Pattern.compile("^(\\$¶)\t(.*)$");

	private Matcher innerMatcher = null;

	private Handler out;

	public Parser(Handler out) {
		this.out = out;
	}

	public boolean parse(Reader cgText, Reader sgmlText) throws IOException {
		try {
			parse0(cgText, sgmlText);
			return true;
		} catch (ParserException e) {
			return false;
		}
	}

	private void parse0(Reader cgText, Reader sgmlText0) throws IOException, ParserException {
		ArrayList<String> cgLines = preParseCG(cgText);

		ClumsySGMLLexer sgmlLexer = new ClumsySGMLLexer(sgmlText0);
		sgmlLexer.next();
		
		ClumsySGMLFilter.skipToTag(sgmlLexer, "TEXT");
		sgmlLexer.next();
		sgmlLexer.next();

		Reader sgmlText = ClumsySGMLFilter.readToTag(sgmlLexer, "/TEXT");
		
		BufferedReader sgmlLines = new BufferedReader(sgmlText);
		
		
		int ccgLine = -1;
		String line;
		
		while( (line = sgmlLines.readLine()) != null) {
			match(cgLines.get(++ccgLine), PATTERN_CG_S);
			
			char[] cs = line.toCharArray();
			
			Matcher entryMatcher;
			String sKey;
			char[] key;
			int j;

			entryMatcher = PATTERN_CG_ENTRY.matcher(cgLines.get(++ccgLine));
			entryMatcher.matches();
			sKey = entryMatcher.group(1);
			key = sKey.toCharArray();
			j = 0;
			if (key[j] == '$')
				j++;
			
			int next = 0;
			while(isWhitespace(cs[next]) || cs[next] == '"') {
				out.printf("%c", cs[next]);
				next++;
			}

			for (int i = next; i < cs.length; i++) {
				if (j == key.length || isWhitespace(key[j])) {
					// match!
					emitToken(line, next, i, entryMatcher);
					
					while(isWhitespace(cs[i]) || cs[i] == '"') {
						out.printf("%c", cs[i]);
						i++;
					}
					next = i;
					
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLines.get(++ccgLine));
					entryMatcher.matches();
					sKey = entryMatcher.group(1);
					key = sKey.toCharArray();
					j = 0;
					if (key[j] == '$')
						j++;
				}
				
				if (key[j] == '=') {
					while(isWhitespace(cs[i]))
						i++;
					
					j++;
				}
				
				if (toLowerCase(cs[i]) == toLowerCase(key[j]))
					j++;
				else if (toLowerCase(cs[i]) == 'à' && toLowerCase(key[j]) == 'a' && innerMatcher != null)
					j++;
				else if (toLowerCase(cs[i]) == 'à' && toLowerCase(key[j]) == 'a' && key.length == j + 1) {
					// double-match
					innerMatcher = entryMatcher;
					
					i--;
					
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLines.get(++ccgLine));
					entryMatcher.matches();
					sKey = entryMatcher.group(1);
					key = sKey.toCharArray();
					j = 0;
					if (key[j] == '$')
						j++;
				} else if(toLowerCase(cs[i]) == 'n' && toLowerCase(key[j]) == 'e' && toLowerCase(key[j+1]) == 'm' && key.length == j+2) {
					// match!
					i++;
					emitToken(line, next, i, entryMatcher);
					next = i;
					i--;
					
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLines.get(++ccgLine));
					entryMatcher.matches();
					sKey = entryMatcher.group(1);
					key = sKey.toCharArray();
					j = 0;
					if (key[j] == '$')
						j++;
				} else if(j > 0 && toLowerCase(key[j-1]) == 'd' && toLowerCase(key[j]) == 'e' && key.length == j + 1) {
					emitToken(line, next, i, entryMatcher);
					next = i;
					i--;
					
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLines.get(++ccgLine));
					entryMatcher.matches();
					sKey = entryMatcher.group(1);
					key = sKey.toCharArray();
					j = 0;
					if (key[j] == '$')
						j++;
				} else if(j > 0 && toLowerCase(key[j-1]) == 'p' && toLowerCase(key[j]) == 'o' && toLowerCase(key[j+1]) == 'r' && key.length == j + 2 && toLowerCase(cs[i]) == 'e' && toLowerCase(cs[i+1]) == 'l') {
					// match!
					i++; i++;
					emitToken(line, next, i, entryMatcher);
					next = i;
					i--;
					
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLines.get(++ccgLine));
					entryMatcher.matches();
					sKey = entryMatcher.group(1);
					key = sKey.toCharArray();
					j = 0;
					if (key[j] == '$')
						j++;
					
				} else if (key[j] == '¶') {
					entryMatcher = PATTERN_CG_PENTRY.matcher(cgLines.get(ccgLine));
					entryMatcher.matches();
					emitToken("", 0, entryMatcher);
					match(cgLines.get(++ccgLine), PATTERN_CG_S_S);
					match(cgLines.get(++ccgLine), PATTERN_CG_S);
					i--;
					
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLines.get(++ccgLine));
					entryMatcher.matches();
					sKey = entryMatcher.group(1);
					key = sKey.toCharArray();
					j = 0;
					if (key[j] == '$')
						j++;
				} else {
					bailOut(cs, next, key, j, i);
				}
				
			}

			if (j == key.length) {
				// match!
				emitToken(line, next, entryMatcher);
			} else {
				bailOut(cs, next, key, j, cs.length);
			}

			entryMatcher = PATTERN_CG_PENTRY.matcher(cgLines.get(++ccgLine));
			entryMatcher.matches();
			emitToken(entryMatcher);
			
			match(cgLines.get(++ccgLine), PATTERN_CG_S_S);
			
		}
		
		sgmlText.close();

		ClumsySGMLFilter.skipToTag(sgmlLexer, "");
	}

	private ArrayList<String> preParseCG(Reader cgText) throws IOException {
		ClumsySGMLLexer lexer = new ClumsySGMLLexer(cgText);
		
		lexer.next();
		ClumsySGMLFilter.skipToTag(lexer, "TEXT");
		lexer.next();
		lexer.next();
		BufferedReader reader = new BufferedReader(ClumsySGMLFilter.readToTag(lexer, "/TEXT"));
		
		ArrayList<String> cgLines = new ArrayList<String>();
		String line;
		while ((line = reader.readLine()) != null) {
			cgLines.add(line);
		}
		reader.close();
		
		ClumsySGMLFilter.skipToTag(lexer, "");
		
		return cgLines;
	}

	private void bailOut(char[] cs, int from, char[] key, int j, int to) throws ParserException {
		StringBuilder builder = new StringBuilder();
		builder.append("Mismatch at (").append(to).append(", ").append(j).append(")\n");
		builder.append("text = ").append(new String(cs)).append("\n");
		builder.append("key = ").append(new String(key)).append("\n");
		builder.append("next = ").append(from).append("\n");
		ParserException up = new ParserException(builder.toString());
		throw up; // ha ha
	}

	private void emitToken(String text, int from, Matcher entryMatcher) {
		emitToken(text, from, text.length(), entryMatcher);
	}

	private void emitToken(Matcher entryMatcher) {
		emitToken("\n", 0, entryMatcher);
	}

	private void emitToken(String text, int from, int to, Matcher entryMatcher) {
		String attributes = entryMatcher.group(2);
		if (innerMatcher == null)
			out.printf("<token a=\"%s\">%s</token>", escape(attributes), text.substring(from, to));
		else {
			int prefixLength = innerMatcher.group(1).length();
			out.printf("<token a=\"%s\"><token a=\"%s\">%s</token>%s</token>", escape(attributes), escape(innerMatcher.group(2)), text.substring(from, from + prefixLength), text.substring(from + prefixLength, to));
			innerMatcher = null;
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
}
