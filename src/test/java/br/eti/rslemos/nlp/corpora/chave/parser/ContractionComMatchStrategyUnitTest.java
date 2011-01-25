package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class ContractionComMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionComMatchStrategyUnitTest() {
		super(new ContractionComMatchStrategy());
	}
	
	@Test
	public void testContraction_com_mim() throws Exception {
		cg.add("com");
		cg.add("mim");
		
		MatchResult result = match("comigo");
		
		verifyIntersectingPseudoToken(result, "co", "m", "igo");
	}
	
	@Test
	public void testContraction_com_ti() throws Exception {
		cg.add("com");
		cg.add("ti");
		
		MatchResult result = match("contigo");
		
		verifyTokensInSequence(result, "con", "tigo");
	}
	
	@Test
	public void testContraction_com_si() throws Exception {
		cg.add("com");
		cg.add("si");
		
		MatchResult result = match("consigo");
		
		verifyTokensInSequence(result, "con", "sigo");
	}
	
	@Test
	public void testContraction_com_nos() throws Exception {
		cg.add("com");
		cg.add("nos");
		
		MatchResult result = match("conosco");
		
		verifyIntersectingPseudoToken(result, "co", "n", "osco");
	}
	
	@Test
	public void testContraction_com_vos() throws Exception {
		cg.add("com");
		cg.add("vos");
		
		MatchResult result = match("convosco");
		
		verifyTokensInSequence(result, "con", "vosco");
	}
}
