package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.util.List;


public final class MatchResult {
	private int from;
	private int to;
	private final int consume;
	
	private final Match[] matches;
	
	public MatchResult(int from, int to, int consume, int... positions) {
		this.from = from;
		this.to = to;
		if (positions.length % 2 == 1)
			throw new IllegalArgumentException("unpaired position");
			
		this.consume = consume;
	
		for (int i = 0; i < positions.length; i += 2) {
			if (positions[i] == -1 || positions[i+1] == -1)
				throw new IllegalArgumentException();

			if (positions[i] < from || positions[i] > to)
				throw new IndexOutOfBoundsException(i + "th position outside [" + from + ", " + to + "[");
			
			if (positions[i+1] < from || positions[i+1] > to)
				throw new IndexOutOfBoundsException((i+1) + "th position outside [" + from + ", " + to + "[");
		}
		
		if (consume < positions.length/2)
			throw new IllegalArgumentException("Must consume at least the same number of emited tokens");
		
		matches = new Match[positions.length / 2];
		for (int i = 0; i < matches.length; i++) {
			matches[i] = new Match(positions[i*2], positions[i*2+1], i);
		}
	}

	public void apply(AnnotationSet annotationSet, List<CGEntry> cg) throws InvalidOffsetException {
		for (Match match : getMatches()) {
			FeatureMap features = new SimpleFeatureMapImpl();
			features.put("match", cg.get(match.entry).getKey());
			features.put("cg", cg.get(match.entry).getValue());
			annotationSet.add((long)match.from, (long)match.to, "token", features);
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
		return consume;
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
