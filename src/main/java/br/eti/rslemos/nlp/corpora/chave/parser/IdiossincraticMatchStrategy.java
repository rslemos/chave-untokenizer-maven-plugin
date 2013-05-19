package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IdiossincraticMatchStrategy extends DirectMatchStrategy {

	private List<Pair> matches = new ArrayList<Pair>();

	@Override
	protected String getKey(int i) {
		String key = super.getKey(i);
		
		for (Pair pair : matches) {
			if (pair.entry.equals(key))
				return pair.text;
		}
		
		throw new CutException();
	}

	
	@Override
	protected void addAllMatches(int from, int to, int i, Set<Match> matches) {
		try {
			super.addAllMatches(from, to, i, matches);
		} catch (CutException e) { /* swallow */ }
	}


	public void addMatch(String text, String entry) {
		matches.add(pair(text, entry));
	}

	private Pair pair(String text, String entry) {
		return new Pair(text, entry);
	}

}

class Pair {

	public final String text;
	public final String entry;

	public Pair(String text, String entry) {
		this.text = text;
		this.entry = entry;
	}
	
}

@SuppressWarnings("serial")
class CutException extends RuntimeException {
	
}