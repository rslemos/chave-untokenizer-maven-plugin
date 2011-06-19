package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionParaMatchStrategy extends AbstractContractionMatchStrategy {

	private static final ContractionTemplate[] TEMPLATES = {
			// PARA + X
			new ContractionTemplate("PARA", "A", "PRA", 3, 2),
			new ContractionTemplate("PARA", "AQUELA", "PRAQUELA", 3, 2),
			new ContractionTemplate("PARA", "AQUELAS", "PRAQUELAS", 3, 2),
			new ContractionTemplate("PARA", "AQUELE", "PRAQUELE", 3, 2),
			new ContractionTemplate("PARA", "AQUELES", "PRAQUELES", 3, 2),
			new ContractionTemplate("PARA", "AQUILO", "PRAQUILO", 3, 2),
			new ContractionTemplate("PARA", "AS", "PRAS", 3, 2),
			new ContractionTemplate("PARA", "O", "PRO", 2, 2),
			new ContractionTemplate("PARA", "OS", "PROS", 2, 2),
			// Para + x
			new ContractionTemplate("Para", "a", "Pra", 3, 2),
			new ContractionTemplate("Para", "aquela", "Praquela", 3, 2),
			new ContractionTemplate("Para", "aquelas", "Praquelas", 3, 2),
			new ContractionTemplate("Para", "aquele", "Praquele", 3, 2),
			new ContractionTemplate("Para", "aqueles", "Praqueles", 3, 2),
			new ContractionTemplate("Para", "aquilo", "Praquilo", 3, 2),
			new ContractionTemplate("Para", "as", "Pras", 3, 2),
			new ContractionTemplate("Para", "o", "Pro", 2, 2),
			new ContractionTemplate("Para", "os", "Pros", 2, 2),
			// para + x
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

 
