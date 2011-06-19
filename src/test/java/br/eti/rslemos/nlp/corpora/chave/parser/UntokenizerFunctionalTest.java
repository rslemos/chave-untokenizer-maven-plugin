package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.Document;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
@Ignore
public class UntokenizerFunctionalTest extends UntokenizerAbstractFunctionalTest implements WorkingData {

	private Untokenizer untokenizer;

	@Before
	public void setUp() {
		char[] separator = new char[80];
		Arrays.fill(separator, '=');
		System.out.println(separator);
		
		untokenizer = new Untokenizer();
	}
	
	@Override
	protected void untokenize(Document document, List<CGEntry> cgLines) {
		untokenizer.untokenize(document, cgLines);
		//System.out.println(document.toXml());
	}

}
