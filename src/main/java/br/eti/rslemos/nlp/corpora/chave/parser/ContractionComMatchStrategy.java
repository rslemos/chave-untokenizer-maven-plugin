package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionComMatchStrategy extends AbstractContractionMatchStrategy {
	
	private static final ContractionTemplate[] TEMPLATES = {
			// COM + X
			new ContractionTemplate("COM", "MIM", "COMIGO", 3, 2),
			new ContractionTemplate("COM", "NOS", "CONOSCO", 3, 2),
			new ContractionTemplate("COM", "SI", "CONSIGO", 3, 3),
			new ContractionTemplate("COM", "TI", "CONTIGO", 3, 3),
			new ContractionTemplate("COM", "VOS", "CONVOSCO", 3, 3),
			// Com + x
			new ContractionTemplate("Com", "mim", "Comigo", 3, 2),
			new ContractionTemplate("Com", "nos", "Conosco", 3, 2),
			new ContractionTemplate("Com", "si", "Consigo", 3, 3),
			new ContractionTemplate("Com", "ti", "Contigo", 3, 3),
			new ContractionTemplate("Com", "vos", "Convosco", 3, 3),
			// com + x
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
