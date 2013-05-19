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

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class DirectMatchStrategy extends AbstractMatchStrategy {
	
	public Set<Match> matchAll(int from, int to, int start, int end) {
		Set<Match> matches = new LinkedHashSet<Match>();

		for (int i = start; i < end; i++) {
			addAllMatches(from, to, i, matches);
		}
		
		return matches;
	}

	protected void addAllMatches(int from, int to, int i, Set<Match> matches) {
		for (Match match : matchKey(from, to, getKey(i))) {
			matches.add(match.adjust(0, i, this.getClass()));
		}
	}

	private Set<Match> matchKey(int from, int to, String key0) {
		return matcher.matchKey(from, to, key0, span(0, key0.length(), 0));
	}

	protected String getKey(int i) {
		String key0 = getKey(cg.get(i));
		
		// TODO: retirar este bacalho quando o DL for religado;
		// este bacalho resolve o caso "sra." + "$."; o DL conseguiria capturar este caso
		if (cg.size() > i + 1) {
			String key1 = getKey(cg.get(i + 1));
			
			if (Arrays.binarySearch(new String[] { ",", "-", ".", ":", ";" }, key1) >= 0) {
				if (key0.endsWith(key1)) {
					key0 = key0.substring(0, key0.length() - key1.length());
				}
			}
		}
		
		return key0;
	}

	private static String getKey(String currentKey) {
		currentKey = currentKey.split(" ")[0];
		
		if ("$--".equals(currentKey))
			currentKey = "-";
		
		if (currentKey.startsWith("$"))
			currentKey = currentKey.substring(1);
		else
			currentKey = currentKey.replaceAll("=", " ");
		
		return currentKey;
	}
}