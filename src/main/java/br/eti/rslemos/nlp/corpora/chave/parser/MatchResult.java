package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.CharBuffer;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

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

	public void apply(CharBuffer buffer, List<Entry<String, String>> cg, Handler handler) {
		if (from > 0)
			throw new IllegalStateException("Cannoy apply if data must be skipped");
		
		char[] data = new char[getMatchLength()];
		buffer.get(data);
		
		@SuppressWarnings("unchecked")
		Entry<String, String> entries[] = new Entry[consume];

		if (cg != null) {
			for (int i = 0; i < consume; i++) {
				entries[i] = cg.remove(0);
			}
		}
		
		if (handler != null) {
			if (positions.length == 0) {
				if (data.length > 0)
					handler.characters(data);
			} else if (positions.length == 2) {
				handler.startToken(entries[0].getValue());
				handler.characters(data);
				handler.endToken();
			} else if (positions.length == 4) {
				if (positions[2] >= positions[1]) {
					// non-intersecting
					handler.startToken(entries[0].getValue());
					handler.characters(subchar(data, positions[0], positions[1]));
					handler.endToken();
					handler.startToken(entries[1].getValue());
					handler.characters(subchar(data, positions[2], positions[3]));
					handler.endToken();
				} else {
					// intersecting
					if (positions[0] == positions[2]) {
						// 0th inside 1st
						handler.startToken(entries[1].getValue());
						handler.startToken(entries[0].getValue());
						handler.characters(subchar(data, positions[0], positions[1]));
						handler.endToken();
						handler.characters(subchar(data, positions[1], positions[3]));
						handler.endToken();
					} else if (positions[3] == positions[1]) {
						// 1st inside 0th
						handler.startToken(entries[0].getValue());
						handler.characters(subchar(data, positions[0], positions[2]));
						handler.startToken(entries[1].getValue());
						handler.characters(subchar(data, positions[2], positions[3]));
						handler.endToken();
						handler.endToken();
					} else {
						// overlapping
						handler.startPseudoToken(entries[0].getValue());
						handler.characters(subchar(data, positions[0], positions[2]));
						handler.startPseudoToken(entries[1].getValue());
						handler.characters(subchar(data, positions[2], positions[1]));
						handler.endPseudoToken();
						handler.characters(subchar(data, positions[1], positions[3]));
						handler.endPseudoToken();
					}
				}
			} else
				throw new UnsupportedOperationException();
		}
	}
	
	private char[] subchar(char[] data, int from, int to) {
		if (to == this.to + 1)
			to = this.to;
		
		char[] result = new char[to - from];
		System.arraycopy(data, from, result, 0, to - from);
		
		return result;
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
}