package br.eti.rslemos.nlp.corpora.chave.parser;


public class ContractionAMatchStrategy extends AbstractContractionMatchStrategy {

	public ContractionAMatchStrategy() {
		super("a", 
				new String[] { "a", "aquela", "aquelas", "aquele", "aqueles", "aquilo", "as",  "o",  "os" },
				new String[] { "à", "àquela", "àquelas", "àquele", "àqueles", "àquilo", "às", "ao", "aos" },
				new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 1 } 
			);
	}

}
