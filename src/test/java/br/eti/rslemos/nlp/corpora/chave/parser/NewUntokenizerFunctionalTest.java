package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class NewUntokenizerFunctionalTest extends UntokenizerAbstractFunctionalTest implements WorkingData {

	private NewUntokenizer untokenizer;

	@Before
	public void setUp() {
		char[] separator = new char[80];
		Arrays.fill(separator, '=');
		System.out.println(separator);
		
		untokenizer = new NewUntokenizer();
	}
	
	@Override
	protected void untokenize(Document document, List<CGEntry> cgLines) throws IOException, ParserException {
		untokenizer.untokenize(document, cgLines);
		//System.out.println(document.toXml());
	}

}
