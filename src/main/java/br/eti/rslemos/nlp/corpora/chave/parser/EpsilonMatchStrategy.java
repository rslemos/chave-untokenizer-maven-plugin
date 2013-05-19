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

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import java.util.HashSet;
import java.util.Set;

public class EpsilonMatchStrategy extends AbstractMatchStrategy {

	@Override
	public Set<Match> matchAll(int from, int to, int start, int end) {
		Set<Match> result = new HashSet<Match>((to - from + 1) * (end - start));
		
		for (int i = from; i <= to; i++) {
			for (int j = start; j < end; j++) {
				result.add(match(i, i, this.getClass(), span(i, i, j)));
			}
		}
		
		if (result.size() > 1) {
			System.err.printf("\n%d matches between (%d, %d) for entries (%d, %d)\n", result.size(), from, to, start, end);
		}
		
		return result;
	}

}
