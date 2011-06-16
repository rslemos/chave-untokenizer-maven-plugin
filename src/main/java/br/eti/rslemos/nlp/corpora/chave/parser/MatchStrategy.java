package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;
import java.util.Set;

public interface MatchStrategy {
	void setData(TextMatcher matcher, List<String> cg);
	
	/**
	 * Try to match the <code>i</code>-th key (and a few following it) over the
	 * text possibly starting at <code>k</code>.
	 * 
	 * @param k
	 * @param i
	 * 
	 * @return the nearest match starting at <code>k</code> if one such match
	 * exists; <code>null</code> otherwise.
	 */
	//Match match(int k, int i);
	
	@Deprecated
	Set<Match> matchAll();
}