package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry.entry;

import org.junit.Test;

public class ContractionComMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionComMatchStrategyUnitTest() {
		super(new ContractionComMatchStrategy());
	}
	
	@Test
	public void testContraction_com_mim() throws Exception {
		cg.add(entry("com", " [com] PRP <sam-> @<ADVL"));
		cg.add(entry("mim", " [eu] PERS M/F 1S PIV @P<"));
		
		MatchResult result = match("comigo");
		
		verifyIntersectingPseudoToken(result, "co", "m", "igo");
	}
	
	@Test
	public void testContraction_com_ti() throws Exception {
		cg.add(entry("com", " [com] PRP <sam-> @<ADVL"));
		cg.add(entry("ti", " [tu] PERS M/F 2S PIV @P<"));
		
		MatchResult result = match("contigo");
		
		verifyTokensInSequence(result, "con", "tigo");
	}
	
	@Test
	public void testContraction_com_si() throws Exception {
		cg.add(entry("com", " [com] PRP <sam-> @<ADVL"));
		cg.add(entry("si", " [se] <refl> PERS M/F 3S/P PIV @P<"));
		
		MatchResult result = match("consigo");
		
		verifyTokensInSequence(result, "con", "sigo");
	}
	
	@Test
	public void testContraction_com_nos() throws Exception {
		cg.add(entry("com", " [com] PRP <sam-> @<ADVL"));
		cg.add(entry("nos", " [nós] PERS M/F 1P PIV @P<"));
		
		MatchResult result = match("conosco");
		
		verifyIntersectingPseudoToken(result, "co", "n", "osco");
	}
	
	@Test
	public void testContraction_com_vos() throws Exception {
		cg.add(entry("com", " [com] PRP <sam-> @<ADVL"));
		cg.add(entry("vos", " [vós] PERS M/F 2P PIV @P<"));
		
		MatchResult result = match("convosco");
		
		verifyTokensInSequence(result, "con", "vosco");
	}
}
