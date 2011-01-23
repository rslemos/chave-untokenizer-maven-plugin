package br.eti.rslemos.nlp.corpora.chave.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class CGEntry {

	private static final Pattern PATTERN_CG_ENTRY = Pattern.compile("^([^\t]*)(?:\t(.*))?$");

	private String key;
	private String value;

	public CGEntry(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}
	
	public String toString() {
		return String.valueOf(key);
	}
	
	public static CGEntry entry(String key, String value) {
		return new CGEntry(key, value);
	}

	public static List<CGEntry> loadFromReader(Reader cgText) throws IOException {
		@SuppressWarnings("unchecked")
		List<String> lines = IOUtils.readLines(cgText);
		
		ArrayList<CGEntry> result = new ArrayList<CGEntry>();
	
		for (String line : lines) {
			if (line.startsWith("<"))
				continue;
			
			Matcher matcher = PATTERN_CG_ENTRY.matcher(line);
			if (!matcher.matches()) 
				continue;
			
			result.add(entry(matcher.group(1), matcher.group(2)));
		}
	
		result.trimToSize();
		
		return result;
	}

}