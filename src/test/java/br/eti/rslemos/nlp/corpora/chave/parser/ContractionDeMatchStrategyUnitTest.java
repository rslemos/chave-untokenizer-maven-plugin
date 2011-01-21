package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry.entry;

import org.junit.Before;
import org.junit.Test;

public class ContractionDeMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	@Before
	public void setUp() {
		super.setUp(new ContractionDeMatchStrategy());
	}
	
	@Test
	public void testContraction_de_o() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("o", " [o] DET M S <artd> <-sam> @>N"));
		
		MatchResult result = match("do");
		
		verifyTokensInSequence(result, "d", "o");
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("a", " [a] DET F S <artd> <-sam> @>N"));
		
		MatchResult result = match("da");
		
		verifyTokensInSequence(result, "d", "a");
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("os", " [os] DET M P <artd> <-sam> @>N"));
		
		MatchResult result = match("dos");
		
		verifyTokensInSequence(result, "d", "os");
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("as", " [as] DET F P <artd> <-sam> @>N"));
		
		MatchResult result = match("das");
		
		verifyTokensInSequence(result, "d", "as");
	}

	@Test
	public void testContraction_de_este() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("este", " [este] DET M S <dem> <-sam> @>N"));
		
		MatchResult result = match("deste");
		
		verifyTokensInSequence(result, "d", "este");
	}
	
	@Test
	public void testContraction_de_esse() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("esse", " [esse] DET M S <dem> <-sam> @>N"));
		
		MatchResult result = match("desse");
		
		verifyTokensInSequence(result, "d", "esse");
	}
	
	@Test
	public void testContraction_de_esta() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("esta", " [este] DET F S <dem> <-sam> @>N"));
		
		MatchResult result = match("desta");
		
		verifyTokensInSequence(result, "d", "esta");
	}
	
	@Test
	public void testContraction_de_essa() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("essa", " [esse] DET F S <dem> <-sam> @>N"));
		
		MatchResult result = match("dessa");
		
		verifyTokensInSequence(result, "d", "essa");
	}
	
	@Test
	public void testContraction_de_estes() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("estes", " [este] DET M P <dem> <-sam> @>N"));
		
		MatchResult result = match("destes");
		
		verifyTokensInSequence(result, "d", "estes");
	}
	
	@Test
	public void testContraction_de_esses() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("esses", " [esse] DET M P <dem> <-sam> @>N"));
		
		MatchResult result = match("desses");
		
		verifyTokensInSequence(result, "d", "esses");
	}
	
	@Test
	public void testContraction_de_estas() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("estas", " [este] DET F P <dem> @>N"));
		
		MatchResult result = match("destas");
		
		verifyTokensInSequence(result, "d", "estas");
	}
	
	@Test
	public void testContraction_de_essas() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("essas", " [esse] DET F P <dem> <-sam> @>N"));
		
		MatchResult result = match("dessas");
		
		verifyTokensInSequence(result, "d", "essas");
	}
	
	@Test
	public void testContraction_de_isto() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("isto", " [isto] SPEC M S <dem> @<ACC"));
		
		MatchResult result = match("disto");
		
		verifyTokensInSequence(result, "d", "isto");
	}
	
	@Test
	public void testContraction_de_isso() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("isso", " [isso] SPEC M S <dem> @SUBJ>"));
		
		MatchResult result = match("disso");
		
		verifyTokensInSequence(result, "d", "isso");
	}
	
	@Test
	public void testContraction_de_aquele() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("aquele", " [aquele] DET M S <dem> <-sam> @>N"));
		
		MatchResult result = match("daquele");
		
		verifyTokensInSequence(result, "d", "aquele");
	}
	
	@Test
	public void testContraction_de_aquela() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("aquela", "[aquele] DET F S <dem> @>N"));
		
		MatchResult result = match("daquela");
		
		verifyTokensInSequence(result, "d", "aquela");
	}
	
	@Test
	public void testContraction_de_aqueles() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("aqueles", " [aquele] DET M P <dem> @P<"));
		
		MatchResult result = match("daqueles");
		
		verifyTokensInSequence(result, "d", "aqueles");
	}
	
	@Test
	public void testContraction_de_aquelas() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("aquelas", " [aquele] DET F P <dem> <-sam> @P<"));
		
		MatchResult result = match("daquelas");
		
		verifyTokensInSequence(result, "d", "aquelas");
	}
	
	@Test
	public void testContraction_de_aquilo() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("aquilo", " [aquilo] SPEC M S <dem> <-sam> @P<"));
		
		MatchResult result = match("daquilo");
		
		verifyTokensInSequence(result, "d", "aquilo");
	}
	
	@Test
	public void testContraction_de_ele() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("ele", " [ele] PERS M 3S NOM @SUBJ>"));
		
		MatchResult result = match("dele");
		
		verifyTokensInSequence(result, "d", "ele");
	}
	
	@Test
	public void testContraction_de_ela() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("ela", " [ela] PERS F 3S NOM/PIV <-sam> @P<"));
		
		MatchResult result = match("dela");
		
		verifyTokensInSequence(result, "d", "ela");
	}
	
	@Test
	public void testContraction_de_eles() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("eles", " [eles] PERS M 3P NOM/PIV <-sam> @P<"));
		
		MatchResult result = match("deles");
		
		verifyTokensInSequence(result, "d", "eles");
	}
	
	@Test
	public void testContraction_de_elas() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @N<"));
		cg.add(entry("elas", " [elas] PERS F 3P NOM/PIV <-sam> @P<"));
		
		MatchResult result = match("delas");
		
		verifyTokensInSequence(result, "d", "elas");
	}
	
	@Test
	public void testContraction_de_aí() throws Exception {
		cg.add(entry("de", " [de] PRP <sam-> @<ADVL"));
		cg.add(entry("aí", " [aí] ADV <-sam> @<ADVL"));
		
		MatchResult result = match("daí");
		
		verifyTokensInSequence(result, "d", "aí");
	}
	
	@Test
	public void testExpressionContraction_Alem_de_isso() throws Exception {
		cg.add(entry("Além=de", " [além=de] PRP <sam-> @ADVL>"));
		cg.add(entry("isso", " [isso] SPEC M S <-sam> <dem> @P<"));
		
		MatchResult result = match("Além disso");
		
		verifyTokensInSequence(result, "Além d", "isso");
	}
}
