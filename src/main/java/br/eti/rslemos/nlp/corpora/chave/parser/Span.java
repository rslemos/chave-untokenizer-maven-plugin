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

import java.util.Comparator;

import br.eti.rslemos.nlp.corpora.chave.parser.Span;

public class Span {
	public static final class SpanComparatorByOffsets implements Comparator<Span> {
		public int compare(Span o1, Span o2) {
			int fromdist = o1.from - o2.from;
			int todist = o2.to - o1.to;
			
			return fromdist != 0 ? fromdist : todist;
		}
	}

	public final int from;
	public final int to;
	public final int entry;
	
	public Span(int from, int to, int entry) {
		this.from = from;
		this.to = to;
		this.entry = entry;
	}
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		
		if (o == this)
			return true;
		
		if (!(o instanceof Span))
			return false;
		
		Span m = (Span) o;
		return this.from == m.from && 
			this.to == m.to &&
			this.entry == m.entry;
	}
	
	public int hashCode() {
		return to * 3 + from * 5 + entry * 7;
	}

	public String toString() {
		return "{" + from + ", " + to + "} -> " + entry;
	}
	
	public static Span span(int from, int to, int i) {
		return new Span(from, to, i);
	}
}