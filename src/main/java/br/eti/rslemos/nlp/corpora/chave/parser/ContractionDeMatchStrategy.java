package br.eti.rslemos.nlp.corpora.chave.parser;


public class ContractionDeMatchStrategy extends AbstractContractionMatchStrategy {
	public ContractionDeMatchStrategy() {
		super("de", new String[] {"a", "as", "o", "os"}, new String[] {"da", "das", "do", "dos"}, 1);
	}
}
