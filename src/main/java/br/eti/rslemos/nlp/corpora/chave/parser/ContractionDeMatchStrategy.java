package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionDeMatchStrategy extends AbstractContractionMatchStrategy {

	private static final ContractionTemplate[] TEMPLATES = {
			// DE + X
			new ContractionTemplate("DE", "A", "DA", 1, 1),
			new ContractionTemplate("DE", "AQUELA", "DAQUELA", 1, 1),
			new ContractionTemplate("DE", "AQUELAS", "DAQUELAS", 1, 1),
			new ContractionTemplate("DE", "AQUELE", "DAQUELE", 1, 1),
			new ContractionTemplate("DE", "AQUELES", "DAQUELES", 1, 1),
			new ContractionTemplate("DE", "AQUILO", "DAQUILO", 1, 1),
			new ContractionTemplate("DE", "AS", "DAS", 1, 1),
			new ContractionTemplate("DE", "AÍ", "DAÍ", 1, 1),
			new ContractionTemplate("DE", "ELA", "DELA", 1, 1),
			new ContractionTemplate("DE", "ELAS", "DELAS", 1, 1),
			new ContractionTemplate("DE", "ELE", "DELE", 1, 1),
			new ContractionTemplate("DE", "ELES", "DELES", 1, 1),
			new ContractionTemplate("DE", "ESSA", "DESSA", 1, 1),
			new ContractionTemplate("DE", "ESSAS", "DESSAS", 1, 1),
			new ContractionTemplate("DE", "ESSE", "DESSE", 1, 1),
			new ContractionTemplate("DE", "ESSES", "DESSES", 1, 1),
			new ContractionTemplate("DE", "ESTA", "DESTA", 1, 1),
			new ContractionTemplate("DE", "ESTAS", "DESTAS", 1, 1),
			new ContractionTemplate("DE", "ESTE", "DESTE", 1, 1),
			new ContractionTemplate("DE", "ESTES", "DESTES", 1, 1),
			new ContractionTemplate("DE", "ISSO", "DISSO", 1, 1),
			new ContractionTemplate("DE", "ISTO", "DISTO", 1, 1),
			new ContractionTemplate("DE", "O", "DO", 1, 1),
			new ContractionTemplate("DE", "OS", "DOS", 1, 1),
			// De + x
			new ContractionTemplate("De", "a", "Da", 1, 1),
			new ContractionTemplate("De", "aquela", "Daquela", 1, 1),
			new ContractionTemplate("De", "aquelas", "Daquelas", 1, 1),
			new ContractionTemplate("De", "aquele", "Daquele", 1, 1),
			new ContractionTemplate("De", "aqueles", "Daqueles", 1, 1),
			new ContractionTemplate("De", "aquilo", "Daquilo", 1, 1),
			new ContractionTemplate("De", "as", "Das", 1, 1),
			new ContractionTemplate("De", "aí", "Daí", 1, 1),
			new ContractionTemplate("De", "ela", "Dela", 1, 1),
			new ContractionTemplate("De", "elas", "Delas", 1, 1),
			new ContractionTemplate("De", "ele", "Dele", 1, 1),
			new ContractionTemplate("De", "eles", "Deles", 1, 1),
			new ContractionTemplate("De", "essa", "Dessa", 1, 1),
			new ContractionTemplate("De", "essas", "Dessas", 1, 1),
			new ContractionTemplate("De", "esse", "Desse", 1, 1),
			new ContractionTemplate("De", "esses", "Desses", 1, 1),
			new ContractionTemplate("De", "esta", "Desta", 1, 1),
			new ContractionTemplate("De", "estas", "Destas", 1, 1),
			new ContractionTemplate("De", "este", "Deste", 1, 1),
			new ContractionTemplate("De", "estes", "Destes", 1, 1),
			new ContractionTemplate("De", "isso", "Disso", 1, 1),
			new ContractionTemplate("De", "isto", "Disto", 1, 1),
			new ContractionTemplate("De", "o", "Do", 1, 1),
			new ContractionTemplate("De", "os", "Dos", 1, 1),
			// de + x
			new ContractionTemplate("de", "a", "da", 1, 1),
			new ContractionTemplate("de", "aquela", "daquela", 1, 1),
			new ContractionTemplate("de", "aquelas", "daquelas", 1, 1),
			new ContractionTemplate("de", "aquele", "daquele", 1, 1),
			new ContractionTemplate("de", "aqueles", "daqueles", 1, 1),
			new ContractionTemplate("de", "aquilo", "daquilo", 1, 1),
			new ContractionTemplate("de", "as", "das", 1, 1),
			new ContractionTemplate("de", "aí", "daí", 1, 1),
			new ContractionTemplate("de", "ela", "dela", 1, 1),
			new ContractionTemplate("de", "elas", "delas", 1, 1),
			new ContractionTemplate("de", "ele", "dele", 1, 1),
			new ContractionTemplate("de", "eles", "deles", 1, 1),
			new ContractionTemplate("de", "essa", "dessa", 1, 1),
			new ContractionTemplate("de", "essas", "dessas", 1, 1),
			new ContractionTemplate("de", "esse", "desse", 1, 1),
			new ContractionTemplate("de", "esses", "desses", 1, 1),
			new ContractionTemplate("de", "esta", "desta", 1, 1),
			new ContractionTemplate("de", "estas", "destas", 1, 1),
			new ContractionTemplate("de", "este", "deste", 1, 1),
			new ContractionTemplate("de", "estes", "destes", 1, 1),
			new ContractionTemplate("de", "isso", "disso", 1, 1),
			new ContractionTemplate("de", "isto", "disto", 1, 1),
			new ContractionTemplate("de", "o", "do", 1, 1),
			new ContractionTemplate("de", "os", "dos", 1, 1),
		};
	
	public ContractionDeMatchStrategy() {
		super(TEMPLATES);
	}
	
}

 
