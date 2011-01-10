package br.eti.rslemos.nlp.corpora.chave.parser;




public class ContractionEmMatchStrategy extends AbstractContractionMatchStrategy {

	public ContractionEmMatchStrategy() {
		super("em", new String[] {"a", "as", "o", "os"}, new String[] {"na", "nas", "no", "nos"}, 1);
	}

}
