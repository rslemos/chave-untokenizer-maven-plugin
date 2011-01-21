package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry.entry;

import org.junit.Before;
import org.junit.Test;

public class ContractionPorMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	@Before
	public void setUp() {
		super.setUp(new ContractionPorMatchStrategy());
	}
	
	@Test
	public void testContraction_de_o() throws Exception {
		cg.add(entry("por", " [por] PRP <sam-> @ADVL"));
		cg.add(entry("o", " [o] DET M S <artd> <-sam> @>N"));
		
		MatchResult result = match("pelo");
		
		verifyTokensInSequence(result, "pel", "o");
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		cg.add(entry("por", " [por] PRP <sam-> @ADVL"));
		cg.add(entry("a", " [a] DET F S <artd> <-sam> @>N"));
		
		MatchResult result = match("pela");
		
		verifyTokensInSequence(result, "pel", "a");
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		cg.add(entry("por", " [por] PRP <sam-> @ADVL"));
		cg.add(entry("os", " [os] DET M P <artd> <-sam> @>N"));
		
		MatchResult result = match("pelos");
		
		verifyTokensInSequence(result, "pel", "os");
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		cg.add(entry("por", " [por] PRP <sam-> @ADVL"));
		cg.add(entry("as", " [as] DET F P <artd> <-sam> @>N"));
		
		MatchResult result = match("pelas");
		
		verifyTokensInSequence(result, "pel", "as");
	}
}