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