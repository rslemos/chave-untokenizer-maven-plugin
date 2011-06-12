package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.DELETION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.INSERTION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.MATCH;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.SUBSTITUTION;
import static br.eti.rslemos.nlp.corpora.chave.parser.AdaptativeDamerauLevenshteinDistance.AlignOp.TRANSPOSITION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	private char c0;
	private char c1;

	private int j;

	private final int matchcost;
	private final int substcost;
	private final int insertcost;
	private final int delcost;
	private final int transpcost;
	
	public AdaptativeDamerauLevenshteinDistance(char[] key) {
		this(key, 0, 1, 1, 1, 1);
	}
	
	public AdaptativeDamerauLevenshteinDistance(char[] key, int matchcost, int substcost, int insertcost, int delcost, int transpcost) {
		this.key = key;
		this.matchcost = matchcost;
		this.substcost = substcost;
		this.insertcost = insertcost;
		this.delcost = delcost;
		this.transpcost = transpcost;
		
		d2 = new int[key.length + 1];
		int[] op;
		
		history.add(op = new int[key.length + 1]);
		
		for(int i=0; i<=key.length; i++) {
			d2[i] = i * insertcost;
			op[i] = INSERTION.mask;
		}

		j = 1;
		op[0] = 0;
	}

	public int append(char c2) {
		return append(matchcost, substcost, insertcost, delcost, transpcost, c2);
	}
	
	public int append(int matchcost, int substcost, int insertcost, int delcost, int transpcost, char c2) {
		d0 = d1;
		d1 = d2;
		d2 = new int[key.length + 1];
		int[] op;
		
		history.add(op = new int[key.length + 1]);
		
		d2[0] = j * delcost;
		op[0] = DELETION.mask;
		
		for(int i=1; i<=key.length; i++) {
			int ifmatch = (key[i-1] == c2 ? d1[i-1] + matchcost : Integer.MAX_VALUE);
			int ifinsert = d2[i-1] + insertcost;
			int ifdelete = d1[i] + delcost;
			int ifsubst = d1[i-1] + substcost;
			int iftransp = i > 1 && j > 1 && key[i-1] == c1 && key[i-2] == c2 ? d0[i-2] + transpcost : Integer.MAX_VALUE;

			d2[i] = min(ifmatch, ifinsert, ifdelete, ifsubst, iftransp);
			
			if (d2[i] == ifmatch) op[i] |= MATCH.mask;
			if (d2[i] == ifinsert) op[i] |= INSERTION.mask;
			if (d2[i] == ifdelete) op[i] |= DELETION.mask;
			if (d2[i] == ifsubst) op[i] |= SUBSTITUTION.mask;
			if (d2[i] == iftransp) op[i] |= TRANSPOSITION.mask;
		}
		
		c0 = c1;
		c1 = c2;
		j++;
		
		return getDistance();
	}
	
	public void undo() {
		if (j < 2 || d0 == null && j > 2)
			throw new IllegalStateException("Can't undo");
		
		d2 = d1;
		d1 = d0;
		d0 = null;

		history.remove(history.size() - 1);
		
		j--;
		
		c1 = c0;
		c0 = 0;
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

	public Set<AlignOp[]> getAlignments() {
		Set<List<AlignOp>> alignments = getAlignments(history.size()-1, key.length);
		
		Set<AlignOp[]> result = new HashSet<AlignOp[]>(alignments.size());
		for (List<AlignOp> alignment : alignments) {
			result.add(alignment.toArray(new AlignOp[alignment.size()]));
		}
		
		return result;
	}

	private Set<List<AlignOp>> getAlignments(int j, int i) {
		
		if (j == 0 && i == 0)
			return Collections.singleton((List<AlignOp>)new ArrayList<AlignOp>());
		
		Set<List<AlignOp>> result = new HashSet<List<AlignOp>>();
		int[] op = history.get(j);
		
		if ((op[i] & MATCH.mask) > 0) {
			result.addAll(getAlignmentsAndAppend(j-1, i-1, MATCH));
		}
		
		if ((op[i] & SUBSTITUTION.mask) > 0) {
			result.addAll(getAlignmentsAndAppend(j-1, i-1, SUBSTITUTION));
		}
		
		if ((op[i] & DELETION.mask) > 0) {
			result.addAll(getAlignmentsAndAppend(j-1, i, DELETION));
		} 

		if ((op[i] & INSERTION.mask) > 0) {
			result.addAll(getAlignmentsAndAppend(j, i-1, INSERTION));
		}
		
		if ((op[i] & TRANSPOSITION.mask) > 0) {
			result.addAll(getAlignmentsAndAppend(j-2, i-2, TRANSPOSITION));
		} 
		
		return result;
	}

	private Set<List<AlignOp>> getAlignmentsAndAppend(int j, int i, AlignOp alignop) {
		Set<List<AlignOp>> alignments = getAlignments(j, i);
		
		for (List<AlignOp> alignment : alignments) {
			alignment.add(alignop);
		}
		
		return alignments;
	}
}