package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry.entry;

import org.junit.Before;
import org.junit.Test;

public class ContractionAMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	@Before
	public void setUp() {
		super.setUp(new ContractionAMatchStrategy());
	}
	
	@Test
	public void testContraction_a_o() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("o", " [o] DET M S <artd> <-sam> @>N"));
		
		MatchResult result = match("ao");
		
		verifyTokensInSequence(result, "a", "o");
	}
	
	@Test
	public void testContraction_a_a() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("a", " [o] DET F S <artd> <-sam> @>N"));
		
		MatchResult result = match("à");
		
		verifyFullOverlappingTokens(result, "à");
	}
	
	@Test
	public void testContraction_a_os() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("os", " [o] DET M P <artd> <-sam> @>N"));
		
		MatchResult result = match("aos");
		
		verifyTokensInSequence(result, "a", "os");
	}
	
	@Test
	public void testContraction_a_as() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("as", " [o] DET F P <artd> <-sam> @>N"));
		
		MatchResult result = match("às");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "s");
	}
	
	@Test
	public void testContraction_a_aquele() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("aquele", "[aquele] DET M S <dem> @>N"));
		
		MatchResult result = match("àquele");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "quele");
	}
	
	@Test
	public void testContraction_a_aquela() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("aquela", "[aquele] DET F S <dem> @>N"));
		
		MatchResult result = match("àquela");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "quela");
	}
	
	@Test
	public void testContraction_a_aqueles() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("aqueles", " [aquele] DET M P <dem> @P<"));
		
		MatchResult result = match("àqueles");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "queles");
	}
	
	@Test
	public void testContraction_a_aquelas() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("aquelas", " [aquele] DET F P <dem> <-sam> @P<"));
		
		MatchResult result = match("àquelas");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "quelas");
	}

	@Test
	public void testContraction_a_aquilo() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("aquilo", " [aquilo] SPEC M S <dem> <-sam> @P<"));
		
		MatchResult result = match("àquilo");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "quilo");
	}
	
	@Test
	public void testExpressionContraction_Quanto_a_as() throws Exception {
		cg.add(entry("Quanto=a", " [quanto=a] PRP <sam-> @ADVL>"));
		cg.add(entry("as", " [o] DET F P <artd> <-sam> @>N"));
		
		MatchResult result = match("Quanto às");
		
		verifyIntersectingPseudoToken(result, "Quanto ", "à", "s");
	}
	
	@Test
	public void testExpressionContraction_a_as_quais() throws Exception {
		cg.add(entry("a", " [a] PRP <sam-> @ADVL"));
		cg.add(entry("as=quais", " [o=qual] SPEC F P @P< <rel> <-sam> @#FS-N<"));
		
		MatchResult result = match("às quais");
		
		verifyLeftAlignedOverlappingTokens(result, "à", "s quais");
	}
	
	@Test
	public void testExpressionContraction_devido_a_a() throws Exception {
		cg.add(entry("devido=a", " [devido=a] PRP <sam-> @<ADVL"));
		cg.add(entry("a", " [o] DET F S <-sam> <artd> @>N"));
		
		MatchResult result = match("devido à");
		
		verifyRightAlignedOverlappingTokens(result, "devido ", "à");
	}
	
	@Test
	public void testExpressionContraction_devido_a_os_quais() throws Exception {
		cg.add(entry("devido=a", " [devido=a] PRP <sam-> @<ADVL"));
		cg.add(entry("os=quais", " [o=qual] SPEC M P @P< <rel> <-sam> @#FS-N<"));
		
		MatchResult result = match("devido aos quais");
		
		verifyTokensInSequence(result, "devido a", "os quais");
	}
}
