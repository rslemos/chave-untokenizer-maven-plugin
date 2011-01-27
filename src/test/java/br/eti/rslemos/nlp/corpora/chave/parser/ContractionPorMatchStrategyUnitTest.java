package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
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
		
		runOver("pelo");
		
		verifyMatches(match(0, "pelo".length(), span(0, "pel".length(), 0), 
				span("pel".length(), "pelo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		cg.add("por");
		cg.add("a");
		
		runOver("pela");
		
		verifyMatches(match(0, "pela".length(), span(0, "pel".length(), 0), 
				span("pel".length(), "pela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		cg.add("por");
		cg.add("os");
		
		runOver("pelos");
		
		verifyMatches(match(0, "pelos".length(), span(0, "pel".length(), 0), 
				span("pel".length(), "pelos".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		cg.add("por");
		cg.add("as");
		
		runOver("pelas");
		
		verifyMatches(match(0, "pelas".length(), span(0, "pel".length(), 0), 
				span("pel".length(), "pelas".length(), 1)
			));
	}
}