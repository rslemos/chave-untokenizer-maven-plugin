package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.util.List;


public final class Match {
	private int from;
	private int to;
	
	private final Span[] matches;
	
	public Match(int from, int to, Span... matches) {
		this.from = from;
		this.to = to;
		this.matches = matches;
	}
	
	public static Span result(int from, int to, int i) {
		return new Span(from, to, i);
	}

	public void apply(AnnotationSet annotationSet, List<CGEntry> cg) throws InvalidOffsetException {
		for (Span span : getMatches()) {
			if (span.from >= 0 && span.to >= 0) {
				FeatureMap features = new SimpleFeatureMapImpl();
				features.put("match", cg.get(span.entry).getKey());
				features.put("cg", cg.get(span.entry).getValue());
				annotationSet.add((long)span.from, (long)span.to, "token", features);
			}
		}
	}

	public Match adjust(int k, int i) {
		from += k;
		to += k;
		for (int j = 0; j < matches.length; j++) {
			matches[j] = new Span(matches[j].from + k, matches[j].to + k, matches[j].entry + i);
		}
		
		return this;
	}

	public int getMatchLength() {
		return to - from;
	}

	public int getSkipLength() {
		return from;
	}

	public int getFrom() {
		return from;
	}
	
	public int getTo() {
		return to;
	}
	
	public Span[] getMatches() {
		return matches;
	}

	public int getConsume() {
		return matches.length;
	}
	
	public static class Span {
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
	}
}
