package br.eti.rslemos.nlp.corpora.chave.parser;


public class ContractionPorMatchStrategy extends AbstractContractionMatchStrategy {

	private static final ContractionTemplate[] TEMPLATES = {
			new ContractionTemplate("por", "a", "pela", 3, 3),
			new ContractionTemplate("por", "as", "pelas", 3, 3),
			new ContractionTemplate("por", "o", "pelo", 3, 3),
			new ContractionTemplate("por", "os", "pelos", 3, 3),
		};
	
	public ContractionPorMatchStrategy() {
		super(TEMPLATES);
	}
	
}