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

public class EncliticMatchStrategy extends AbstractMatchStrategy {

	public Set<Match> matchAll(int from, int to, int start, int end) {
		Set<Match> result = new LinkedHashSet<Match>();
		
		for (int i = start; i < end - 1; i++) {
			String key0 = cg.get(i).toLowerCase();
			String key1 = cg.get(i + 1).toLowerCase();
			
			if (Arrays.asList("la", "las", "lo", "los").contains(key1)) {
				String toMatch = null;
				
				if (key0.endsWith("ar-"))
					toMatch = key0.replaceAll("ar-$", "á-");
		
				if (key0.endsWith("er-"))
					toMatch = key0.replaceAll("er-$", "ê-");
				
				if (key0.endsWith("ir-"))
					toMatch = key0.replaceAll("ir-$", "i-");
				
				if (key0.endsWith("por-"))
					toMatch = key0.replaceAll("por-$", "po-");
				
				if (key0.endsWith("pôr-"))
					toMatch = key0.replaceAll("pôr-$", "pô-");
				
				if (toMatch != null) {
					toMatch += key1;
					
					for (Match match : matchAndUpdateCache(from, to, toMatch, key1)) {
						result.add(match.adjust(0, i, this.getClass()));
					}
				}
			}
		}
		
		return result;
	}

	private Set<Match> matchAndUpdateCache(int from, int to, String toMatch, String key1) {
		return matcher.matchKey(from, to, toMatch,
					span(0, toMatch.length() - key1.length(), 0),
					span(toMatch.length() - key1.length(), toMatch.length(), 1)
				);
	}

}
