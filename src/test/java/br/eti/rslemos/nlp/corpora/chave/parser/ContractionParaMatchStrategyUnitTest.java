package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class ContractionParaMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionParaMatchStrategyUnitTest() {
		super(new ContractionParaMatchStrategy());
	}
	
	@Test
	public void testContraction_para_o() throws Exception {
		cg.add("para");
		cg.add("o");
		
		runOver("pro");
		
		verifyMatches(match(0, "pro".length(), span(0, "pr".length(), 0), 
				span("pr".length(), "pro".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_a() throws Exception {
		cg.add("para");
		cg.add("a");
		
		runOver("pra");
		
		verifyMatches(match(0, "pra".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "pra".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_os() throws Exception {
		cg.add("para");
		cg.add("os");
		
		runOver("pros");
		
		verifyMatches(match(0, "pros".length(), span(0, "pr".length(), 0), 
				span("pr".length(), "pros".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_as() throws Exception {
		cg.add("para");
		cg.add("as");
		
		runOver("pras");
		
		verifyMatches(match(0, "pras".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "pras".length(), 1)
			));
	}

	@Test
	public void testContraction_para_aquele() throws Exception {
		cg.add("para");
		cg.add("aquele");
		
		runOver("praquele");
		
		verifyMatches(match(0, "praquele".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praquele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_aquela() throws Exception {
		cg.add("para");
		cg.add("aquela");
		
		runOver("praquela");
		
		verifyMatches(match(0, "praquela".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praquela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_aqueles() throws Exception {
		cg.add("para");
		cg.add("aqueles");
		
		runOver("praqueles");
		
		verifyMatches(match(0, "praqueles".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praqueles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_aquelas() throws Exception {
		cg.add("para");
		cg.add("aquelas");
		
		runOver("praquelas");
		
		verifyMatches(match(0, "praquelas".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praquelas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_aquilo() throws Exception {
		cg.add("para");
		cg.add("aquilo");
		
		runOver("praquilo");
		
		verifyMatches(match(0, "praquilo".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praquilo".length(), 1)
			));
	}
	
}
