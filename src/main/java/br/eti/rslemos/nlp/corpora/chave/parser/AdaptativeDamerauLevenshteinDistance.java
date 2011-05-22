package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.LinkedList;

public class AdaptativeDamerauLevenshteinDistance {
	private static final int INSERTION = 0x1;
	private static final int DELETION = 0x2;
	private static final int SUBSTITUTION = 0x4;
	private static final int TRANSPOSITION = 0x8;
	
	private final char[] key;
	
	private final LinkedList<int[]> history = new LinkedList<int[]>();
	
	private int d0[] = null;
	private int d1[] = null;
	private int d2[] = null;
	
	private char c1;

	private int j;
	
	public AdaptativeDamerauLevenshteinDistance(char[] key) {
		this.key = key;
		d2 = new int[key.length + 1];
		int[] op;
		
		history.addFirst(op = new int[key.length + 1]);
		
		for(int i=0; i<=key.length; i++) {
			d2[i] = i;
			op[i] = INSERTION;
		}

		j = 1;
		op[0] = 0;
	}
	
	public int append(char c2) {
		d0 = d1;
		d1 = d2;
		d2 = new int[key.length + 1];
		int[] op;
		
		history.addFirst(op = new int[key.length + 1]);
		
		d2[0] = j;
		op[0] = DELETION;
		
		for(int i=1; i<=key.length; i++) {
			int ifnone = (key[i-1] == c2 ? d1[i-1] : Integer.MAX_VALUE);
			int ifinsert = d2[i-1] + 1;
			int ifdelete = d1[i] + 1;
			int ifsubst = d1[i-1] + 1;
			int iftransp = i > 1 && j > 1 && key[i-1] == c1 && key[i-2] == c2 ? d0[i-2] + 1 : Integer.MAX_VALUE;

			d2[i] = min(ifnone, ifinsert, ifdelete, ifsubst, iftransp);
			
			if (d2[i] == ifinsert) op[i] |= INSERTION;
			if (d2[i] == ifdelete) op[i] |= DELETION;
			if (d2[i] == ifsubst) op[i] |= SUBSTITUTION;
			if (d2[i] == iftransp) op[i] |= TRANSPOSITION;
		}
		
		c1 = c2;
		j++;
		
		return getDistance();
	}

	private static int min(int... values) {
		int result = Integer.MAX_VALUE;
		
		for (int value : values) {
			result = Math.min(result, value);
		}
		
		return result;
	}

	public int getDistance() {
		return d2[key.length];
	}

	public int getMinimalDistance() {
		return min(d2);
	}
	
//	public int[] showPaths() {
//
//		LinkedList<Integer> result = new LinkedList<Integer>();
//		
//		int i = key.length;
//		int j = 0;
//		while (i > 0 && j < history.size()) {
//			int[] op = history.get(j); 
//			if ((op[i] & TRANSPOSITION) > 0) {
//				result.addFirst(TRANSPOSITION);
//				i -= 2;
//				j += 2;
//			} else if ((op[i] & INSERTION) > 0) {
//				result.addFirst(INSERTION);
//				i -= 1;
//			} else if ((op[i] & DELETION) > 0) {
//				result.addFirst(DELETION);
//				j += 1;
//			} else if ((op[i] & SUBSTITUTION) > 0) {
//				result.addFirst(SUBSTITUTION);
//				i -= 1;
//				j += 1;
//			} else {
//				result.addFirst(0);
//				i -= 1;
//				j += 1;
//			}
//		}
//
//		return result.toArray(new int[result.size()]);
//	}
}