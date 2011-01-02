import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	private static final Charset UTF8 = Charset.forName("UTF-8");

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

	private static Matcher innerMatcher = null;

	public static void main(String[] args) throws Exception {
		URL cg = Main.class.getResource("/pt_BR/CHAVEFolha/1994/01/01/124.cg");
		URL sgml = new URL(cg, "124.sgml");
		
		System.out.printf("cg = %s\n", cg);
		System.out.printf("sgml = %s\n", sgml);
		
		InputStream cgStream = cg.openStream();
		InputStream sgmlStream = sgml.openStream();
		
		InputStreamReader cgText = new InputStreamReader(cgStream, UTF8);
		InputStreamReader sgmlText = new InputStreamReader(sgmlStream, UTF8);
		
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
			int next = 0;
			
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
			
			for (int i = 0; i < cs.length; i++) {
				if (j == key.length || Character.isWhitespace(key[j])) {
					// match!
					emitToken(line, next, i, entryMatcher);
					
					while(Character.isWhitespace(cs[i]) || cs[i] == '"') {
						System.out.printf("%c", cs[i]);
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
					while(Character.isWhitespace(cs[i]))
						i++;
					
					j++;
				}
				
				if (Character.toLowerCase(cs[i]) == Character.toLowerCase(key[j]))
					j++;
				else if (cs[i] == 'à' && key[j] == 'a' && innerMatcher != null)
					j++;
				else if (cs[i] == 'à' && key[j] == 'a' && key.length == j + 1) {
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
				} else if(cs[i] == 'n' && key[j] == 'e' && key[j+1] == 'm' && key.length == j+2) {
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
				} else if(j > 0 && key[j-1] == 'd' && key[j] == 'e' && key.length == j + 1) {
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
					System.err.printf("Mismatch at (%d, %d)\n", i, j);
					System.err.printf("text = %s\n", new String(cs));
					System.err.printf("key = %s\n", new String(key));
					System.err.printf("next = %d\n", next);
					Exception up = new Exception();
					throw up; // ha ha
				}
				
			}

			if (j == key.length) {
				// match!
				emitToken(line, next, entryMatcher);
			} else {
				System.err.printf("Mismatch at (eol, %d)\n", j);
				System.err.printf("text = %s\n", new String(cs));
				System.err.printf("key = %s\n", new String(key));
				System.err.printf("next = %d\n", next);
				Exception up = new Exception();
				throw up; // ha ha
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

	private static void emitToken(String text, int from, Matcher entryMatcher) {
		emitToken(text, from, text.length(), entryMatcher);
	}

	private static void emitToken(Matcher entryMatcher) {
		emitToken("\n", 0, entryMatcher);
	}

	private static void emitToken(String text, int from, int to, Matcher entryMatcher) {
		String attributes = entryMatcher.group(2);
		if (innerMatcher == null)
			System.out.printf("<token a=\"%s\">%s</token>", escape(attributes), text.substring(from, to));
		else {
			int prefixLength = innerMatcher.group(1).length();
			System.out.printf("<token a=\"%s\"><token a=\"%s\">%s</token>%s</token>", escape(attributes), escape(innerMatcher.group(2)), text.substring(from, from + prefixLength), text.substring(from + prefixLength, to));
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
