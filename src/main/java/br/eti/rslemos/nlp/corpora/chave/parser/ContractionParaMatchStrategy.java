package br.eti.rslemos.nlp.corpora.chave.parser;


public class ContractionParaMatchStrategy extends AbstractContractionMatchStrategy {
	public ContractionParaMatchStrategy() {
		super("para", 
				new String[] {   "a",   "aquela",   "aquelas",   "aquele",   "aqueles",   "aquilo",   "as",   "o",   "os", }, 
				new String[] { "pra", "praquela", "praquelas", "praquele", "praqueles", "praquilo", "pras", "pro", "pros", },
				new int[] { 3, 3, 3, 3, 3, 3, 3, 2, 2 },
				new int[] { 2, 2, 2, 2, 2, 2, 2, 2, 2 } 
			);
	}
}

 
