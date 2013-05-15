/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "chave-untokenizer-maven-plugin"
 * Copyright 2013  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.nlp.corpora.chave.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.AbstractList;
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
	
	public static List<String> onlyKeys(final List<CGEntry> cg) {
		return new AbstractList<String>() {
			@Override
			public String get(int index) {
				return cg.get(index).getKey();
			}

			@Override
			public int size() {
				return cg.size();
			}
		};
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