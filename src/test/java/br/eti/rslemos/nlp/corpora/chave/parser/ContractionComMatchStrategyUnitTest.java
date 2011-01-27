package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class ContractionComMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionComMatchStrategyUnitTest() {
		super(new ContractionComMatchStrategy());
	}
	
	@Test
	public void testContraction_com_mim() throws Exception {
		cg.add("com");
		cg.add("mim");
		
		runOver("comigo");
		
		verifyMatches(match(0, "comigo".length(), span(0, "com".length(), 0),  
				span("co".length(), "comigo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_com_ti() throws Exception {
		cg.add("com");
		cg.add("ti");
		
		runOver("contigo");
		
		verifyMatches(match(0, "contigo".length(), span(0, "con".length(), 0), 
				span("con".length(), "contigo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_com_si() throws Exception {
		cg.add("com");
		cg.add("si");
		
		runOver("consigo");
		
		verifyMatches(match(0, "consigo".length(), span(0, "con".length(), 0), 
				span("con".length(), "consigo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_com_nos() throws Exception {
		cg.add("com");
		cg.add("nos");
		
		runOver("conosco");
		
		verifyMatches(match(0, "conosco".length(), span(0, "con".length(), 0),  
				span("co".length(), "conosco".length(), 1)
			));
	}
	
	@Test
	public void testContraction_com_vos() throws Exception {
		cg.add("com");
		cg.add("vos");
		
		runOver("convosco");
		
		verifyMatches(match(0, "convosco".length(), span(0, "con".length(), 0), 
				span("con".length(), "convosco".length(), 1)
			));
	}
}
