package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class ContractionDeMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionDeMatchStrategyUnitTest() {
		super(new ContractionDeMatchStrategy());
	}
	
	@Test
	public void testContraction_de_o() throws Exception {
		cg.add("de");
		cg.add("o");
		
		runOver("do");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		cg.add("de");
		cg.add("a");
		
		runOver("da");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		cg.add("de");
		cg.add("os");
		
		runOver("dos");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		cg.add("de");
		cg.add("as");
		
		runOver("das");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}

	@Test
	public void testContraction_de_este() throws Exception {
		cg.add("de");
		cg.add("este");
		
		runOver("deste");
		
		verifyMatches(match(0, "deste".length(), span(0, 1, 0), 
				span(1, "deste".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_esse() throws Exception {
		cg.add("de");
		cg.add("esse");
		
		runOver("desse");
		
		verifyMatches(match(0, "desse".length(), span(0, 1, 0), 
				span(1, "desse".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_esta() throws Exception {
		cg.add("de");
		cg.add("esta");
		
		runOver("desta");
		
		verifyMatches(match(0, "desta".length(), span(0, 1, 0), 
				span(1, "desta".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_essa() throws Exception {
		cg.add("de");
		cg.add("essa");
		
		runOver("dessa");
		
		verifyMatches(match(0, "dessa".length(), span(0, 1, 0), 
				span(1, "dessa".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_estes() throws Exception {
		cg.add("de");
		cg.add("estes");
		
		runOver("destes");
		
		verifyMatches(match(0, "destes".length(), span(0, 1, 0), 
				span(1, "destes".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_esses() throws Exception {
		cg.add("de");
		cg.add("esses");
		
		runOver("desses");
		
		verifyMatches(match(0, "desses".length(), span(0, 1, 0), 
				span(1, "desses".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_estas() throws Exception {
		cg.add("de");
		cg.add("estas");
		
		runOver("destas");
		
		verifyMatches(match(0, "destas".length(), span(0, 1, 0), 
				span(1, "destas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_essas() throws Exception {
		cg.add("de");
		cg.add("essas");
		
		runOver("dessas");
		
		verifyMatches(match(0, "dessas".length(), span(0, 1, 0), 
				span(1, "dessas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_isto() throws Exception {
		cg.add("de");
		cg.add("isto");
		
		runOver("disto");
		
		verifyMatches(match(0, "disto".length(), span(0, 1, 0), 
				span(1, "disto".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_isso() throws Exception {
		cg.add("de");
		cg.add("isso");
		
		runOver("disso");
		
		verifyMatches(match(0, "disso".length(), span(0, 1, 0), 
				span(1, "disso".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_aquele() throws Exception {
		cg.add("de");
		cg.add("aquele");
		
		runOver("daquele");
		
		verifyMatches(match(0, "daquele".length(), span(0, 1, 0), 
				span(1, "daquele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_aquela() throws Exception {
		cg.add("de");
		cg.add("aquela");
		
		runOver("daquela");
		
		verifyMatches(match(0, "daquela".length(), span(0, 1, 0), 
				span(1, "daquela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_aqueles() throws Exception {
		cg.add("de");
		cg.add("aqueles");
		
		runOver("daqueles");
		
		verifyMatches(match(0, "daqueles".length(), span(0, 1, 0), 
				span(1, "daqueles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_aquelas() throws Exception {
		cg.add("de");
		cg.add("aquelas");
		
		runOver("daquelas");
		
		verifyMatches(match(0, "daquelas".length(), span(0, 1, 0), 
				span(1, "daquelas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_aquilo() throws Exception {
		cg.add("de");
		cg.add("aquilo");
		
		runOver("daquilo");
		
		verifyMatches(match(0, "daquilo".length(), span(0, 1, 0), 
				span(1, "daquilo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_ele() throws Exception {
		cg.add("de");
		cg.add("ele");
		
		runOver("dele");
		
		verifyMatches(match(0, "dele".length(), span(0, 1, 0), 
				span(1, "dele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_ela() throws Exception {
		cg.add("de");
		cg.add("ela");
		
		runOver("dela");
		
		verifyMatches(match(0, "dela".length(), span(0, 1, 0), 
				span(1, "dela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_eles() throws Exception {
		cg.add("de");
		cg.add("eles");
		
		runOver("deles");
		
		verifyMatches(match(0, "deles".length(), span(0, 1, 0), 
				span(1, "deles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_elas() throws Exception {
		cg.add("de");
		cg.add("elas");
		
		runOver("delas");
		
		verifyMatches(match(0, "delas".length(), span(0, 1, 0), 
				span(1, "delas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_aí() throws Exception {
		cg.add("de");
		cg.add("aí");
		
		runOver("daí");
		
		verifyMatches(match(0, "daí".length(), span(0, 1, 0), 
				span(1, "daí".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_Alem_de_isso() throws Exception {
		cg.add("Além=de");
		cg.add("isso");
		
		runOver("Além disso");
		
		verifyMatches(match(0, "Além disso".length(), span(0, "Além d".length(), 0), 
				span("Além d".length(), "Além disso".length(), 1)
			));
	}

	@Test
	public void testManyContractions() throws Exception {
		cg.add("");
		cg.add("de");
		cg.add("o");
		cg.add("");
		cg.add("de");
		cg.add("a");
		cg.add("");
		
		runOver("do da do da dado");
		
		verifyMatches(
				match( 0,  2, 
						span( 0,  1, 1), 
						span( 1,  2, 2)
					),
				match( 3,  5, 
						span( 3,  4, 4), 
						span( 4,  5, 5)
					),
				match( 6,  8, 
						span( 6,  7, 1), 
						span( 7,  8, 2)
					),
				match( 9, 11, 
						span( 9, 10, 4), 
						span(10, 11, 5)
					)
			);
	}
}
