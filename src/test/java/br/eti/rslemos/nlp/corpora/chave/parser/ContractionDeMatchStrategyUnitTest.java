package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class ContractionDeMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionDeMatchStrategyUnitTest() {
		super(new ContractionDeMatchStrategy());
	}
	
	@Test
	public void testContraction_de_o() throws Exception {
		cg.add("de");
		cg.add("o");
		
		Match result = runOver("do");
		
		verifyMatch(result, 0, 2, 
				span(0, 1, 0),
				span(1, 2, 1)
			);
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		cg.add("de");
		cg.add("a");
		
		Match result = runOver("da");
		
		verifyMatch(result, 0, 2, 
				span(0, 1, 0),
				span(1, 2, 1)
			);
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		cg.add("de");
		cg.add("os");
		
		Match result = runOver("dos");
		
		verifyMatch(result, 0, 3, 
				span(0, 1, 0),
				span(1, 3, 1)
			);
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		cg.add("de");
		cg.add("as");
		
		Match result = runOver("das");
		
		verifyMatch(result, 0, 3, 
				span(0, 1, 0),
				span(1, 3, 1)
			);
	}

	@Test
	public void testContraction_de_este() throws Exception {
		cg.add("de");
		cg.add("este");
		
		Match result = runOver("deste");
		
		verifyMatch(result, 0, "deste".length(), 
				span(0, 1, 0),
				span(1, "deste".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_esse() throws Exception {
		cg.add("de");
		cg.add("esse");
		
		Match result = runOver("desse");
		
		verifyMatch(result, 0, "desse".length(), 
				span(0, 1, 0),
				span(1, "desse".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_esta() throws Exception {
		cg.add("de");
		cg.add("esta");
		
		Match result = runOver("desta");
		
		verifyMatch(result, 0, "desta".length(), 
				span(0, 1, 0),
				span(1, "desta".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_essa() throws Exception {
		cg.add("de");
		cg.add("essa");
		
		Match result = runOver("dessa");
		
		verifyMatch(result, 0, "dessa".length(), 
				span(0, 1, 0),
				span(1, "dessa".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_estes() throws Exception {
		cg.add("de");
		cg.add("estes");
		
		Match result = runOver("destes");
		
		verifyMatch(result, 0, "destes".length(), 
				span(0, 1, 0),
				span(1, "destes".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_esses() throws Exception {
		cg.add("de");
		cg.add("esses");
		
		Match result = runOver("desses");
		
		verifyMatch(result, 0, "desses".length(), 
				span(0, 1, 0),
				span(1, "desses".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_estas() throws Exception {
		cg.add("de");
		cg.add("estas");
		
		Match result = runOver("destas");
		
		verifyMatch(result, 0, "destas".length(), 
				span(0, 1, 0),
				span(1, "destas".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_essas() throws Exception {
		cg.add("de");
		cg.add("essas");
		
		Match result = runOver("dessas");
		
		verifyMatch(result, 0, "dessas".length(), 
				span(0, 1, 0),
				span(1, "dessas".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_isto() throws Exception {
		cg.add("de");
		cg.add("isto");
		
		Match result = runOver("disto");
		
		verifyMatch(result, 0, "disto".length(), 
				span(0, 1, 0),
				span(1, "disto".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_isso() throws Exception {
		cg.add("de");
		cg.add("isso");
		
		Match result = runOver("disso");
		
		verifyMatch(result, 0, "disso".length(), 
				span(0, 1, 0),
				span(1, "disso".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_aquele() throws Exception {
		cg.add("de");
		cg.add("aquele");
		
		Match result = runOver("daquele");
		
		verifyMatch(result, 0, "daquele".length(), 
				span(0, 1, 0),
				span(1, "daquele".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_aquela() throws Exception {
		cg.add("de");
		cg.add("aquela");
		
		Match result = runOver("daquela");
		
		verifyMatch(result, 0, "daquela".length(), 
				span(0, 1, 0),
				span(1, "daquela".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_aqueles() throws Exception {
		cg.add("de");
		cg.add("aqueles");
		
		Match result = runOver("daqueles");
		
		verifyMatch(result, 0, "daqueles".length(), 
				span(0, 1, 0),
				span(1, "daqueles".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_aquelas() throws Exception {
		cg.add("de");
		cg.add("aquelas");
		
		Match result = runOver("daquelas");
		
		verifyMatch(result, 0, "daquelas".length(), 
				span(0, 1, 0),
				span(1, "daquelas".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_aquilo() throws Exception {
		cg.add("de");
		cg.add("aquilo");
		
		Match result = runOver("daquilo");
		
		verifyMatch(result, 0, "daquilo".length(), 
				span(0, 1, 0),
				span(1, "daquilo".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_ele() throws Exception {
		cg.add("de");
		cg.add("ele");
		
		Match result = runOver("dele");
		
		verifyMatch(result, 0, "dele".length(), 
				span(0, 1, 0),
				span(1, "dele".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_ela() throws Exception {
		cg.add("de");
		cg.add("ela");
		
		Match result = runOver("dela");
		
		verifyMatch(result, 0, "dela".length(), 
				span(0, 1, 0),
				span(1, "dela".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_eles() throws Exception {
		cg.add("de");
		cg.add("eles");
		
		Match result = runOver("deles");
		
		verifyMatch(result, 0, "deles".length(), 
				span(0, 1, 0),
				span(1, "deles".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_elas() throws Exception {
		cg.add("de");
		cg.add("elas");
		
		Match result = runOver("delas");
		
		verifyMatch(result, 0, "delas".length(), 
				span(0, 1, 0),
				span(1, "delas".length(), 1)
			);
	}
	
	@Test
	public void testContraction_de_aí() throws Exception {
		cg.add("de");
		cg.add("aí");
		
		Match result = runOver("daí");
		
		verifyMatch(result, 0, "daí".length(), 
				span(0, 1, 0),
				span(1, "daí".length(), 1)
			);
	}
	
	@Test
	public void testExpressionContraction_Alem_de_isso() throws Exception {
		cg.add("Além=de");
		cg.add("isso");
		
		Match result = runOver("Além disso");
		
		verifyMatch(result, 0, "Além disso".length(), 
				span(0, "Além d".length(), 0),
				span("Além d".length(), "Além disso".length(), 1)
			);
	}
}
