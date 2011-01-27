package br.eti.rslemos.nlp.corpora.chave.parser;

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
		
		Match result = match("comigo");
		
		verifyMatch(result, 0, "comigo".length(),  
			span(0, "com".length(), 0),
			span("co".length(), "comigo".length(), 1)
		);
	}
	
	@Test
	public void testContraction_com_ti() throws Exception {
		cg.add("com");
		cg.add("ti");
		
		Match result = match("contigo");
		
		verifyMatch(result, 0, "contigo".length(), 
				span(0, "con".length(), 0),
				span("con".length(), "contigo".length(), 1)
			);
	}
	
	@Test
	public void testContraction_com_si() throws Exception {
		cg.add("com");
		cg.add("si");
		
		Match result = match("consigo");
		
		verifyMatch(result, 0, "consigo".length(), 
				span(0, "con".length(), 0),
				span("con".length(), "consigo".length(), 1)
			);
	}
	
	@Test
	public void testContraction_com_nos() throws Exception {
		cg.add("com");
		cg.add("nos");
		
		Match result = match("conosco");
		
		verifyMatch(result, 0, "conosco".length(),  
			span(0, "con".length(), 0),
			span("co".length(), "conosco".length(), 1)
		);
	}
	
	@Test
	public void testContraction_com_vos() throws Exception {
		cg.add("com");
		cg.add("vos");
		
		Match result = match("convosco");
		
		verifyMatch(result, 0, "convosco".length(), 
				span(0, "con".length(), 0),
				span("con".length(), "convosco".length(), 1)
			);
	}
}
