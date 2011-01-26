package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.util.List;


public final class MatchResult {
	private int from;
	private int to;
	
	private final Match[] matches;
	
	public MatchResult(int from, int to, Match... matches) {
		this.from = from;
		this.to = to;
		this.matches = matches;
	}
	
	public static Match result(int from, int to, int i) {
		return new Match(from, to, i);
	}

	public void apply(AnnotationSet annotationSet, List<CGEntry> cg) throws InvalidOffsetException {
		for (Match match : getMatches()) {
			if (match.from >= 0 && match.to >= 0) {
				FeatureMap features = new SimpleFeatureMapImpl();
				features.put("match", cg.get(match.entry).getKey());
				features.put("cg", cg.get(match.entry).getValue());
				annotationSet.add((long)match.from, (long)match.to, "token", features);
			}
		}
	}

	public MatchResult adjust(int k, int i) {
		from += k;
		to += k;
		for (int j = 0; j < matches.length; j++) {
			matches[j] = new Match(matches[j].from + k, matches[j].to + k, matches[j].entry + i);
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
	
	public Match[] getMatches() {
		return matches;
	}

	public int getConsume() {
		return matches.length;
	}
	
	public static class Match {
		public final int from;
		public final int to;
		public final int entry;
		
		public Match(int from, int to, int entry) {
			this.from = from;
			this.to = to;
			this.entry = entry;
		}
	}
}
