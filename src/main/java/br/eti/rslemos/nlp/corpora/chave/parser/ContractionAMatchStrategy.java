package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionAMatchStrategy extends AbstractContractionMatchStrategy {

	private static final ContractionTemplate[] TEMPLATES = {
			new ContractionTemplate("a", "a", "à", 1, 0),
			new ContractionTemplate("a", "aquela", "àquela", 1, 0),
			new ContractionTemplate("a", "aquelas", "àquelas", 1, 0),
			new ContractionTemplate("a", "aquele", "àquele", 1, 0),
			new ContractionTemplate("a", "aqueles", "àqueles", 1, 0),
			new ContractionTemplate("a", "aquilo", "àquilo", 1, 0),
			new ContractionTemplate("a", "as", "às", 1, 0),
			new ContractionTemplate("a", "o", "ao", 1, 1),
			new ContractionTemplate("a", "os", "aos", 1, 1),
		};

	public ContractionAMatchStrategy() {
		super(TEMPLATES);
	}

}
