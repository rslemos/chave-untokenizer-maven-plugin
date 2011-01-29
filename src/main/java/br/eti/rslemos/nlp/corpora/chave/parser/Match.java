package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public final class Match {
	private int from;
	private int to;
	
	private Set<Span> spans;
	
	public Match(int from, int to, Span... spans) {
		this(from, to, new LinkedHashSet<Span>(Arrays.asList(spans)));
	}
	
	public Match(int from, int to, Set<Span> spans) {
		this.from = from;
		this.to = to;
		this.spans = spans;
	}
	
	public void apply(AnnotationSet annotationSet, List<CGEntry> cg) throws InvalidOffsetException {
		for (Span span : getSpans()) {
			if (span.from >= 0 && span.to >= 0) {
				FeatureMap features = new SimpleFeatureMapImpl();
				features.put("match", cg.get(span.entry).getKey());
				features.put("cg", cg.get(span.entry).getValue());
				annotationSet.add((long)span.from, (long)span.to, "token", features);
			}
		}
	}

	public Match adjust(int k, int i) {
		Set<Span> spans = new LinkedHashSet<Span>(this.spans.size());
		
		for (Span span : this.spans) {
			span = Span.span(span.from + k, span.to + k, span.entry + i);
			spans.add(span);
		}
		
		return new Match(from + k, to + k, spans);
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
	
	public Set<Span> getSpans() {
		return spans;
	}

	public int getConsume() {
		return spans.size();
	}
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		
		if (o == this)
			return true;
		
		if (!(o instanceof Match))
			return false;
		
		Match m = (Match) o;
		
		return this.from == m.from &&
			this.to == m.to &&
			(this.spans != null ? this.spans.equals(m.spans) : m.spans == null);
	}
	
	public int hashCode() {
		return this.to * 3 + this.from * 5 + (this.spans != null ? this.spans.hashCode() * 7 : 1); 
	}
	
	public String toString() {
		return "{" + from + ", " + to + "}/" + spans;
	}
	
	public static Match match(int from, int to, Span... spans) {
		return new Match(from, to, spans);
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

		public String toString() {
			return "{" + from + ", " + to + "} -> " + entry;
		}
		
		public static Match.Span span(int from, int to, int i) {
			return new Match.Span(from, to, i);
		}
	}
}
