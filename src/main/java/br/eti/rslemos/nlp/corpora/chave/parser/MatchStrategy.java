package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;
import java.util.Set;

public interface MatchStrategy {
	/**
	 * Try to match the (few) next item(s) on <code>cg</code>. An
	 * implementation is expected neither to skip over entries in
	 * <code>cg</code> nor characters over <code>text</code>.
	 * 
	 * @param text
	 * @param cg
	 */
	Set<Match> match(String text, List<String> cg);
}