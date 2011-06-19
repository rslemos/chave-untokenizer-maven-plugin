package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionDeMatchStrategy extends AbstractContractionMatchStrategy {

	private static final ContractionTemplate[] TEMPLATES = {
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

 
