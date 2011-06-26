package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class ContractionPorMatchStrategyUnitTest extends ContractionMatchStrategyUnitTest {
	
	// por + x
	@Test
	public void testContraction_por_o() throws Exception {
		cg.add("por");
		cg.add("o");
		
		runOver("pelo");
		
		verifyMatches(match(0, "pelo".length(), span(0, "pel".length(), 0), 
				span("pel".length(), "pelo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_por_a() throws Exception {
		cg.add("por");
		cg.add("a");
		
		runOver("pela");
		
		verifyMatches(match(0, "pela".length(), span(0, "pel".length(), 0), 
				span("pel".length(), "pela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_por_os() throws Exception {
		cg.add("por");
		cg.add("os");
		
		runOver("pelos");
		
		verifyMatches(match(0, "pelos".length(), span(0, "pel".length(), 0), 
				span("pel".length(), "pelos".length(), 1)
			));
	}
	
	@Test
	public void testContraction_por_as() throws Exception {
		cg.add("por");
		cg.add("as");
		
		runOver("pelas");
		
		verifyMatches(match(0, "pelas".length(), span(0, "pel".length(), 0), 
				span("pel".length(), "pelas".length(), 1)
			));
	}
	
	// Por + x
	@Test
	public void testContraction_Por_o() throws Exception {
		cg.add("Por");
		cg.add("o");
		
		runOver("Pelo");
		
		verifyMatches(match(0, "Pelo".length(), span(0, "Pel".length(), 0), 
				span("Pel".length(), "Pelo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Por_a() throws Exception {
		cg.add("Por");
		cg.add("a");
		
		runOver("Pela");
		
		verifyMatches(match(0, "Pela".length(), span(0, "Pel".length(), 0), 
				span("Pel".length(), "Pela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Por_os() throws Exception {
		cg.add("Por");
		cg.add("os");
		
		runOver("Pelos");
		
		verifyMatches(match(0, "Pelos".length(), span(0, "Pel".length(), 0), 
				span("Pel".length(), "Pelos".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Por_as() throws Exception {
		cg.add("Por");
		cg.add("as");
		
		runOver("Pelas");
		
		verifyMatches(match(0, "Pelas".length(), span(0, "Pel".length(), 0), 
				span("Pel".length(), "Pelas".length(), 1)
			));
	}
	
	// POR + X
	@Test
	public void testContraction_POR_O() throws Exception {
		cg.add("POR");
		cg.add("O");
		
		runOver("PELO");
		
		verifyMatches(match(0, "PELO".length(), span(0, "PEL".length(), 0), 
				span("PEL".length(), "PELO".length(), 1)
			));
	}
	
	@Test
	public void testContraction_POR_A() throws Exception {
		cg.add("POR");
		cg.add("A");
		
		runOver("PELA");
		
		verifyMatches(match(0, "PELA".length(), span(0, "PEL".length(), 0), 
				span("PEL".length(), "PELA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_POR_OS() throws Exception {
		cg.add("POR");
		cg.add("OS");
		
		runOver("PELOS");
		
		verifyMatches(match(0, "PELOS".length(), span(0, "PEL".length(), 0), 
				span("PEL".length(), "PELOS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_POR_AS() throws Exception {
		cg.add("POR");
		cg.add("AS");
		
		runOver("PELAS");
		
		verifyMatches(match(0, "PELAS".length(), span(0, "PEL".length(), 0), 
				span("PEL".length(), "PELAS".length(), 1)
			));
	}
}