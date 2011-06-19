package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class ContractionEmMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionEmMatchStrategyUnitTest() {
		super(new ContractionEmMatchStrategy());
	}

	// em + x
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

	// Em + x
	@Test
	public void testContraction_Em_o() throws Exception {
		cg.add("Em");
		cg.add("o");
		
		runOver("No");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_Em_a() throws Exception {
		cg.add("Em");
		cg.add("a");
		
		runOver("Na");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_Em_os() throws Exception {
		cg.add("Em");
		cg.add("os");
		
		runOver("Nos");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_Em_as() throws Exception {
		cg.add("Em");
		cg.add("as");
		
		runOver("Nas");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}

	@Test
	public void testContraction_Em_este() throws Exception {
		cg.add("Em");
		cg.add("este");
		
		runOver("Neste");
		
		verifyMatches(match(0, "Neste".length(), span(0, 1, 0), 
				span(1, "Neste".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_esse() throws Exception {
		cg.add("Em");
		cg.add("esse");
		
		runOver("Nesse");
		
		verifyMatches(match(0, "Nesse".length(), span(0, 1, 0), 
				span(1, "Nesse".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_esta() throws Exception {
		cg.add("Em");
		cg.add("esta");
		
		runOver("Nesta");
		
		verifyMatches(match(0, "Nesta".length(), span(0, 1, 0), 
				span(1, "Nesta".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_essa() throws Exception {
		cg.add("Em");
		cg.add("essa");
		
		runOver("Nessa");
		
		verifyMatches(match(0, "Nessa".length(), span(0, 1, 0), 
				span(1, "Nessa".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_estes() throws Exception {
		cg.add("Em");
		cg.add("estes");
		
		runOver("Nestes");
		
		verifyMatches(match(0, "Nestes".length(), span(0, 1, 0), 
				span(1, "Nestes".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_esses() throws Exception {
		cg.add("Em");
		cg.add("esses");
		
		runOver("Nesses");
		
		verifyMatches(match(0, "Nesses".length(), span(0, 1, 0), 
				span(1, "Nesses".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_estas() throws Exception {
		cg.add("Em");
		cg.add("estas");
		
		runOver("Nestas");
		
		verifyMatches(match(0, "Nestas".length(), span(0, 1, 0), 
				span(1, "Nestas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_essas() throws Exception {
		cg.add("Em");
		cg.add("essas");
		
		runOver("Nessas");
		
		verifyMatches(match(0, "Nessas".length(), span(0, 1, 0), 
				span(1, "Nessas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_isto() throws Exception {
		cg.add("Em");
		cg.add("isto");
		
		runOver("Nisto");
		
		verifyMatches(match(0, "Nisto".length(), span(0, 1, 0), 
				span(1, "Nisto".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_isso() throws Exception {
		cg.add("Em");
		cg.add("isso");
		
		runOver("Nisso");
		
		verifyMatches(match(0, "Nisso".length(), span(0, 1, 0), 
				span(1, "Nisso".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_aquele() throws Exception {
		cg.add("Em");
		cg.add("aquele");
		
		runOver("Naquele");
		
		verifyMatches(match(0, "Naquele".length(), span(0, 1, 0), 
				span(1, "Naquele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_aquela() throws Exception {
		cg.add("Em");
		cg.add("aquela");
		
		runOver("Naquela");
		
		verifyMatches(match(0, "Naquela".length(), span(0, 1, 0), 
				span(1, "Naquela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_aqueles() throws Exception {
		cg.add("Em");
		cg.add("aqueles");
		
		runOver("Naqueles");
		
		verifyMatches(match(0, "Naqueles".length(), span(0, 1, 0), 
				span(1, "Naqueles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_aquelas() throws Exception {
		cg.add("Em");
		cg.add("aquelas");
		
		runOver("Naquelas");
		
		verifyMatches(match(0, "Naquelas".length(), span(0, 1, 0), 
				span(1, "Naquelas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_aquilo() throws Exception {
		cg.add("Em");
		cg.add("aquilo");
		
		runOver("Naquilo");
		
		verifyMatches(match(0, "Naquilo".length(), span(0, 1, 0), 
				span(1, "Naquilo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_ele() throws Exception {
		cg.add("Em");
		cg.add("ele");
		
		runOver("Nele");
		
		verifyMatches(match(0, "Nele".length(), span(0, 1, 0), 
				span(1, "Nele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_ela() throws Exception {
		cg.add("Em");
		cg.add("ela");
		
		runOver("Nela");
		
		verifyMatches(match(0, "Nela".length(), span(0, 1, 0), 
				span(1, "Nela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_eles() throws Exception {
		cg.add("Em");
		cg.add("eles");
		
		runOver("Neles");
		
		verifyMatches(match(0, "Neles".length(), span(0, 1, 0), 
				span(1, "Neles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_elas() throws Exception {
		cg.add("Em");
		cg.add("elas");
		
		runOver("Nelas");
		
		verifyMatches(match(0, "Nelas".length(), span(0, 1, 0), 
				span(1, "Nelas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Em_um() throws Exception {
		cg.add("Em");
		cg.add("um");
		
		runOver("Num");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_Em_uma() throws Exception {
		cg.add("Em");
		cg.add("uma");
		
		runOver("Numa");
		
		verifyMatches(match(0, 4, span(0, 1, 0), 
				span(1, 4, 1)
			));
	}
	
	@Test
	public void testContraction_Em_uns() throws Exception {
		cg.add("Em");
		cg.add("uns");
		
		runOver("Nuns");
		
		verifyMatches(match(0, 4, span(0, 1, 0), 
				span(1, 4, 1)
			));
	}
	
	@Test
	public void testContraction_Em_umas() throws Exception {
		cg.add("Em");
		cg.add("umas");
		
		runOver("Numas");
		
		verifyMatches(match(0, 5, span(0, 1, 0), 
				span(1, 5, 1)
			));
	}
	
	@Test
	public void testExpressionContraction_Em_o_qual() throws Exception {
		cg.add("Em");
		cg.add("o=qual");
		
		runOver("No qual");
		
		verifyMatches(match(0, "No qual".length(), span(0, 1, 0), 
				span(1, "No qual".length(), 1)
			));
		
	}

	// EM + X
	@Test
	public void testContraction_EM_O() throws Exception {
		cg.add("EM");
		cg.add("O");
		
		runOver("NO");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_EM_A() throws Exception {
		cg.add("EM");
		cg.add("A");
		
		runOver("NA");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_EM_OS() throws Exception {
		cg.add("EM");
		cg.add("OS");
		
		runOver("NOS");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_EM_AS() throws Exception {
		cg.add("EM");
		cg.add("AS");
		
		runOver("NAS");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}

	@Test
	public void testContraction_EM_ESTE() throws Exception {
		cg.add("EM");
		cg.add("ESTE");
		
		runOver("NESTE");
		
		verifyMatches(match(0, "NESTE".length(), span(0, 1, 0), 
				span(1, "NESTE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ESSE() throws Exception {
		cg.add("EM");
		cg.add("ESSE");
		
		runOver("NESSE");
		
		verifyMatches(match(0, "NESSE".length(), span(0, 1, 0), 
				span(1, "NESSE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ESTA() throws Exception {
		cg.add("EM");
		cg.add("ESTA");
		
		runOver("NESTA");
		
		verifyMatches(match(0, "NESTA".length(), span(0, 1, 0), 
				span(1, "NESTA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ESSA() throws Exception {
		cg.add("EM");
		cg.add("ESSA");
		
		runOver("NESSA");
		
		verifyMatches(match(0, "NESSA".length(), span(0, 1, 0), 
				span(1, "NESSA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ESTES() throws Exception {
		cg.add("EM");
		cg.add("ESTES");
		
		runOver("NESTES");
		
		verifyMatches(match(0, "NESTES".length(), span(0, 1, 0), 
				span(1, "NESTES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ESSES() throws Exception {
		cg.add("EM");
		cg.add("ESSES");
		
		runOver("NESSES");
		
		verifyMatches(match(0, "NESSES".length(), span(0, 1, 0), 
				span(1, "NESSES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ESTAS() throws Exception {
		cg.add("EM");
		cg.add("ESTAS");
		
		runOver("NESTAS");
		
		verifyMatches(match(0, "NESTAS".length(), span(0, 1, 0), 
				span(1, "NESTAS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ESSAS() throws Exception {
		cg.add("EM");
		cg.add("ESSAS");
		
		runOver("NESSAS");
		
		verifyMatches(match(0, "NESSAS".length(), span(0, 1, 0), 
				span(1, "NESSAS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ISTO() throws Exception {
		cg.add("EM");
		cg.add("ISTO");
		
		runOver("NISTO");
		
		verifyMatches(match(0, "NISTO".length(), span(0, 1, 0), 
				span(1, "NISTO".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ISSO() throws Exception {
		cg.add("EM");
		cg.add("ISSO");
		
		runOver("NISSO");
		
		verifyMatches(match(0, "NISSO".length(), span(0, 1, 0), 
				span(1, "NISSO".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_AQUELE() throws Exception {
		cg.add("EM");
		cg.add("AQUELE");
		
		runOver("NAQUELE");
		
		verifyMatches(match(0, "NAQUELE".length(), span(0, 1, 0), 
				span(1, "NAQUELE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_AQUELA() throws Exception {
		cg.add("EM");
		cg.add("AQUELA");
		
		runOver("NAQUELA");
		
		verifyMatches(match(0, "NAQUELA".length(), span(0, 1, 0), 
				span(1, "NAQUELA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_AQUELES() throws Exception {
		cg.add("EM");
		cg.add("AQUELES");
		
		runOver("NAQUELES");
		
		verifyMatches(match(0, "NAQUELES".length(), span(0, 1, 0), 
				span(1, "NAQUELES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_AQUELAS() throws Exception {
		cg.add("EM");
		cg.add("AQUELAS");
		
		runOver("NAQUELAS");
		
		verifyMatches(match(0, "NAQUELAS".length(), span(0, 1, 0), 
				span(1, "NAQUELAS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_AQUILO() throws Exception {
		cg.add("EM");
		cg.add("AQUILO");
		
		runOver("NAQUILO");
		
		verifyMatches(match(0, "NAQUILO".length(), span(0, 1, 0), 
				span(1, "NAQUILO".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ELE() throws Exception {
		cg.add("EM");
		cg.add("ELE");
		
		runOver("NELE");
		
		verifyMatches(match(0, "NELE".length(), span(0, 1, 0), 
				span(1, "NELE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ELA() throws Exception {
		cg.add("EM");
		cg.add("ELA");
		
		runOver("NELA");
		
		verifyMatches(match(0, "NELA".length(), span(0, 1, 0), 
				span(1, "NELA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ELES() throws Exception {
		cg.add("EM");
		cg.add("ELES");
		
		runOver("NELES");
		
		verifyMatches(match(0, "NELES".length(), span(0, 1, 0), 
				span(1, "NELES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_ELAS() throws Exception {
		cg.add("EM");
		cg.add("ELAS");
		
		runOver("NELAS");
		
		verifyMatches(match(0, "NELAS".length(), span(0, 1, 0), 
				span(1, "NELAS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_EM_UM() throws Exception {
		cg.add("EM");
		cg.add("UM");
		
		runOver("NUM");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_EM_UMA() throws Exception {
		cg.add("EM");
		cg.add("UMA");
		
		runOver("NUMA");
		
		verifyMatches(match(0, 4, span(0, 1, 0), 
				span(1, 4, 1)
			));
	}
	
	@Test
	public void testContraction_EM_UNS() throws Exception {
		cg.add("EM");
		cg.add("UNS");
		
		runOver("NUNS");
		
		verifyMatches(match(0, 4, span(0, 1, 0), 
				span(1, 4, 1)
			));
	}
	
	@Test
	public void testContraction_EM_UMAS() throws Exception {
		cg.add("EM");
		cg.add("UMAS");
		
		runOver("NUMAS");
		
		verifyMatches(match(0, 5, span(0, 1, 0), 
				span(1, 5, 1)
			));
	}
	
	@Test
	public void testExpressionContraction_EM_o_qual() throws Exception {
		cg.add("EM");
		cg.add("O=QUAL");
		
		runOver("NO QUAL");
		
		verifyMatches(match(0, "NO QUAL".length(), span(0, 1, 0), 
				span(1, "NO QUAL".length(), 1)
			));
		
	}
}
