package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.InvalidOffsetException;
import gate.util.SimpleFeatureMapImpl;

import java.util.List;


public final class MatchResult {
	private final int from;
	private final int to;
	private final int consume;
	
	private final int[] positions;
	
	public MatchResult(int from, int to, int consume, int... positions) {
		this.from = from;
		this.to = to;
		if (positions.length % 2 == 1)
			throw new IllegalArgumentException("unpaired position");
			
		this.consume = consume;
		this.positions = positions;
	
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
	}

	public void apply(AnnotationSet annotationSet, int k, List<CGEntry> cg, int i) throws InvalidOffsetException {
		for (Match match : getMatches()) {
			FeatureMap features = new SimpleFeatureMapImpl();
			features.put("match", cg.get(match.entry + i).getKey());
			features.put("cg", cg.get(match.entry + i).getValue());
			annotationSet.add((long)(match.from + k), (long)(match.to + k), "token", features);
		}
	}

	public void apply(AnnotationSet annotationSet, int k, List<CGEntry> cg, int i) throws InvalidOffsetException {
		for (Match match : getMatches()) {
			FeatureMap features = new SimpleFeatureMapImpl();
			features.put("match", cg.get(match.entry + i).getKey());
			features.put("cg", cg.get(match.entry + i).getValue());
			annotationSet.add((long)(match.from + k), (long)(match.to + k), "token", features);
		}
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
		Match[] result = new Match[positions.length / 2];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Match(positions[i*2], positions[i*2+1], i);
		}
		
		return result;
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

	public int getConsume() {
		return consume;
	}
}
