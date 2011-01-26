package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class ContractionPorMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionPorMatchStrategyUnitTest() {
		super(new ContractionPorMatchStrategy());
	}
	
	@Test
	public void testContraction_de_o() throws Exception {
		cg.add("por");
		cg.add("o");
		
		Match result = match("pelo");
		
		verifyTokensInSequence(result, "pel", "o");
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		cg.add("por");
		cg.add("a");
		
		Match result = match("pela");
		
		verifyTokensInSequence(result, "pel", "a");
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		cg.add("por");
		cg.add("os");
		
		Match result = match("pelos");
		
		verifyTokensInSequence(result, "pel", "os");
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		cg.add("por");
		cg.add("as");
		
		Match result = match("pelas");
		
		verifyTokensInSequence(result, "pel", "as");
	}
}