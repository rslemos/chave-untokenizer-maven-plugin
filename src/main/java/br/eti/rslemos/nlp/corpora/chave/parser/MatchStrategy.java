package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.List;
import java.util.Set;

public interface MatchStrategy {
	void setText(String text);
	
	void setCG(List<String> cg);

	Set<Match> match();
}