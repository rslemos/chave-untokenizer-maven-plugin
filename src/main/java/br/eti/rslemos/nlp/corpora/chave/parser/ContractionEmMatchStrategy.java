package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionEmMatchStrategy extends AbstractContractionMatchStrategy {

	public ContractionEmMatchStrategy() {
		super("em", 
				new String[] {  "a",  "aquela",  "aquelas",  "aquele",  "aqueles",  "aquilo",  "as",  "ela",  "elas",  "ele",  "eles",  "essa",  "essas",  "esse",  "esses",  "esta",  "estas",  "este",  "estes",  "isso",  "isto",  "o",  "os", }, 
				new String[] { "na", "naquela", "naquelas", "naquele", "naqueles", "naquilo", "nas", "nela", "nelas", "nele", "neles", "nessa", "nessas", "nesse", "nesses", "nesta", "nestas", "neste", "nestes", "nisso", "nisto", "no", "nos", },
			1);
	}

}
