package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class ContractionEmMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionEmMatchStrategyUnitTest() {
		super(new ContractionEmMatchStrategy());
	}
	
	@Test
	public void testContraction_em_o() throws Exception {
		cg.add("em");
		cg.add("o");
		
		MatchResult result = match("no");
		
		verifyTokensInSequence(result, "n", "o");
	}
	
	@Test
	public void testContraction_em_a() throws Exception {
		cg.add("em");
		cg.add("a");
		
		MatchResult result = match("na");
		
		verifyTokensInSequence(result, "n", "a");
	}
	
	@Test
	public void testContraction_em_os() throws Exception {
		cg.add("em");
		cg.add("os");
		
		MatchResult result = match("nos");
		
		verifyTokensInSequence(result, "n", "os");
	}
	
	@Test
	public void testContraction_em_as() throws Exception {
		cg.add("em");
		cg.add("as");
		
		MatchResult result = match("nas");
		
		verifyTokensInSequence(result, "n", "as");
	}

	@Test
	public void testContraction_em_este() throws Exception {
		cg.add("em");
		cg.add("este");
		
		MatchResult result = match("neste");
		
		verifyTokensInSequence(result, "n", "este");
	}
	
	@Test
	public void testContraction_em_esse() throws Exception {
		cg.add("em");
		cg.add("esse");
		
		MatchResult result = match("nesse");
		
		verifyTokensInSequence(result, "n", "esse");
	}
	
	@Test
	public void testContraction_em_esta() throws Exception {
		cg.add("em");
		cg.add("esta");
		
		MatchResult result = match("nesta");
		
		verifyTokensInSequence(result, "n", "esta");
	}
	
	@Test
	public void testContraction_em_essa() throws Exception {
		cg.add("em");
		cg.add("essa");
		
		MatchResult result = match("nessa");
		
		verifyTokensInSequence(result, "n", "essa");
	}
	
	@Test
	public void testContraction_em_estes() throws Exception {
		cg.add("em");
		cg.add("estes");
		
		MatchResult result = match("nestes");
		
		verifyTokensInSequence(result, "n", "estes");
	}
	
	@Test
	public void testContraction_em_esses() throws Exception {
		cg.add("em");
		cg.add("esses");
		
		MatchResult result = match("nesses");
		
		verifyTokensInSequence(result, "n", "esses");
	}
	
	@Test
	public void testContraction_em_estas() throws Exception {
		cg.add("em");
		cg.add("estas");
		
		MatchResult result = match("nestas");
		
		verifyTokensInSequence(result, "n", "estas");
	}
	
	@Test
	public void testContraction_em_essas() throws Exception {
		cg.add("em");
		cg.add("essas");
		
		MatchResult result = match("nessas");
		
		verifyTokensInSequence(result, "n", "essas");
	}
	
	@Test
	public void testContraction_em_isto() throws Exception {
		cg.add("em");
		cg.add("isto");
		
		MatchResult result = match("nisto");
		
		verifyTokensInSequence(result, "n", "isto");
	}
	
	@Test
	public void testContraction_em_isso() throws Exception {
		cg.add("em");
		cg.add("isso");
		
		MatchResult result = match("nisso");
		
		verifyTokensInSequence(result, "n", "isso");
	}
	
	@Test
	public void testContraction_em_aquele() throws Exception {
		cg.add("em");
		cg.add("aquele");
		
		MatchResult result = match("naquele");
		
		verifyTokensInSequence(result, "n", "aquele");
	}
	
	@Test
	public void testContraction_em_aquela() throws Exception {
		cg.add("em");
		cg.add("aquela");
		
		MatchResult result = match("naquela");
		
		verifyTokensInSequence(result, "n", "aquela");
	}
	
	@Test
	public void testContraction_em_aqueles() throws Exception {
		cg.add("em");
		cg.add("aqueles");
		
		MatchResult result = match("naqueles");
		
		verifyTokensInSequence(result, "n", "aqueles");
	}
	
	@Test
	public void testContraction_em_aquelas() throws Exception {
		cg.add("em");
		cg.add("aquelas");
		
		MatchResult result = match("naquelas");
		
		verifyTokensInSequence(result, "n", "aquelas");
	}
	
	@Test
	public void testContraction_em_aquilo() throws Exception {
		cg.add("em");
		cg.add("aquilo");
		
		MatchResult result = match("naquilo");
		
		verifyTokensInSequence(result, "n", "aquilo");
	}
	
	@Test
	public void testContraction_em_ele() throws Exception {
		cg.add("em");
		cg.add("ele");
		
		MatchResult result = match("nele");
		
		verifyTokensInSequence(result, "n", "ele");
	}
	
	@Test
	public void testContraction_em_ela() throws Exception {
		cg.add("em");
		cg.add("ela");
		
		MatchResult result = match("nela");
		
		verifyTokensInSequence(result, "n", "ela");
	}
	
	@Test
	public void testContraction_em_eles() throws Exception {
		cg.add("em");
		cg.add("eles");
		
		MatchResult result = match("neles");
		
		verifyTokensInSequence(result, "n", "eles");
	}
	
	@Test
	public void testContraction_em_elas() throws Exception {
		cg.add("em");
		cg.add("elas");
		
		MatchResult result = match("nelas");
		
		verifyTokensInSequence(result, "n", "elas");
	}
	
	@Test
	public void testContraction_em_um() throws Exception {
		cg.add("em");
		cg.add("um");
		
		MatchResult result = match("num");
		
		verifyTokensInSequence(result, "n", "um");
	}
	
	@Test
	public void testContraction_em_uma() throws Exception {
		cg.add("em");
		cg.add("uma");
		
		MatchResult result = match("numa");
		
		verifyTokensInSequence(result, "n", "uma");
	}
	
	@Test
	public void testContraction_em_uns() throws Exception {
		cg.add("em");
		cg.add("uns");
		
		MatchResult result = match("nuns");
		
		verifyTokensInSequence(result, "n", "uns");
	}
	
	@Test
	public void testContraction_em_umas() throws Exception {
		cg.add("em");
		cg.add("umas");
		
		MatchResult result = match("numas");
		
		verifyTokensInSequence(result, "n", "umas");
	}
	
	@Test
	public void testExpressionContraction_em_o_qual() throws Exception {
		cg.add("em");
		cg.add("o=qual");
		
		MatchResult result = match("no qual");
		
		verifyTokensInSequence(result, "n", "o qual");
		
	}
}
