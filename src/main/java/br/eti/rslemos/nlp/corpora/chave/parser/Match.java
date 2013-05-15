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

import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class Match {
	public final int from;
	public final int to;
	
	private final Set<Span> spans;
	private final Class<? extends MatchStrategy> strategy;
	
	public class Span extends br.eti.rslemos.nlp.corpora.chave.parser.Span {
		public Span(int from, int to, int entry) {
			super(from, to, entry);
		}

		public Match getMatch() {
			return Match.this;
		}

//		@Override
//		public boolean equals(Object o) {
//			if (!super.equals(o))
//				return false;
//			
//			if (!(o instanceof Span))
//				return false;
//			
//			Span m = (Span) o;
//			return Match.this == m.getMatch(); 
//		}

//		@Override
//		public int hashCode() {
//			return 11 * System.identityHashCode(Match.this) + super.hashCode();
//		}
	}
	
	private Match(int from, int to, Class<? extends MatchStrategy> strategy, Set<Span> spans) {
		this.from = from;
		this.to = to;
		this.strategy = strategy;
		this.spans = spans;
	}
	
	public void apply(AnnotationSet annotationSet, List<CGEntry> cg) throws InvalidOffsetException {
		List<FeatureMap> tokens = new ArrayList<FeatureMap>();
		for (Span span : getSpans()) {
			if (span.from >= 0 && span.to >= 0) {
				FeatureMap features = new SimpleFeatureMapImpl();
				features.put("index", span.entry);
				features.put("match", cg.get(span.entry).getKey());
				features.put("cg", cg.get(span.entry).getValue());
				annotationSet.add((long)span.from, (long)span.to, "token", features);
				
				tokens.add(features);
			}
		}
		
		FeatureMap fullMatch = new SimpleFeatureMapImpl();

		fullMatch.put("strategy", strategy.getName());
		fullMatch.put("tokens", tokens.toArray(new FeatureMap[tokens.size()]));
		
		annotationSet.add((long)from, (long)to, "match", fullMatch);
	}

	public Match adjust(int k, int i, Class<? extends MatchStrategy> strategy) {
		Set<Span> spans = new LinkedHashSet<Span>(this.spans.size());
		Match match = new Match(from + k, to + k, strategy, spans);
		
		for (Span span : this.spans) {
			span = match.new Span(span.from + k, span.to + k, span.entry + i);
			spans.add(span);
		}
		
		return match;
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
	
	private Match(int from, int to, Class<? extends MatchStrategy> strategy, br.eti.rslemos.nlp.corpora.chave.parser.Span... nakedSpans) {
		this.from = from;
		this.to = to;
		this.strategy = strategy;
		this.spans = new LinkedHashSet<Span>(nakedSpans.length);
		
		for (br.eti.rslemos.nlp.corpora.chave.parser.Span nakedSpan : nakedSpans) {
			Span span = new Span(nakedSpan.from, nakedSpan.to, nakedSpan.entry);
			spans.add(span);
		}
	}
	
	public static Match match(int from, int to, br.eti.rslemos.nlp.corpora.chave.parser.Span... spans) {
		return new Match(from, to, null, spans);
	}
}
