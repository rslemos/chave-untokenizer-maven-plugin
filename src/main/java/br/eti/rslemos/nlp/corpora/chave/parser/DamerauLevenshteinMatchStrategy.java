package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

public class DamerauLevenshteinMatchStrategy implements MatchStrategy {

	private final int maxDistance;

	public DamerauLevenshteinMatchStrategy(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public MatchResult match(CharBuffer buffer, List<String> cg, boolean noMoreData) throws BufferUnderflowException {
		String key0 = cg.get(0);

		if (cg.size() > 1) {
			String key1 = cg.get(1);
			if (key1.startsWith("$"))
				key1 = key1.substring(1);
			
			if (Arrays.binarySearch(new String[] { ",", "-", ".", ":", ";" }, key1) >= 0) {
				if (key0.endsWith(key1)) {
					key0 = key0.substring(0, key0.length() - key1.length());
				}
			}
		}

		CharBuffer tempBuffer = buffer.slice();

		char[] cs = new char[key0.length()];
		try {
			tempBuffer.get(cs);
		} catch (BufferUnderflowException e) {
			return null;
		}
		
		String[] slices = new String(cs).split("\\p{Space}");
		if (slices.length == 0)
			return null;
		
		char[] cs1 = slices[0].toCharArray();
		if (cs1.length < 2)
			return null;
		
		if (levenshteinDistance(key0.toCharArray(), cs1) <= maxDistance) {
			return new MatchResult(buffer, 0, cs1.length, 1, 0, cs1.length);
		} else
			return null;
	}

	private static int levenshteinDistance(char[] cs1, char[] cs2) {
		int d[][] = new int [cs1.length + 1][cs2.length + 1];

		for(int i=0; i<=cs1.length; i++) {
			d[i][0] = i;
		}
		
		for(int j=0; j<=cs2.length; j++) {
			d[0][j] = j;
		}
		
		for(int j=1; j<=cs2.length; j++) {
			for(int i=1; i<=cs1.length; i++) {
				int cost;
				
				if (cs1[i-1] == cs2[j-1])
					cost = 0;
				else
					cost = 1;
				
				d[i][j] = Math.min(Math.min(d[i-1][j] + 1, d[i][j-1] + 1), d[i-1][j-1] + cost);

				if (i > 1 && j > 1 && cs1[i-1] == cs2[j-2] && cs1[i-2] == cs2[j-1])
					d[i][j] = Math.min(d[i][j], d[i-2][j-2] + cost);
			}
		}
		
		return d[cs1.length][cs2.length];
	}
}
