package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class ContractionEmMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionEmMatchStrategyUnitTest() {
		super(new ContractionEmMatchStrategy());
	}
	
	@Test
	public void testContraction_em_o() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("o", " [o] DET M S <artd> <-sam> @>N"));
		
		MatchResult result = match("no");
		
		verifyTokensInSequence(result, "n", "o");
	}
	
	@Test
	public void testContraction_em_a() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("a", " [o] DET F S <artd> <-sam> @>N"));
		
		MatchResult result = match("na");
		
		verifyTokensInSequence(result, "n", "a");
	}
	
	@Test
	public void testContraction_em_os() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("os", " [o] DET M P <artd> <-sam> @>N"));
		
		MatchResult result = match("nos");
		
		verifyTokensInSequence(result, "n", "os");
	}
	
	@Test
	public void testContraction_em_as() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("as", " [o] DET F P <artd> <-sam> @>N"));
		
		MatchResult result = match("nas");
		
		verifyTokensInSequence(result, "n", "as");
	}

	@Test
	public void testContraction_em_este() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("este", " [este] DET M S <dem> <-sam> @>N"));
		
		MatchResult result = match("neste");
		
		verifyTokensInSequence(result, "n", "este");
	}
	
	@Test
	public void testContraction_em_esse() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("esse", " [esse] DET M S <dem> <-sam> @>N"));
		
		MatchResult result = match("nesse");
		
		verifyTokensInSequence(result, "n", "esse");
	}
	
	@Test
	public void testContraction_em_esta() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("esta", " [este] DET F S <dem> <-sam> @>N"));
		
		MatchResult result = match("nesta");
		
		verifyTokensInSequence(result, "n", "esta");
	}
	
	@Test
	public void testContraction_em_essa() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("essa", " [esse] DET F S <dem> <-sam> @>N"));
		
		MatchResult result = match("nessa");
		
		verifyTokensInSequence(result, "n", "essa");
	}
	
	@Test
	public void testContraction_em_estes() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("estes", " [este] DET M P <dem> <-sam> @>N"));
		
		MatchResult result = match("nestes");
		
		verifyTokensInSequence(result, "n", "estes");
	}
	
	@Test
	public void testContraction_em_esses() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("esses", " [esse] DET M P <dem> <-sam> @>N"));
		
		MatchResult result = match("nesses");
		
		verifyTokensInSequence(result, "n", "esses");
	}
	
	@Test
	public void testContraction_em_estas() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("estas", " [este] DET F P <dem> @>N"));
		
		MatchResult result = match("nestas");
		
		verifyTokensInSequence(result, "n", "estas");
	}
	
	@Test
	public void testContraction_em_essas() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("essas", " [esse] DET F P <dem> <-sam> @>N"));
		
		MatchResult result = match("nessas");
		
		verifyTokensInSequence(result, "n", "essas");
	}
	
	@Test
	public void testContraction_em_isto() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("isto", " [isto] SPEC M S <dem> @<ACC"));
		
		MatchResult result = match("nisto");
		
		verifyTokensInSequence(result, "n", "isto");
	}
	
	@Test
	public void testContraction_em_isso() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("isso", " [isso] SPEC M S <dem> @SUBJ>"));
		
		MatchResult result = match("nisso");
		
		verifyTokensInSequence(result, "n", "isso");
	}
	
	@Test
	public void testContraction_em_aquele() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("aquele", " [aquele] DET M S <dem> <-sam> @>N"));
		
		MatchResult result = match("naquele");
		
		verifyTokensInSequence(result, "n", "aquele");
	}
	
	@Test
	public void testContraction_em_aquela() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("aquela", "[aquele] DET F S <dem> @>N"));
		
		MatchResult result = match("naquela");
		
		verifyTokensInSequence(result, "n", "aquela");
	}
	
	@Test
	public void testContraction_em_aqueles() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("aqueles", " [aquele] DET M P <dem> @P<"));
		
		MatchResult result = match("naqueles");
		
		verifyTokensInSequence(result, "n", "aqueles");
	}
	
	@Test
	public void testContraction_em_aquelas() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("aquelas", " [aquele] DET F P <dem> <-sam> @P<"));
		
		MatchResult result = match("naquelas");
		
		verifyTokensInSequence(result, "n", "aquelas");
	}
	
	@Test
	public void testContraction_em_aquilo() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("aquilo", " [aquilo] SPEC M S <dem> <-sam> @P<"));
		
		MatchResult result = match("naquilo");
		
		verifyTokensInSequence(result, "n", "aquilo");
	}
	
	@Test
	public void testContraction_em_ele() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("ele", " [ele] PERS M 3S NOM @SUBJ>"));
		
		MatchResult result = match("nele");
		
		verifyTokensInSequence(result, "n", "ele");
	}
	
	@Test
	public void testContraction_em_ela() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("ela", " [ela] PERS F 3S NOM/PIV <-sam> @P<"));
		
		MatchResult result = match("nela");
		
		verifyTokensInSequence(result, "n", "ela");
	}
	
	@Test
	public void testContraction_em_eles() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("eles", " [eles] PERS M 3P NOM/PIV <-sam> @P<"));
		
		MatchResult result = match("neles");
		
		verifyTokensInSequence(result, "n", "eles");
	}
	
	@Test
	public void testContraction_em_elas() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("elas", " [elas] PERS F 3P NOM/PIV <-sam> @P<"));
		
		MatchResult result = match("nelas");
		
		verifyTokensInSequence(result, "n", "elas");
	}
	
	@Test
	public void testContraction_em_um() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("um", " [um] DET M S <arti> @>N"));
		
		MatchResult result = match("num");
		
		verifyTokensInSequence(result, "n", "um");
	}
	
	@Test
	public void testContraction_em_uma() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("uma", " [um] DET F S <arti> @>N"));
		
		MatchResult result = match("numa");
		
		verifyTokensInSequence(result, "n", "uma");
	}
	
	@Test
	public void testContraction_em_uns() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("uns", " [um] DET M P <arti> @>N"));
		
		MatchResult result = match("nuns");
		
		verifyTokensInSequence(result, "n", "uns");
	}
	
	@Test
	public void testContraction_em_umas() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("umas", " [um] DET F P <arti> @>N"));
		
		MatchResult result = match("numas");
		
		verifyTokensInSequence(result, "n", "umas");
	}
	
	@Test
	public void testExpressionContraction_em_o_qual() throws Exception {
		cg.add(entry("em", " [em] PRP <sam-> @<ADVL"));
		cg.add(entry("o=qual", " [o=qual] SPEC M S @P< <rel> <-sam> @#FS-N<"));
		
		MatchResult result = match("no qual");
		
		verifyTokensInSequence(result, "n", "o qual");
		
	}
}
