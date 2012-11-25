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
	
	public static final int ALL_CHAR_CLASSES =
			  (1 << Character.UNASSIGNED)
			| (1 << Character.UPPERCASE_LETTER)
			| (1 << Character.LOWERCASE_LETTER)
			| (1 << Character.TITLECASE_LETTER)
			| (1 << Character.MODIFIER_LETTER)
			| (1 << Character.OTHER_LETTER)
			| (1 << Character.NON_SPACING_MARK)
			| (1 << Character.ENCLOSING_MARK)
			| (1 << Character.COMBINING_SPACING_MARK)
			| (1 << Character.DECIMAL_DIGIT_NUMBER)
			| (1 << Character.LETTER_NUMBER)
			| (1 << Character.OTHER_NUMBER)
			| (1 << Character.SPACE_SEPARATOR)
			| (1 << Character.LINE_SEPARATOR)
			| (1 << Character.PARAGRAPH_SEPARATOR)
			| (1 << Character.CONTROL)
			| (1 << Character.FORMAT)
			
			| (1 << Character.PRIVATE_USE)
			| (1 << Character.SURROGATE)
			| (1 << Character.DASH_PUNCTUATION)
			| (1 << Character.START_PUNCTUATION)
			| (1 << Character.END_PUNCTUATION)
			| (1 << Character.CONNECTOR_PUNCTUATION)
			| (1 << Character.OTHER_PUNCTUATION)
			| (1 << Character.MATH_SYMBOL)
			| (1 << Character.CURRENCY_SYMBOL)
			| (1 << Character.MODIFIER_SYMBOL)
			| (1 << Character.OTHER_SYMBOL)
			| (1 << Character.INITIAL_QUOTE_PUNCTUATION)
			| (1 << Character.FINAL_QUOTE_PUNCTUATION);

	public static final int WHITESPACE_CLASSES = 
			  (1 << Character.COMBINING_SPACING_MARK)
			| (1 << Character.SPACE_SEPARATOR)
			| (1 << Character.LINE_SEPARATOR)
			| (1 << Character.PARAGRAPH_SEPARATOR);

	public static final int LETTER_CLASSES =
			  (1 << Character.UPPERCASE_LETTER)
			| (1 << Character.LOWERCASE_LETTER)
			| (1 << Character.TITLECASE_LETTER)
			| (1 << Character.MODIFIER_LETTER)
			| (1 << Character.OTHER_LETTER);

	private final char[] key;
	
	private final ArrayList<int[]> history = new ArrayList<int[]>();
	
	private int d0[] = null;
	private int d1[] = null;
	private int d2[] = null;
	
	private char c0;
	private char c1;

	private int j;

	private final Cost1[] matchCosts;
	private final Cost2[] substCosts;
	private final Cost1[] insertCosts;
	private final Cost1[] deleteCosts;
	private final Cost2[] transpositionCosts;
	
	public static class Cost1 {
		public final int charTypeMask;
		public final int cost;
		
		public Cost1(int charTypeMask, int cost) {
			this.charTypeMask = charTypeMask;
			this.cost = cost;
		}
	}
	
	public static class Cost2 {
		public final int char1TypeMask; // typically from
		public final int char2TypeMask; // typically to
		public final int cost;
		
		public Cost2(int char1TypeMask, int char2TypeMask, int cost) {
			this.char1TypeMask = char1TypeMask;
			this.char2TypeMask = char2TypeMask;
			this.cost = cost;
		}
	}
	
	public static Cost1 buildCost1FromMasks(int cost, int... masks) {
		int mask = 0;
		
		for (int i = 0; i < masks.length; i++) {
			mask |= masks[i];
		}
		
		return new Cost1(mask, cost);
	}
	
	public static Cost1 buildCost1FromClasses(int cost, int... classes) {
		int mask = 0;
		
		for (int i = 0; i < classes.length; i++) {
			mask |= 1 << classes[i];
		}
		
		return new Cost1(mask, cost);
	}

	
	public static Cost2 buildCost2FromMasks(int cost, int... masks) {
		int mask1 = 0;
		int mask2 = 0;
		
		for (int i = 0; i < masks.length; i+=2) {
			mask1 |= masks[i+0];
			mask2 |= masks[i+1];
		}
		
		return new Cost2(mask1, mask2, cost);
	}
	
	public static Cost2 buildCost2FromClasses(int cost, int... classes) {
		int mask1 = 0;
		int mask2 = 0;
		
		for (int i = 0; i < classes.length; i+=2) {
			mask1 |= 1 << classes[i+0];
			mask2 |= 1 << classes[i+1];
		}
		
		return new Cost2(mask1, mask2, cost);
	}

	public AdaptativeDamerauLevenshteinDistance(char[] key) {
		this(key, 0, 1, 1, 1, 1);
	}
	
	public AdaptativeDamerauLevenshteinDistance(char[] key, int matchcost, int substcost, int insertcost, int delcost, int transpcost) {
		this(key, constantCost1(matchcost), constantCost2(substcost), constantCost1(insertcost), constantCost1(delcost), constantCost2(transpcost));
	}

	public AdaptativeDamerauLevenshteinDistance(char[] key, Cost1[] matchCosts, Cost2[] substCosts, Cost1[] insertCosts, Cost1[] deleteCosts, Cost2[] transpositionCosts) {
		this.key = key;
		this.matchCosts = matchCosts;
		this.substCosts = substCosts;
		this.insertCosts = insertCosts;
		this.deleteCosts = deleteCosts;
		this.transpositionCosts = transpositionCosts;
		
		d2 = new int[key.length + 1];
		int[] op;
		
		history.add(op = new int[key.length + 1]);

		d2[0] = 0;
		op[0] = 0;
		
		for(int i=1; i<=key.length; i++) {
			d2[i] = d2[i-1] + getCost1(insertCosts, key[i-1]);
			op[i] = INSERTION.mask;
		}

		j = 1;
	}

	public int append(char c2) {
		return append(matchCosts, substCosts, insertCosts, deleteCosts, transpositionCosts, c2);
	}
			
	public int append(int matchcost, int substcost, int insertcost, int delcost, int transpcost, char c2) {
		return append(constantCost1(matchcost), constantCost2(substcost), constantCost1(insertcost), constantCost1(delcost), constantCost2(transpcost), c2);
	}

	public int append(Cost1[] matchCosts, Cost2[] substCosts, Cost1[] insertCosts, Cost1[] deleteCosts, Cost2[] transpositionCosts, char c2) {
		d0 = d1;
		d1 = d2;
		d2 = new int[key.length + 1];
		int[] op;
		
		history.add(op = new int[key.length + 1]);
		
		d2[0] = d1[0] + getCost1(deleteCosts, c2);
		op[0] = DELETION.mask;
		
		for(int i=1; i<=key.length; i++) {
			int ifmatch = (key[i-1] == c2 ? d1[i-1] + getCost1(matchCosts, key[i-1]) : Integer.MAX_VALUE);
			int ifinsert = d2[i-1] + getCost1(insertCosts, c2);
			int ifdelete = d1[i] + getCost1(deleteCosts, key[i-1]);
			int ifsubst = d1[i-1] + getCost2(substCosts, key[i-1], c2);
			int iftransp = i > 1 && j > 1 && key[i-1] == c1 && key[i-2] == c2 ? d0[i-2] + getCost2(transpositionCosts, key[i-1], c2) : Integer.MAX_VALUE;

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
	
	private static int getCost1(Cost1[] costs, char c) {
		for (Cost1 cost : costs) {
			if ((cost.charTypeMask & (1 << Character.getType(c))) != 0)
				return cost.cost;
		}
		
		throw new IllegalStateException();
	}
	
	private static int getCost2(Cost2[] costs, char c1, char c2) {
		for (Cost2 cost : costs) {
			if (((cost.char1TypeMask & (1 << Character.getType(c1))) != 0) && ((cost.char2TypeMask & (1 << Character.getType(c2))) != 0))
				return cost.cost;
		}
		
		throw new IllegalStateException();
	}
	
	public static Cost1[] constantCost1(int cost) {
		return new Cost1[] { new Cost1(-1, cost) };
	}
	
	public static Cost2[] constantCost2(int cost) {
		return new Cost2[] { new Cost2(-1, -1, cost) };
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
		return minSection(0, values.length, values);
	}

	private static int minSection(int start, int end, int... values) {
		int result = Integer.MAX_VALUE;
		
		for (int i = start; i < end; i++) {
			result = Math.min(result, values[i]);
		}
		
		return result;
	}

	public int getDistance() {
		return d2[key.length];
	}

	public int getPastDistance() {
		return d1[key.length];
	}

	public int getMinimalDistance() {
		return min(d2);
	}

	public int getMinimalFutureDistance() {
		return minSection(0, d2.length - 1, d2);
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