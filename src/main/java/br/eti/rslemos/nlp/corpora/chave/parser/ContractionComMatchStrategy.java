package br.eti.rslemos.nlp.corpora.chave.parser;


public class ContractionComMatchStrategy extends AbstractContractionMatchStrategy {

	public ContractionComMatchStrategy() {
		super("com", 
				new String[] {   "mim" ,   "nos"  ,    "si"  ,    "ti"  ,    "vos"  },
				new String[] { "comigo", "conosco", "consigo", "contigo", "convosco"},
				new int[] { 3, 3, 3, 3, 3 },
				new int[] { 2, 2, 3, 3, 3 }
		);
	}

}
