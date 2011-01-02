package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;
import static java.lang.Character.toLowerCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private static final Pattern PATTERN_SGML_DOC = Pattern.compile("^<DOC>$");
	private static final Pattern PATTERN_SGML_DOCNO = Pattern.compile("^<DOCNO>(.*)</DOCNO>$");
	private static final Pattern PATTERN_SGML_DOCID = Pattern.compile("^<DOCID>(.*)</DOCID>$");
	private static final Pattern PATTERN_SGML_DATE = Pattern.compile("^<DATE>(.*)</DATE>$");
	private static final Pattern PATTERN_SGML_TEXT = Pattern.compile("^<TEXT>$");
	private static final Pattern PATTERN_SGML_S_TEXT = Pattern.compile("^</TEXT>$");
	private static final Pattern PATTERN_SGML_S_DOC = Pattern.compile("^</DOC>$");

	private static final Pattern PATTERN_CG_DOC = Pattern.compile("^<DOC>$");
	private static final Pattern PATTERN_CG_DOCNO = Pattern.compile("^<DOCNO\\.valor=(.*)>$");
	private static final Pattern PATTERN_CG_DOCID = Pattern.compile("^<DOCID\\.valor=(.*)>$");
	private static final Pattern PATTERN_CG_DATE = Pattern.compile("^<DATE\\.valor=(.*)>$");
	private static final Pattern PATTERN_CG_TEXT = Pattern.compile("^<TEXT>$");
	private static final Pattern PATTERN_CG_S_TEXT = Pattern.compile("^</TEXT>$");
	private static final Pattern PATTERN_CG_S_DOC = Pattern.compile("^</DOC>$");
	private static final Pattern PATTERN_CG_ENTRY = Pattern.compile("^([^\t]*)(?:\t(.*))?$");
	private static final Pattern PATTERN_CG_S = Pattern.compile("^<s>$");
	private static final Pattern PATTERN_CG_S_S = Pattern.compile("^</s>$");

	private static final Pattern PATTERN_CG_PENTRY = Pattern.compile("^(\\$¶)\t(.*)$");

	private Matcher innerMatcher = null;

	private PrintStream out;

	public Parser(PrintStream out) {
		this.out = out;
	}

	public boolean parse(InputStreamReader cgText, InputStreamReader sgmlText) throws IOException {
		try {
			parse0(cgText, sgmlText);
			return true;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			return false;
		}
	}

	private void parse0(InputStreamReader cgText, InputStreamReader sgmlText) throws IOException, Exception {
		BufferedReader cgLines = new BufferedReader(cgText);
		BufferedReader sgmlLines = new BufferedReader(sgmlText);
		
		String line, cgLine;
		
		match(line = sgmlLines.readLine(), PATTERN_SGML_DOC);
		String docno = matchAndExtract(line = sgmlLines.readLine(), PATTERN_SGML_DOCNO);
		String docid = matchAndExtract(line = sgmlLines.readLine(), PATTERN_SGML_DOCID);
		String date = matchAndExtract(line = sgmlLines.readLine(), PATTERN_SGML_DATE);
		match(line = sgmlLines.readLine(), PATTERN_SGML_TEXT);
		
		match(cgLine = cgLines.readLine(), PATTERN_CG_DOC);
		String cgdocno = matchAndExtract(cgLine = cgLines.readLine(), PATTERN_CG_DOCNO);
		String cgdocid = matchAndExtract(cgLine = cgLines.readLine(), PATTERN_CG_DOCID);
		String cgdate = matchAndExtract(cgLine = cgLines.readLine(), PATTERN_CG_DATE);
		match(cgLine = cgLines.readLine(), PATTERN_CG_TEXT);

		while( (line = sgmlLines.readLine()) != null && !PATTERN_SGML_S_TEXT.matcher(line).matches()) {
			match(cgLine = cgLines.readLine(), PATTERN_CG_S);
			
			char[] cs = line.toCharArray();
			
			Matcher entryMatcher;
			String sKey;
			char[] key;
			int j;

			cgLine = cgLines.readLine();
			entryMatcher = PATTERN_CG_ENTRY.matcher(cgLine);
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
					
					cgLine = cgLines.readLine();
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLine);
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
					
					cgLine = cgLines.readLine();
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLine);
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
					
					cgLine = cgLines.readLine();
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLine);
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
					
					cgLine = cgLines.readLine();
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLine);
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
					
					cgLine = cgLines.readLine();
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLine);
					entryMatcher.matches();
					sKey = entryMatcher.group(1);
					key = sKey.toCharArray();
					j = 0;
					if (key[j] == '$')
						j++;
					
				} else if (key[j] == '¶') {
					entryMatcher = PATTERN_CG_PENTRY.matcher(cgLine);
					entryMatcher.matches();
					emitToken("", 0, entryMatcher);
					match(cgLine = cgLines.readLine(), PATTERN_CG_S_S);
					match(cgLine = cgLines.readLine(), PATTERN_CG_S);
					i--;
					
					cgLine = cgLines.readLine();
					entryMatcher = PATTERN_CG_ENTRY.matcher(cgLine);
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

			cgLine = cgLines.readLine();
			entryMatcher = PATTERN_CG_PENTRY.matcher(cgLine);
			entryMatcher.matches();
			emitToken(entryMatcher);
			
			match(cgLine = cgLines.readLine(), PATTERN_CG_S_S);
			
		}
		
		match(line, PATTERN_SGML_S_TEXT);
		match(line = sgmlLines.readLine(), PATTERN_SGML_S_DOC);
	}

	private void bailOut(char[] cs, int from, char[] key, int j, int to) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("Mismatch at (").append(to).append(", ").append(j).append(")\n");
		builder.append("text = ").append(new String(cs)).append("\n");
		builder.append("key = ").append(new String(key)).append("\n");
		builder.append("next = ").append(from).append("\n");
		Exception up = new Exception(builder.toString());
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

	private static String matchAndExtract(String line, Pattern pattern) {
		Matcher matcher = match(line, pattern);
		return matcher.group(1);
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
