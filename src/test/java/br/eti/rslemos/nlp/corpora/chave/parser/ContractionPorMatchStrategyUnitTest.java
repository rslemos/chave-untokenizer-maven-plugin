package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class ContractionPorMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionPorMatchStrategyUnitTest() {
		super(new ContractionPorMatchStrategy());
	}
	
	@Test
	public void testContraction_de_o() throws Exception {
		cg.add("por");
		cg.add("o");
		
		Match result = runOver("pelo");
		
		verifyMatch(result, 0, "pelo".length(), 
				span(0, "pel".length(), 0),
				span("pel".length(), "pelo".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		cg.add("por");
		cg.add("a");
		
		Match result = runOver("pela");
		
		verifyMatch(result, 0, "pela".length(), 
				span(0, "pel".length(), 0),
				span("pel".length(), "pela".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		cg.add("por");
		cg.add("os");
		
		Match result = runOver("pelos");
		
		verifyMatch(result, 0, "pelos".length(), 
				span(0, "pel".length(), 0),
				span("pel".length(), "pelos".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		cg.add("por");
		cg.add("as");
		
		Match result = runOver("pelas");
		
		verifyMatch(result, 0, "pelas".length(), 
				span(0, "pel".length(), 0),
				span("pel".length(), "pelas".length(), 1)
			);
	}
}