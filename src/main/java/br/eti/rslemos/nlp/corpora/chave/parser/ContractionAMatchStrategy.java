package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionAMatchStrategy extends AbstractContractionMatchStrategy {

	private static final ContractionTemplate[] TEMPLATES = {
			// A + X
			new ContractionTemplate("A", "A", "À", 1, 0),
			new ContractionTemplate("A", "AQUELA", "ÀQUELA", 1, 0),
			new ContractionTemplate("A", "AQUELAS", "ÀQUELAS", 1, 0),
			new ContractionTemplate("A", "AQUELE", "ÀQUELE", 1, 0),
			new ContractionTemplate("A", "AQUELES", "ÀQUELES", 1, 0),
			new ContractionTemplate("A", "AQUILO", "ÀQUILO", 1, 0),
			new ContractionTemplate("A", "AS", "ÀS", 1, 0),
			new ContractionTemplate("A", "O", "AO", 1, 1),
			new ContractionTemplate("A", "OS", "AOS", 1, 1),
			// A + x
			new ContractionTemplate("A", "a", "À", 1, 0),
			new ContractionTemplate("A", "aquela", "Àquela", 1, 0),
			new ContractionTemplate("A", "aquelas", "Àquelas", 1, 0),
			new ContractionTemplate("A", "aquele", "Àquele", 1, 0),
			new ContractionTemplate("A", "aqueles", "Àqueles", 1, 0),
			new ContractionTemplate("A", "aquilo", "Àquilo", 1, 0),
			new ContractionTemplate("A", "as", "Às", 1, 0),
			new ContractionTemplate("A", "o", "Ao", 1, 1),
			new ContractionTemplate("A", "os", "Aos", 1, 1),
			// a + x
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
