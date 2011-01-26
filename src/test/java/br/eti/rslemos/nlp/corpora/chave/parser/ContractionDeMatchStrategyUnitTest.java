package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class ContractionDeMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionDeMatchStrategyUnitTest() {
		super(new ContractionDeMatchStrategy());
	}
	
	@Test
	public void testContraction_de_o() throws Exception {
		cg.add("de");
		cg.add("o");
		
		Match result = match("do");
		
		verifyTokensInSequence(result, "d", "o");
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		cg.add("de");
		cg.add("a");
		
		Match result = match("da");
		
		verifyTokensInSequence(result, "d", "a");
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		cg.add("de");
		cg.add("os");
		
		Match result = match("dos");
		
		verifyTokensInSequence(result, "d", "os");
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		cg.add("de");
		cg.add("as");
		
		Match result = match("das");
		
		verifyTokensInSequence(result, "d", "as");
	}

	@Test
	public void testContraction_de_este() throws Exception {
		cg.add("de");
		cg.add("este");
		
		Match result = match("deste");
		
		verifyTokensInSequence(result, "d", "este");
	}
	
	@Test
	public void testContraction_de_esse() throws Exception {
		cg.add("de");
		cg.add("esse");
		
		Match result = match("desse");
		
		verifyTokensInSequence(result, "d", "esse");
	}
	
	@Test
	public void testContraction_de_esta() throws Exception {
		cg.add("de");
		cg.add("esta");
		
		Match result = match("desta");
		
		verifyTokensInSequence(result, "d", "esta");
	}
	
	@Test
	public void testContraction_de_essa() throws Exception {
		cg.add("de");
		cg.add("essa");
		
		Match result = match("dessa");
		
		verifyTokensInSequence(result, "d", "essa");
	}
	
	@Test
	public void testContraction_de_estes() throws Exception {
		cg.add("de");
		cg.add("estes");
		
		Match result = match("destes");
		
		verifyTokensInSequence(result, "d", "estes");
	}
	
	@Test
	public void testContraction_de_esses() throws Exception {
		cg.add("de");
		cg.add("esses");
		
		Match result = match("desses");
		
		verifyTokensInSequence(result, "d", "esses");
	}
	
	@Test
	public void testContraction_de_estas() throws Exception {
		cg.add("de");
		cg.add("estas");
		
		Match result = match("destas");
		
		verifyTokensInSequence(result, "d", "estas");
	}
	
	@Test
	public void testContraction_de_essas() throws Exception {
		cg.add("de");
		cg.add("essas");
		
		Match result = match("dessas");
		
		verifyTokensInSequence(result, "d", "essas");
	}
	
	@Test
	public void testContraction_de_isto() throws Exception {
		cg.add("de");
		cg.add("isto");
		
		Match result = match("disto");
		
		verifyTokensInSequence(result, "d", "isto");
	}
	
	@Test
	public void testContraction_de_isso() throws Exception {
		cg.add("de");
		cg.add("isso");
		
		Match result = match("disso");
		
		verifyTokensInSequence(result, "d", "isso");
	}
	
	@Test
	public void testContraction_de_aquele() throws Exception {
		cg.add("de");
		cg.add("aquele");
		
		Match result = match("daquele");
		
		verifyTokensInSequence(result, "d", "aquele");
	}
	
	@Test
	public void testContraction_de_aquela() throws Exception {
		cg.add("de");
		cg.add("aquela");
		
		Match result = match("daquela");
		
		verifyTokensInSequence(result, "d", "aquela");
	}
	
	@Test
	public void testContraction_de_aqueles() throws Exception {
		cg.add("de");
		cg.add("aqueles");
		
		Match result = match("daqueles");
		
		verifyTokensInSequence(result, "d", "aqueles");
	}
	
	@Test
	public void testContraction_de_aquelas() throws Exception {
		cg.add("de");
		cg.add("aquelas");
		
		Match result = match("daquelas");
		
		verifyTokensInSequence(result, "d", "aquelas");
	}
	
	@Test
	public void testContraction_de_aquilo() throws Exception {
		cg.add("de");
		cg.add("aquilo");
		
		Match result = match("daquilo");
		
		verifyTokensInSequence(result, "d", "aquilo");
	}
	
	@Test
	public void testContraction_de_ele() throws Exception {
		cg.add("de");
		cg.add("ele");
		
		Match result = match("dele");
		
		verifyTokensInSequence(result, "d", "ele");
	}
	
	@Test
	public void testContraction_de_ela() throws Exception {
		cg.add("de");
		cg.add("ela");
		
		Match result = match("dela");
		
		verifyTokensInSequence(result, "d", "ela");
	}
	
	@Test
	public void testContraction_de_eles() throws Exception {
		cg.add("de");
		cg.add("eles");
		
		Match result = match("deles");
		
		verifyTokensInSequence(result, "d", "eles");
	}
	
	@Test
	public void testContraction_de_elas() throws Exception {
		cg.add("de");
		cg.add("elas");
		
		Match result = match("delas");
		
		verifyTokensInSequence(result, "d", "elas");
	}
	
	@Test
	public void testContraction_de_aí() throws Exception {
		cg.add("de");
		cg.add("aí");
		
		Match result = match("daí");
		
		verifyTokensInSequence(result, "d", "aí");
	}
	
	@Test
	public void testExpressionContraction_Alem_de_isso() throws Exception {
		cg.add("Além=de");
		cg.add("isso");
		
		Match result = match("Além disso");
		
		verifyTokensInSequence(result, "Além d", "isso");
	}
}
