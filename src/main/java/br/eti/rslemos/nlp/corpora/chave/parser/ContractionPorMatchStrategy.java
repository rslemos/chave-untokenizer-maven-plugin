package br.eti.rslemos.nlp.corpora.chave.parser;


public class ContractionPorMatchStrategy extends AbstractContractionMatchStrategy {

	private static final ContractionTemplate[] TEMPLATES = {
			// POR + X
			new ContractionTemplate("POR", "A", "PELA", 3, 3),
			new ContractionTemplate("POR", "AS", "PELAS", 3, 3),
			new ContractionTemplate("POR", "O", "PELO", 3, 3),
			new ContractionTemplate("POR", "OS", "PELOS", 3, 3),
			// Por + x
			new ContractionTemplate("Por", "a", "Pela", 3, 3),
			new ContractionTemplate("Por", "as", "Pelas", 3, 3),
			new ContractionTemplate("Por", "o", "Pelo", 3, 3),
			new ContractionTemplate("Por", "os", "Pelos", 3, 3),
			// por + x
			new ContractionTemplate("por", "a", "pela", 3, 3),
			new ContractionTemplate("por", "as", "pelas", 3, 3),
			new ContractionTemplate("por", "o", "pelo", 3, 3),
			new ContractionTemplate("por", "os", "pelos", 3, 3),
		};
	
	public ContractionPorMatchStrategy() {
		super(TEMPLATES);
	}
	
}