package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionComMatchStrategy extends AbstractContractionMatchStrategy {
	
	private static final ContractionTemplate[] TEMPLATES = {
			new ContractionTemplate("com", "mim", "comigo", 3, 2),
			new ContractionTemplate("com", "nos", "conosco", 3, 2),
			new ContractionTemplate("com", "si", "consigo", 3, 3),
			new ContractionTemplate("com", "ti", "contigo", 3, 3),
			new ContractionTemplate("com", "vos", "convosco", 3, 3),
		};

	public ContractionComMatchStrategy() {
		super(TEMPLATES);
	}

}
