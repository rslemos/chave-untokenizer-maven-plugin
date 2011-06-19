package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;
import java.util.Set;

public interface MatchStrategy {
	void setData(TextMatcher matcher, List<String> cg);
	
	Set<Match> matchAll();

	Set<Match> matchAll(int start, int end);
}