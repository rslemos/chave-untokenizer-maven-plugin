package br.eti.rslemos.nlp.corpora.chave.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private static final Formatter FORMATTER = new Formatter();

	private static final Pattern PATTERN_CG_ENTRY = Pattern.compile("^([^\t]*)(?:\t(.*))?$");

	private Handler out;
	
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
		
		parse1(cgLines, sgmlText);

		ClumsySGMLFilter.skipToTag(sgmlLexer, "");
	}

	void parse1(List<Entry<String, String>> cg, Reader sgml) throws IOException, ParserException {
		MatchStrategy[] strategies = {
				new DirectMatchStrategy(),
				new EncliticMatchStrategy(),
				new ContractionDeMatchStrategy(),
				new ContractionEmMatchStrategy(),
				new ContractionAMatchStrategy(),
				new ContractionPorMatchStrategy(),
				new QuotesMatchStrategy(),
				new WhitespaceMatchStrategy(),
				new SentenceMarkerMatchStrategy(),
				new NewLineMatchStrategy(),
				new DamerauLevenshteinMatchStrategy(1),
			};

		parser1(strategies, cg, sgml);
	}

	private void parser1(MatchStrategy[] strategies, List<Entry<String, String>> cg, Reader sgml) throws IOException, ParserException {
		LinkedList<Entry<String, String>> cg1 = new LinkedList<Entry<String, String>>(cg);
		
		CharBuffer buffer = CharBuffer.allocate(65536);
		sgml.read(buffer);
		buffer.flip();

outer:
		while (!cg1.isEmpty()) {
			try {
				ArrayList<MatchResult> mementos = new ArrayList<MatchResult>(strategies.length);
				
				for (MatchStrategy strategy : strategies) {
					mementos.add(strategy.match(buffer, cg1, true));
				}
				
				do {} while (mementos.remove(null));
				
				if (mementos.size() > 0) {
					MatchResult best = mementos.get(0);
					int bestLength = -1;
					for (MatchResult memento : mementos) {
						bestLength = best != null ? best.getMatchLength() : -1;
						if (memento.getMatchLength() > bestLength) {
							best = memento;
						}
					}
					if (best != null) {
						best.apply(out);
						continue outer;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			int remaining = buffer.remaining();
			char[] dump = new char[remaining];
			buffer.get(dump);
			
			FORMATTER.format("%d-th entry: %s; buffer at %d\nDump remaining buffer (%d): %s\n", cg.size() - cg1.size(), cg1.get(0).getKey(), buffer.position(), remaining, new String(dump));
			throw new ParserException(FORMATTER.out().toString());
		}
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

	static class Entry<K, V> {

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
