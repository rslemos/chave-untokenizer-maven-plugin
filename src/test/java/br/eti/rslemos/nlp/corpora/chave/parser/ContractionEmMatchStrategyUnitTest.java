package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class ContractionEmMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionEmMatchStrategyUnitTest() {
		super(new ContractionEmMatchStrategy());
	}
	
	@Test
	public void testContraction_em_o() throws Exception {
		cg.add("em");
		cg.add("o");
		
		Match result = runOver("no");
		
		verifyMatch(result, 0, 2, 
				span(0, 1, 0),
				span(1, 2, 1)
			);
	}
	
	@Test
	public void testContraction_em_a() throws Exception {
		cg.add("em");
		cg.add("a");
		
		Match result = runOver("na");
		
		verifyMatch(result, 0, 2, 
				span(0, 1, 0),
				span(1, 2, 1)
			);
	}
	
	@Test
	public void testContraction_em_os() throws Exception {
		cg.add("em");
		cg.add("os");
		
		Match result = runOver("nos");
		
		verifyMatch(result, 0, 3, 
				span(0, 1, 0),
				span(1, 3, 1)
			);
	}
	
	@Test
	public void testContraction_em_as() throws Exception {
		cg.add("em");
		cg.add("as");
		
		Match result = runOver("nas");
		
		verifyMatch(result, 0, 3, 
				span(0, 1, 0),
				span(1, 3, 1)
			);
	}

	@Test
	public void testContraction_em_este() throws Exception {
		cg.add("em");
		cg.add("este");
		
		Match result = runOver("neste");
		
		verifyMatch(result, 0, "neste".length(), 
				span(0, 1, 0),
				span(1, "neste".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_esse() throws Exception {
		cg.add("em");
		cg.add("esse");
		
		Match result = runOver("nesse");
		
		verifyMatch(result, 0, "nesse".length(), 
				span(0, 1, 0),
				span(1, "nesse".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_esta() throws Exception {
		cg.add("em");
		cg.add("esta");
		
		Match result = runOver("nesta");
		
		verifyMatch(result, 0, "nesta".length(), 
				span(0, 1, 0),
				span(1, "nesta".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_essa() throws Exception {
		cg.add("em");
		cg.add("essa");
		
		Match result = runOver("nessa");
		
		verifyMatch(result, 0, "nessa".length(), 
				span(0, 1, 0),
				span(1, "nessa".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_estes() throws Exception {
		cg.add("em");
		cg.add("estes");
		
		Match result = runOver("nestes");
		
		verifyMatch(result, 0, "nestes".length(), 
				span(0, 1, 0),
				span(1, "nestes".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_esses() throws Exception {
		cg.add("em");
		cg.add("esses");
		
		Match result = runOver("nesses");
		
		verifyMatch(result, 0, "nesses".length(), 
				span(0, 1, 0),
				span(1, "nesses".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_estas() throws Exception {
		cg.add("em");
		cg.add("estas");
		
		Match result = runOver("nestas");
		
		verifyMatch(result, 0, "nestas".length(), 
				span(0, 1, 0),
				span(1, "nestas".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_essas() throws Exception {
		cg.add("em");
		cg.add("essas");
		
		Match result = runOver("nessas");
		
		verifyMatch(result, 0, "nessas".length(), 
				span(0, 1, 0),
				span(1, "nessas".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_isto() throws Exception {
		cg.add("em");
		cg.add("isto");
		
		Match result = runOver("nisto");
		
		verifyMatch(result, 0, "nisto".length(), 
				span(0, 1, 0),
				span(1, "nisto".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_isso() throws Exception {
		cg.add("em");
		cg.add("isso");
		
		Match result = runOver("nisso");
		
		verifyMatch(result, 0, "nisso".length(), 
				span(0, 1, 0),
				span(1, "nisso".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_aquele() throws Exception {
		cg.add("em");
		cg.add("aquele");
		
		Match result = runOver("naquele");
		
		verifyMatch(result, 0, "naquele".length(), 
				span(0, 1, 0),
				span(1, "naquele".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_aquela() throws Exception {
		cg.add("em");
		cg.add("aquela");
		
		Match result = runOver("naquela");
		
		verifyMatch(result, 0, "naquela".length(), 
				span(0, 1, 0),
				span(1, "naquela".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_aqueles() throws Exception {
		cg.add("em");
		cg.add("aqueles");
		
		Match result = runOver("naqueles");
		
		verifyMatch(result, 0, "naqueles".length(), 
				span(0, 1, 0),
				span(1, "naqueles".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_aquelas() throws Exception {
		cg.add("em");
		cg.add("aquelas");
		
		Match result = runOver("naquelas");
		
		verifyMatch(result, 0, "naquelas".length(), 
				span(0, 1, 0),
				span(1, "naquelas".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_aquilo() throws Exception {
		cg.add("em");
		cg.add("aquilo");
		
		Match result = runOver("naquilo");
		
		verifyMatch(result, 0, "naquilo".length(), 
				span(0, 1, 0),
				span(1, "naquilo".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_ele() throws Exception {
		cg.add("em");
		cg.add("ele");
		
		Match result = runOver("nele");
		
		verifyMatch(result, 0, "nele".length(), 
				span(0, 1, 0),
				span(1, "nele".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_ela() throws Exception {
		cg.add("em");
		cg.add("ela");
		
		Match result = runOver("nela");
		
		verifyMatch(result, 0, "nela".length(), 
				span(0, 1, 0),
				span(1, "nela".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_eles() throws Exception {
		cg.add("em");
		cg.add("eles");
		
		Match result = runOver("neles");
		
		verifyMatch(result, 0, "neles".length(), 
				span(0, 1, 0),
				span(1, "neles".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_elas() throws Exception {
		cg.add("em");
		cg.add("elas");
		
		Match result = runOver("nelas");
		
		verifyMatch(result, 0, "nelas".length(), 
				span(0, 1, 0),
				span(1, "nelas".length(), 1)
			);
	}
	
	@Test
	public void testContraction_em_um() throws Exception {
		cg.add("em");
		cg.add("um");
		
		Match result = runOver("num");
		
		verifyMatch(result, 0, 3, 
				span(0, 1, 0),
				span(1, 3, 1)
			);
	}
	
	@Test
	public void testContraction_em_uma() throws Exception {
		cg.add("em");
		cg.add("uma");
		
		Match result = runOver("numa");
		
		verifyMatch(result, 0, 4, 
				span(0, 1, 0),
				span(1, 4, 1)
			);
	}
	
	@Test
	public void testContraction_em_uns() throws Exception {
		cg.add("em");
		cg.add("uns");
		
		Match result = runOver("nuns");
		
		verifyMatch(result, 0, 4, 
				span(0, 1, 0),
				span(1, 4, 1)
			);
	}
	
	@Test
	public void testContraction_em_umas() throws Exception {
		cg.add("em");
		cg.add("umas");
		
		Match result = runOver("numas");
		
		verifyMatch(result, 0, 5, 
				span(0, 1, 0),
				span(1, 5, 1)
			);
	}
	
	@Test
	public void testExpressionContraction_em_o_qual() throws Exception {
		cg.add("em");
		cg.add("o=qual");
		
		Match result = runOver("no qual");
		
		verifyMatch(result, 0, "no qual".length(), 
				span(0, 1, 0),
				span(1, "no qual".length(), 1)
			);
		
	}
}
