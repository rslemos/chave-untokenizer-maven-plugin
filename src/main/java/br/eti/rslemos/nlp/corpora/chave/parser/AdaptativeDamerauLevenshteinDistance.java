package br.eti.rslemos.nlp.corpora.chave.parser;

public class AdaptativeDamerauLevenshteinDistance {
	private final char[] key;
	
	private int d0[] = null;
	private int d1[] = null;
	private int d2[] = null;
	
	private int j = 1;
	private char c0;
	
	public AdaptativeDamerauLevenshteinDistance(char[] key) {
		this.key = key;
		d2 = new int[key.length + 1];
		
		for(int i=0; i<=key.length; i++) {
			d2[i] = i;
		}
	}
	
	public int append(char c1) {
		d0 = d1;
		d1 = d2;
		d2 = new int[key.length + 1];
		
		d2[0] = j;
		
		for(int i=1; i<=key.length; i++) {
			int cost;
			
			if (key[i-1] == c1)
				cost = 0;
			else
				cost = 1;
			
			d2[i] = Math.min(Math.min(d2[i-1] + 1, d1[i] + 1), d1[i-1] + cost);

			if (i > 1 && j > 1 && key[i-1] == c0 && key[i-2] == c1)
				d2[i] = Math.min(d2[i], d0[i-2] + cost);
		}
		
		c0 = c1;
		j++;
		
		return getDistance();
	}

	public int getDistance() {
		return d2[key.length];
	}
}