package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class ContractionDeMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionDeMatchStrategyUnitTest() {
		super(new ContractionDeMatchStrategy());
	}

	// de + x
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
	public void testContraction_de_entre() throws Exception {
		cg.add("de");
		cg.add("entre");
		
		runOver("dentre");
		
		verifyMatches(match(0, "dentre".length(), span(0, 1, 0), 
				span(1, "dentre".length(), 1)
			));
	}
	
	@Test
	public void testContraction_de_aqui() throws Exception {
		cg.add("de");
		cg.add("aqui");
		
		runOver("daqui");
		
		verifyMatches(match(0, "daqui".length(), span(0, 1, 0), 
				span(1, "daqui".length(), 1)
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

	// De + x
	@Test
	public void testContraction_De_o() throws Exception {
		cg.add("De");
		cg.add("o");
		
		runOver("Do");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_De_a() throws Exception {
		cg.add("De");
		cg.add("a");
		
		runOver("Da");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_De_os() throws Exception {
		cg.add("De");
		cg.add("os");
		
		runOver("Dos");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_De_as() throws Exception {
		cg.add("De");
		cg.add("as");
		
		runOver("Das");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}

	@Test
	public void testContraction_De_este() throws Exception {
		cg.add("De");
		cg.add("este");
		
		runOver("Deste");
		
		verifyMatches(match(0, "Deste".length(), span(0, 1, 0), 
				span(1, "Deste".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_esse() throws Exception {
		cg.add("De");
		cg.add("esse");
		
		runOver("Desse");
		
		verifyMatches(match(0, "Desse".length(), span(0, 1, 0), 
				span(1, "Desse".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_esta() throws Exception {
		cg.add("De");
		cg.add("esta");
		
		runOver("Desta");
		
		verifyMatches(match(0, "Desta".length(), span(0, 1, 0), 
				span(1, "Desta".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_essa() throws Exception {
		cg.add("De");
		cg.add("essa");
		
		runOver("Dessa");
		
		verifyMatches(match(0, "Dessa".length(), span(0, 1, 0), 
				span(1, "Dessa".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_estes() throws Exception {
		cg.add("De");
		cg.add("estes");
		
		runOver("Destes");
		
		verifyMatches(match(0, "Destes".length(), span(0, 1, 0), 
				span(1, "Destes".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_esses() throws Exception {
		cg.add("De");
		cg.add("esses");
		
		runOver("Desses");
		
		verifyMatches(match(0, "Desses".length(), span(0, 1, 0), 
				span(1, "Desses".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_estas() throws Exception {
		cg.add("De");
		cg.add("estas");
		
		runOver("Destas");
		
		verifyMatches(match(0, "Destas".length(), span(0, 1, 0), 
				span(1, "Destas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_essas() throws Exception {
		cg.add("De");
		cg.add("essas");
		
		runOver("Dessas");
		
		verifyMatches(match(0, "Dessas".length(), span(0, 1, 0), 
				span(1, "Dessas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_isto() throws Exception {
		cg.add("De");
		cg.add("isto");
		
		runOver("Disto");
		
		verifyMatches(match(0, "Disto".length(), span(0, 1, 0), 
				span(1, "Disto".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_isso() throws Exception {
		cg.add("De");
		cg.add("isso");
		
		runOver("Disso");
		
		verifyMatches(match(0, "Disso".length(), span(0, 1, 0), 
				span(1, "Disso".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_aquele() throws Exception {
		cg.add("De");
		cg.add("aquele");
		
		runOver("Daquele");
		
		verifyMatches(match(0, "Daquele".length(), span(0, 1, 0), 
				span(1, "Daquele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_aquela() throws Exception {
		cg.add("De");
		cg.add("aquela");
		
		runOver("Daquela");
		
		verifyMatches(match(0, "Daquela".length(), span(0, 1, 0), 
				span(1, "Daquela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_aqueles() throws Exception {
		cg.add("De");
		cg.add("aqueles");
		
		runOver("Daqueles");
		
		verifyMatches(match(0, "Daqueles".length(), span(0, 1, 0), 
				span(1, "Daqueles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_aquelas() throws Exception {
		cg.add("De");
		cg.add("aquelas");
		
		runOver("Daquelas");
		
		verifyMatches(match(0, "Daquelas".length(), span(0, 1, 0), 
				span(1, "Daquelas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_aquilo() throws Exception {
		cg.add("De");
		cg.add("aquilo");
		
		runOver("Daquilo");
		
		verifyMatches(match(0, "Daquilo".length(), span(0, 1, 0), 
				span(1, "Daquilo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_ele() throws Exception {
		cg.add("De");
		cg.add("ele");
		
		runOver("Dele");
		
		verifyMatches(match(0, "Dele".length(), span(0, 1, 0), 
				span(1, "Dele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_ela() throws Exception {
		cg.add("De");
		cg.add("ela");
		
		runOver("Dela");
		
		verifyMatches(match(0, "Dela".length(), span(0, 1, 0), 
				span(1, "Dela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_eles() throws Exception {
		cg.add("De");
		cg.add("eles");
		
		runOver("Deles");
		
		verifyMatches(match(0, "Deles".length(), span(0, 1, 0), 
				span(1, "Deles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_elas() throws Exception {
		cg.add("De");
		cg.add("elas");
		
		runOver("Delas");
		
		verifyMatches(match(0, "Delas".length(), span(0, 1, 0), 
				span(1, "Delas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_entre() throws Exception {
		cg.add("De");
		cg.add("entre");
		
		runOver("Dentre");
		
		verifyMatches(match(0, "Dentre".length(), span(0, 1, 0), 
				span(1, "Dentre".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_aqui() throws Exception {
		cg.add("De");
		cg.add("aqui");
		
		runOver("Daqui");
		
		verifyMatches(match(0, "Daqui".length(), span(0, 1, 0), 
				span(1, "Daqui".length(), 1)
			));
	}
	
	@Test
	public void testContraction_De_aí() throws Exception {
		cg.add("De");
		cg.add("aí");
		
		runOver("Daí");
		
		verifyMatches(match(0, "Daí".length(), span(0, 1, 0), 
				span(1, "Daí".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_Alem_De_isso() throws Exception {
		cg.add("Além=de");
		cg.add("isso");
		
		runOver("Além disso");
		
		verifyMatches(match(0, "Além disso".length(), span(0, "Além d".length(), 0), 
				span("Além d".length(), "Além disso".length(), 1)
			));
	}

	// DE + X
	@Test
	public void testContraction_DE_O() throws Exception {
		cg.add("DE");
		cg.add("O");
		
		runOver("DO");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_DE_A() throws Exception {
		cg.add("DE");
		cg.add("A");
		
		runOver("DA");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_DE_OS() throws Exception {
		cg.add("DE");
		cg.add("OS");
		
		runOver("DOS");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_DE_AS() throws Exception {
		cg.add("DE");
		cg.add("AS");
		
		runOver("DAS");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}

	@Test
	public void testContraction_DE_ESTE() throws Exception {
		cg.add("DE");
		cg.add("ESTE");
		
		runOver("DESTE");
		
		verifyMatches(match(0, "DESTE".length(), span(0, 1, 0), 
				span(1, "DESTE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ESSE() throws Exception {
		cg.add("DE");
		cg.add("ESSE");
		
		runOver("DESSE");
		
		verifyMatches(match(0, "DESSE".length(), span(0, 1, 0), 
				span(1, "DESSE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ESTA() throws Exception {
		cg.add("DE");
		cg.add("ESTA");
		
		runOver("DESTA");
		
		verifyMatches(match(0, "DESTA".length(), span(0, 1, 0), 
				span(1, "DESTA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ESSA() throws Exception {
		cg.add("DE");
		cg.add("ESSA");
		
		runOver("DESSA");
		
		verifyMatches(match(0, "DESSA".length(), span(0, 1, 0), 
				span(1, "DESSA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ESTES() throws Exception {
		cg.add("DE");
		cg.add("ESTES");
		
		runOver("DESTES");
		
		verifyMatches(match(0, "DESTES".length(), span(0, 1, 0), 
				span(1, "DESTES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ESSES() throws Exception {
		cg.add("DE");
		cg.add("ESSES");
		
		runOver("DESSES");
		
		verifyMatches(match(0, "DESSES".length(), span(0, 1, 0), 
				span(1, "DESSES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ESTAS() throws Exception {
		cg.add("DE");
		cg.add("ESTAS");
		
		runOver("DESTAS");
		
		verifyMatches(match(0, "DESTAS".length(), span(0, 1, 0), 
				span(1, "DESTAS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ESSAS() throws Exception {
		cg.add("DE");
		cg.add("ESSAS");
		
		runOver("DESSAS");
		
		verifyMatches(match(0, "DESSAS".length(), span(0, 1, 0), 
				span(1, "DESSAS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ISTO() throws Exception {
		cg.add("DE");
		cg.add("ISTO");
		
		runOver("DISTO");
		
		verifyMatches(match(0, "DISTO".length(), span(0, 1, 0), 
				span(1, "DISTO".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ISSO() throws Exception {
		cg.add("DE");
		cg.add("ISSO");
		
		runOver("DISSO");
		
		verifyMatches(match(0, "DISSO".length(), span(0, 1, 0), 
				span(1, "DISSO".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_AQUELE() throws Exception {
		cg.add("DE");
		cg.add("AQUELE");
		
		runOver("DAQUELE");
		
		verifyMatches(match(0, "DAQUELE".length(), span(0, 1, 0), 
				span(1, "DAQUELE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_AQUELA() throws Exception {
		cg.add("DE");
		cg.add("AQUELA");
		
		runOver("DAQUELA");
		
		verifyMatches(match(0, "DAQUELA".length(), span(0, 1, 0), 
				span(1, "DAQUELA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_AQUELES() throws Exception {
		cg.add("DE");
		cg.add("AQUELES");
		
		runOver("DAQUELES");
		
		verifyMatches(match(0, "DAQUELES".length(), span(0, 1, 0), 
				span(1, "DAQUELES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_AQUELAS() throws Exception {
		cg.add("DE");
		cg.add("AQUELAS");
		
		runOver("DAQUELAS");
		
		verifyMatches(match(0, "DAQUELAS".length(), span(0, 1, 0), 
				span(1, "DAQUELAS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_AQUILO() throws Exception {
		cg.add("DE");
		cg.add("AQUILO");
		
		runOver("DAQUILO");
		
		verifyMatches(match(0, "DAQUILO".length(), span(0, 1, 0), 
				span(1, "DAQUILO".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ELE() throws Exception {
		cg.add("DE");
		cg.add("ELE");
		
		runOver("DELE");
		
		verifyMatches(match(0, "DELE".length(), span(0, 1, 0), 
				span(1, "DELE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ELA() throws Exception {
		cg.add("DE");
		cg.add("ELA");
		
		runOver("DELA");
		
		verifyMatches(match(0, "DELA".length(), span(0, 1, 0), 
				span(1, "DELA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ELES() throws Exception {
		cg.add("DE");
		cg.add("ELES");
		
		runOver("DELES");
		
		verifyMatches(match(0, "DELES".length(), span(0, 1, 0), 
				span(1, "DELES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ELAS() throws Exception {
		cg.add("DE");
		cg.add("ELAS");
		
		runOver("DELAS");
		
		verifyMatches(match(0, "DELAS".length(), span(0, 1, 0), 
				span(1, "DELAS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_ENTRE() throws Exception {
		cg.add("DE");
		cg.add("ENTRE");
		
		runOver("DENTRE");
		
		verifyMatches(match(0, "DENTRE".length(), span(0, 1, 0), 
				span(1, "DENTRE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_AQUI() throws Exception {
		cg.add("DE");
		cg.add("AQUI");
		
		runOver("DAQUI");
		
		verifyMatches(match(0, "DAQUI".length(), span(0, 1, 0), 
				span(1, "DAQUI".length(), 1)
			));
	}
	
	@Test
	public void testContraction_DE_AÍ() throws Exception {
		cg.add("DE");
		cg.add("AÍ");
		
		runOver("DAÍ");
		
		verifyMatches(match(0, "DAÍ".length(), span(0, 1, 0), 
				span(1, "DAÍ".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_ALÉM_DE_ISSO() throws Exception {
		cg.add("ALÉM=DE");
		cg.add("ISSO");
		
		runOver("ALÉM DISSO");
		
		verifyMatches(match(0, "ALÉM DISSO".length(), span(0, "ALÉM D".length(), 0), 
				span("ALÉM D".length(), "ALÉM DISSO".length(), 1)
			));
	}
}
