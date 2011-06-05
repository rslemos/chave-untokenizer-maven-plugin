package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.ArrayList;
import java.util.LinkedList;

import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.*;

public class AdaptativeDamerauLevenshteinDistance {
	public static enum AlignOp {
		MATCH(0x1), INSERTION(0x2), DELETION(0x4), SUBSTITUTION(0x8), TRANSPOSITION(0x10);
		
		private final int mask;

		private AlignOp(int mask) {
			this.mask = mask;
		}
	}
	
	private final char[] key;
	
	private final ArrayList<int[]> history = new ArrayList<int[]>();
	
	private int d0[] = null;
	private int d1[] = null;
	private int d2[] = null;
	
	private char c1;

	private int j;
	
	public AdaptativeDamerauLevenshteinDistance(char[] key) {
		this.key = key;
		d2 = new int[key.length + 1];
		int[] op;
		
		history.add(op = new int[key.length + 1]);
		
		for(int i=0; i<=key.length; i++) {
			d2[i] = i;
			op[i] = INSERTION.mask;
		}

		j = 1;
		op[0] = 0;
	}
	
	public int append(char c2) {
		d0 = d1;
		d1 = d2;
		d2 = new int[key.length + 1];
		int[] op;
		
		history.add(op = new int[key.length + 1]);
		
		d2[0] = j;
		op[0] = DELETION.mask;
		
		for(int i=1; i<=key.length; i++) {
			int ifmatch = (key[i-1] == c2 ? d1[i-1] : Integer.MAX_VALUE);
			int ifinsert = d2[i-1] + 1;
			int ifdelete = d1[i] + 1;
			int ifsubst = d1[i-1] + 1;
			int iftransp = i > 1 && j > 1 && key[i-1] == c1 && key[i-2] == c2 ? d0[i-2] + 1 : Integer.MAX_VALUE;

			d2[i] = min(ifmatch, ifinsert, ifdelete, ifsubst, iftransp);
			
			if (d2[i] == ifmatch) op[i] |= MATCH.mask;
			if (d2[i] == ifinsert) op[i] |= INSERTION.mask;
			if (d2[i] == ifdelete) op[i] |= DELETION.mask;
			if (d2[i] == ifsubst) op[i] |= SUBSTITUTION.mask;
			if (d2[i] == iftransp) op[i] |= TRANSPOSITION.mask;
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

	public AlignOp[] getAlignment() {
		LinkedList<AlignOp> alignment = new LinkedList<AlignOp>();
		
		int j = history.size()-1;
		int i = key.length;
		
		while (j > 0 && i > 0) {
			int[] op = history.get(j);
			
			if ((op[i] & MATCH.mask) > 0) {
				alignment.addFirst(MATCH);
				i--;
				j--;
			} else if ((op[i] & SUBSTITUTION.mask) > 0) {
				alignment.addFirst(SUBSTITUTION);
				i--;
				j--;
			} else if ((op[i] & DELETION.mask) > 0) {
				alignment.addFirst(DELETION);
				j--;
			} else if ((op[i] & INSERTION.mask) > 0) {
				alignment.addFirst(INSERTION);
				i--;
			} else if ((op[i] & TRANSPOSITION.mask) > 0) {
				alignment.addFirst(TRANSPOSITION);
				i-=2;
				j-=2;
			} else {
				throw new IllegalStateException(op[i] + ": " + alignment.toString());
			}
		}
		
		return alignment.toArray(new AlignOp[alignment.size()]);
	}
}