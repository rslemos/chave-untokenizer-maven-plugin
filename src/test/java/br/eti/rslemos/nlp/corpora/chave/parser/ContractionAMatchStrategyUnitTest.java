package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class ContractionAMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionAMatchStrategyUnitTest() {
		super(new ContractionAMatchStrategy());
	}
	
	@Test
	public void testContraction_a_o() throws Exception {
		cg.add("a");
		cg.add("o");
		
		MatchResult result = match("ao");
		
		verifyTokensInSequence(result, "a", "o");
	}
	
	@Test
	public void testContraction_a_a() throws Exception {
		cg.add("a");
		cg.add("a");
		
		MatchResult result = match("à");
		
		verifyFullOverlappingTokens(result, "à");
	}
	
	@Test
	public void testContraction_a_os() throws Exception {
		cg.add("a");
		cg.add("os");
		
		MatchResult result = match("aos");
		
		verifyTokensInSequence(result, "a", "os");
	}
	
	@Test
	public void testContraction_a_as() throws Exception {
		cg.add("a");
		cg.add("as");
		
		MatchResult result = match("às");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "s");
	}
	
	@Test
	public void testContraction_a_aquele() throws Exception {
		cg.add("a");
		cg.add("aquele");
		
		MatchResult result = match("àquele");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "quele");
	}
	
	@Test
	public void testContraction_a_aquela() throws Exception {
		cg.add("a");
		cg.add("aquela");
		
		MatchResult result = match("àquela");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "quela");
	}
	
	@Test
	public void testContraction_a_aqueles() throws Exception {
		cg.add("a");
		cg.add("aqueles");
		
		MatchResult result = match("àqueles");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "queles");
	}
	
	@Test
	public void testContraction_a_aquelas() throws Exception {
		cg.add("a");
		cg.add("aquelas");
		
		MatchResult result = match("àquelas");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "quelas");
	}

	@Test
	public void testContraction_a_aquilo() throws Exception {
		cg.add("a");
		cg.add("aquilo");
		
		MatchResult result = match("àquilo");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "quilo");
	}
	
	@Test
	public void testExpressionContraction_Quanto_a_as() throws Exception {
		cg.add("Quanto=a");
		cg.add("as");
		
		MatchResult result = match("Quanto às");
		
		verifyIntersectingPseudoToken(result, "Quanto ", "à", "s");
	}
	
	@Test
	public void testExpressionContraction_a_as_quais() throws Exception {
		cg.add("a");
		cg.add("as=quais");
		
		MatchResult result = match("às quais");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "s quais");
	}
	
	@Test
	public void testExpressionContraction_devido_a_a() throws Exception {
		cg.add("devido=a");
		cg.add("a");
		
		MatchResult result = match("devido à");
		
		verifyRightAlignedOverlappingTokens(result, "devido ", "à");
	}
	
	@Test
	public void testExpressionContraction_devido_a_os_quais() throws Exception {
		cg.add("devido=a");
		cg.add("os=quais");
		
		MatchResult result = match("devido aos quais");
		
		verifyTokensInSequence(result, "devido a", "os quais");
	}
}
