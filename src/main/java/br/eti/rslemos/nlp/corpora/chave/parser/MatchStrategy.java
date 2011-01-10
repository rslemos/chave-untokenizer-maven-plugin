package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;
import java.util.List;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public interface MatchStrategy {
	/**
	 * Try to match the (few) next item(s) on <code>cg</code>.
	 * 
	 * On 
	 * @param buffer
	 * @param cg
	 * 
	 * @return <code>MatchingResult</code> that captures the actions that
	 * should be carried later to consume the matching; or null if impossible
	 * to match after all.
	 * 
	 * @throws BufferUnderflowException if more data is needed.
	 */
	MatchResult match(CharBuffer buffer, List<Entry<String, String>> cg) throws BufferUnderflowException;
}