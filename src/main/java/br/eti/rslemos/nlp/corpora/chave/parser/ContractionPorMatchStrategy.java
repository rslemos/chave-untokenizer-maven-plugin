package br.eti.rslemos.nlp.corpora.chave.parser;


public class ContractionPorMatchStrategy extends AbstractContractionMatchStrategy {

	public ContractionPorMatchStrategy() {
		super("por", 
				new String[] {    "a",    "as",    "o",    "os" },
				new String[] { "pela", "pelas", "pelo", "pelos" },
			3);
	}
}