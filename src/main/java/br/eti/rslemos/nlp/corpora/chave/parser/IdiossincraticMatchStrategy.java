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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IdiossincraticMatchStrategy extends DirectMatchStrategy {

	private List<Pair> matches = new ArrayList<Pair>();

	@Override
	protected String getKey(int i) {
		String key = super.getKey(i);
		
		for (Pair pair : matches) {
			if (pair.entry.equals(key))
				return pair.text;
		}
		
		throw new CutException();
	}

	
	@Override
	protected void addAllMatches(int from, int to, int i, Set<Match> matches) {
		try {
			super.addAllMatches(from, to, i, matches);
		} catch (CutException e) { /* swallow */ }
	}


	public void addMatch(String text, String entry) {
		matches.add(pair(text, entry));
	}

	private Pair pair(String text, String entry) {
		return new Pair(text, entry);
	}

}

class Pair {

	public final String text;
	public final String entry;

	public Pair(String text, String entry) {
		this.text = text;
		this.entry = entry;
	}
	
}

@SuppressWarnings("serial")
class CutException extends RuntimeException {
	
}