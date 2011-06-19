package br.eti.rslemos.nlp.corpora.chave.parser;

public class ContractionEmMatchStrategy extends AbstractContractionMatchStrategy {

	private static ContractionTemplate[] EM_CONTRACTIONS = {
			// EM + X
			new ContractionTemplate("EM", "A", "NA", 1, 1),
			new ContractionTemplate("EM", "AQUELA", "NAQUELA", 1, 1),
			new ContractionTemplate("EM", "AQUELAS", "NAQUELAS", 1, 1),
			new ContractionTemplate("EM", "AQUELE", "NAQUELE", 1, 1),
			new ContractionTemplate("EM", "AQUELES", "NAQUELES", 1, 1),
			new ContractionTemplate("EM", "AQUILO", "NAQUILO", 1, 1),
			new ContractionTemplate("EM", "AS", "NAS", 1, 1),
			new ContractionTemplate("EM", "ELA", "NELA", 1, 1),
			new ContractionTemplate("EM", "ELAS", "NELAS", 1, 1),
			new ContractionTemplate("EM", "ELE", "NELE", 1, 1),
			new ContractionTemplate("EM", "ELES", "NELES", 1, 1),
			new ContractionTemplate("EM", "ESSA", "NESSA", 1, 1),
			new ContractionTemplate("EM", "ESSAS", "NESSAS", 1, 1),
			new ContractionTemplate("EM", "ESSE", "NESSE", 1, 1),
			new ContractionTemplate("EM", "ESSES", "NESSES", 1, 1),
			new ContractionTemplate("EM", "ESTA", "NESTA", 1, 1),
			new ContractionTemplate("EM", "ESTAS", "NESTAS", 1, 1),
			new ContractionTemplate("EM", "ESTE", "NESTE", 1, 1),
			new ContractionTemplate("EM", "ESTES", "NESTES", 1, 1),
			new ContractionTemplate("EM", "ISSO", "NISSO", 1, 1),
			new ContractionTemplate("EM", "ISTO", "NISTO", 1, 1),
			new ContractionTemplate("EM", "O", "NO", 1, 1),
			new ContractionTemplate("EM", "OS", "NOS", 1, 1),
			new ContractionTemplate("EM", "UM", "NUM", 1, 1),
			new ContractionTemplate("EM", "UMA", "NUMA", 1, 1),
			new ContractionTemplate("EM", "UMAS", "NUMAS", 1, 1),
			new ContractionTemplate("EM", "UNS", "NUNS", 1, 1),
			// Em + x
			new ContractionTemplate("Em", "a", "Na", 1, 1),
			new ContractionTemplate("Em", "aquela", "Naquela", 1, 1),
			new ContractionTemplate("Em", "aquelas", "Naquelas", 1, 1),
			new ContractionTemplate("Em", "aquele", "Naquele", 1, 1),
			new ContractionTemplate("Em", "aqueles", "Naqueles", 1, 1),
			new ContractionTemplate("Em", "aquilo", "Naquilo", 1, 1),
			new ContractionTemplate("Em", "as", "Nas", 1, 1),
			new ContractionTemplate("Em", "ela", "Nela", 1, 1),
			new ContractionTemplate("Em", "elas", "Nelas", 1, 1),
			new ContractionTemplate("Em", "ele", "Nele", 1, 1),
			new ContractionTemplate("Em", "eles", "Neles", 1, 1),
			new ContractionTemplate("Em", "essa", "Nessa", 1, 1),
			new ContractionTemplate("Em", "essas", "Nessas", 1, 1),
			new ContractionTemplate("Em", "esse", "Nesse", 1, 1),
			new ContractionTemplate("Em", "esses", "Nesses", 1, 1),
			new ContractionTemplate("Em", "esta", "Nesta", 1, 1),
			new ContractionTemplate("Em", "estas", "Nestas", 1, 1),
			new ContractionTemplate("Em", "este", "Neste", 1, 1),
			new ContractionTemplate("Em", "estes", "Nestes", 1, 1),
			new ContractionTemplate("Em", "isso", "Nisso", 1, 1),
			new ContractionTemplate("Em", "isto", "Nisto", 1, 1),
			new ContractionTemplate("Em", "o", "No", 1, 1),
			new ContractionTemplate("Em", "os", "Nos", 1, 1),
			new ContractionTemplate("Em", "um", "Num", 1, 1),
			new ContractionTemplate("Em", "uma", "Numa", 1, 1),
			new ContractionTemplate("Em", "umas", "Numas", 1, 1),
			new ContractionTemplate("Em", "uns", "Nuns", 1, 1),
			// em + x
			new ContractionTemplate("em", "a", "na", 1, 1),
			new ContractionTemplate("em", "aquela", "naquela", 1, 1),
			new ContractionTemplate("em", "aquelas", "naquelas", 1, 1),
			new ContractionTemplate("em", "aquele", "naquele", 1, 1),
			new ContractionTemplate("em", "aqueles", "naqueles", 1, 1),
			new ContractionTemplate("em", "aquilo", "naquilo", 1, 1),
			new ContractionTemplate("em", "as", "nas", 1, 1),
			new ContractionTemplate("em", "ela", "nela", 1, 1),
			new ContractionTemplate("em", "elas", "nelas", 1, 1),
			new ContractionTemplate("em", "ele", "nele", 1, 1),
			new ContractionTemplate("em", "eles", "neles", 1, 1),
			new ContractionTemplate("em", "essa", "nessa", 1, 1),
			new ContractionTemplate("em", "essas", "nessas", 1, 1),
			new ContractionTemplate("em", "esse", "nesse", 1, 1),
			new ContractionTemplate("em", "esses", "nesses", 1, 1),
			new ContractionTemplate("em", "esta", "nesta", 1, 1),
			new ContractionTemplate("em", "estas", "nestas", 1, 1),
			new ContractionTemplate("em", "este", "neste", 1, 1),
			new ContractionTemplate("em", "estes", "nestes", 1, 1),
			new ContractionTemplate("em", "isso", "nisso", 1, 1),
			new ContractionTemplate("em", "isto", "nisto", 1, 1),
			new ContractionTemplate("em", "o", "no", 1, 1),
			new ContractionTemplate("em", "os", "nos", 1, 1),
			new ContractionTemplate("em", "um", "num", 1, 1),
			new ContractionTemplate("em", "uma", "numa", 1, 1),
			new ContractionTemplate("em", "umas", "numas", 1, 1),
			new ContractionTemplate("em", "uns", "nuns", 1, 1),		
		};
	
	public ContractionEmMatchStrategy() {
		super(EM_CONTRACTIONS);
	}

}
