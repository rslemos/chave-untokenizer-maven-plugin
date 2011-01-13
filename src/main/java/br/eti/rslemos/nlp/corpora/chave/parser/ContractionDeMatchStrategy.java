package br.eti.rslemos.nlp.corpora.chave.parser;


public class ContractionDeMatchStrategy extends AbstractContractionMatchStrategy {
	public ContractionDeMatchStrategy() {
		super("de", 
				new String[] {  "a",  "aquela",  "aquelas",  "aquele",  "aqueles",  "aquilo",  "as",  "aí",  "ela",  "elas",  "ele",  "eles",  "essa",  "essas",  "esse",  "esses",  "esta",  "estas",  "este",  "estes",  "isso",  "isto",  "o",  "os", }, 
				new String[] { "da", "daquela", "daquelas", "daquele", "daqueles", "daquilo", "das", "daí", "dela", "delas", "dele", "deles", "dessa", "dessas", "desse", "desses", "desta", "destas", "deste", "destes", "disso", "disto", "do", "dos", },
			1);
	}
}

 
