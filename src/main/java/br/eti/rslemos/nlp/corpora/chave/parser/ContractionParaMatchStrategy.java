package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionParaMatchStrategy extends AbstractContractionMatchStrategy {

	private static final ContractionTemplate[] TEMPLATES = {
			new ContractionTemplate("para", "a", "pra", 3, 2),
			new ContractionTemplate("para", "aquela", "praquela", 3, 2),
			new ContractionTemplate("para", "aquelas", "praquelas", 3, 2),
			new ContractionTemplate("para", "aquele", "praquele", 3, 2),
			new ContractionTemplate("para", "aqueles", "praqueles", 3, 2),
			new ContractionTemplate("para", "aquilo", "praquilo", 3, 2),
			new ContractionTemplate("para", "as", "pras", 3, 2),
			new ContractionTemplate("para", "o", "pro", 2, 2),
			new ContractionTemplate("para", "os", "pros", 2, 2),
		};
	
	public ContractionParaMatchStrategy() {
		super(TEMPLATES);
	}

}

 
