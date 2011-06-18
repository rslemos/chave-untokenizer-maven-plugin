package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class ContractionEmMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionEmMatchStrategyUnitTest() {
		super(new ContractionEmMatchStrategy());
	}
	
	@Test
	public void testContraction_em_o() throws Exception {
		cg.add("em");
		cg.add("o");
		
		runOver("no");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_em_a() throws Exception {
		cg.add("em");
		cg.add("a");
		
		runOver("na");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_em_os() throws Exception {
		cg.add("em");
		cg.add("os");
		
		runOver("nos");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_em_as() throws Exception {
		cg.add("em");
		cg.add("as");
		
		runOver("nas");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}

	@Test
	public void testContraction_em_este() throws Exception {
		cg.add("em");
		cg.add("este");
		
		runOver("neste");
		
		verifyMatches(match(0, "neste".length(), span(0, 1, 0), 
				span(1, "neste".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_esse() throws Exception {
		cg.add("em");
		cg.add("esse");
		
		runOver("nesse");
		
		verifyMatches(match(0, "nesse".length(), span(0, 1, 0), 
				span(1, "nesse".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_esta() throws Exception {
		cg.add("em");
		cg.add("esta");
		
		runOver("nesta");
		
		verifyMatches(match(0, "nesta".length(), span(0, 1, 0), 
				span(1, "nesta".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_essa() throws Exception {
		cg.add("em");
		cg.add("essa");
		
		runOver("nessa");
		
		verifyMatches(match(0, "nessa".length(), span(0, 1, 0), 
				span(1, "nessa".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_estes() throws Exception {
		cg.add("em");
		cg.add("estes");
		
		runOver("nestes");
		
		verifyMatches(match(0, "nestes".length(), span(0, 1, 0), 
				span(1, "nestes".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_esses() throws Exception {
		cg.add("em");
		cg.add("esses");
		
		runOver("nesses");
		
		verifyMatches(match(0, "nesses".length(), span(0, 1, 0), 
				span(1, "nesses".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_estas() throws Exception {
		cg.add("em");
		cg.add("estas");
		
		runOver("nestas");
		
		verifyMatches(match(0, "nestas".length(), span(0, 1, 0), 
				span(1, "nestas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_essas() throws Exception {
		cg.add("em");
		cg.add("essas");
		
		runOver("nessas");
		
		verifyMatches(match(0, "nessas".length(), span(0, 1, 0), 
				span(1, "nessas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_isto() throws Exception {
		cg.add("em");
		cg.add("isto");
		
		runOver("nisto");
		
		verifyMatches(match(0, "nisto".length(), span(0, 1, 0), 
				span(1, "nisto".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_isso() throws Exception {
		cg.add("em");
		cg.add("isso");
		
		runOver("nisso");
		
		verifyMatches(match(0, "nisso".length(), span(0, 1, 0), 
				span(1, "nisso".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_aquele() throws Exception {
		cg.add("em");
		cg.add("aquele");
		
		runOver("naquele");
		
		verifyMatches(match(0, "naquele".length(), span(0, 1, 0), 
				span(1, "naquele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_aquela() throws Exception {
		cg.add("em");
		cg.add("aquela");
		
		runOver("naquela");
		
		verifyMatches(match(0, "naquela".length(), span(0, 1, 0), 
				span(1, "naquela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_aqueles() throws Exception {
		cg.add("em");
		cg.add("aqueles");
		
		runOver("naqueles");
		
		verifyMatches(match(0, "naqueles".length(), span(0, 1, 0), 
				span(1, "naqueles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_aquelas() throws Exception {
		cg.add("em");
		cg.add("aquelas");
		
		runOver("naquelas");
		
		verifyMatches(match(0, "naquelas".length(), span(0, 1, 0), 
				span(1, "naquelas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_aquilo() throws Exception {
		cg.add("em");
		cg.add("aquilo");
		
		runOver("naquilo");
		
		verifyMatches(match(0, "naquilo".length(), span(0, 1, 0), 
				span(1, "naquilo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_ele() throws Exception {
		cg.add("em");
		cg.add("ele");
		
		runOver("nele");
		
		verifyMatches(match(0, "nele".length(), span(0, 1, 0), 
				span(1, "nele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_ela() throws Exception {
		cg.add("em");
		cg.add("ela");
		
		runOver("nela");
		
		verifyMatches(match(0, "nela".length(), span(0, 1, 0), 
				span(1, "nela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_eles() throws Exception {
		cg.add("em");
		cg.add("eles");
		
		runOver("neles");
		
		verifyMatches(match(0, "neles".length(), span(0, 1, 0), 
				span(1, "neles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_elas() throws Exception {
		cg.add("em");
		cg.add("elas");
		
		runOver("nelas");
		
		verifyMatches(match(0, "nelas".length(), span(0, 1, 0), 
				span(1, "nelas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_em_um() throws Exception {
		cg.add("em");
		cg.add("um");
		
		runOver("num");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_em_uma() throws Exception {
		cg.add("em");
		cg.add("uma");
		
		runOver("numa");
		
		verifyMatches(match(0, 4, span(0, 1, 0), 
				span(1, 4, 1)
			));
	}
	
	@Test
	public void testContraction_em_uns() throws Exception {
		cg.add("em");
		cg.add("uns");
		
		runOver("nuns");
		
		verifyMatches(match(0, 4, span(0, 1, 0), 
				span(1, 4, 1)
			));
	}
	
	@Test
	public void testContraction_em_umas() throws Exception {
		cg.add("em");
		cg.add("umas");
		
		runOver("numas");
		
		verifyMatches(match(0, 5, span(0, 1, 0), 
				span(1, 5, 1)
			));
	}
	
	@Test
	public void testExpressionContraction_em_o_qual() throws Exception {
		cg.add("em");
		cg.add("o=qual");
		
		runOver("no qual");
		
		verifyMatches(match(0, "no qual".length(), span(0, 1, 0), 
				span(1, "no qual".length(), 1)
			));
		
	}
}
