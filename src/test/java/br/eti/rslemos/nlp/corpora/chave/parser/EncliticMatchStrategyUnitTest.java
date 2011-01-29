package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class EncliticMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public EncliticMatchStrategyUnitTest() {
		super(new EncliticMatchStrategy());
	}
	
	@Test
	public void testInfinitiv_ar_lo() throws Exception {
		cg.add("Convocar-");
		cg.add("lo");
		
		runOver("Convocá-lo");
		
		verifyMatches(match(0, "Convocá-lo".length(), span(0, "Convocá-".length(), 0), 
				span("Convocá-".length(), "Convocá-lo".length(), 1)
			));
	}

	@Test
	public void testInfinitiv_ar_la() throws Exception {
		cg.add("Convocar-");
		cg.add("la");
		
		runOver("Convocá-la");
		
		verifyMatches(match(0, "Convocá-la".length(), span(0, "Convocá-".length(), 0), 
				span("Convocá-".length(), "Convocá-la".length(), 1)
			));
	}
	
	@Test
	public void testInfinitiv_ar_los() throws Exception {
		cg.add("Convocar-");
		cg.add("los");
		
		runOver("Convocá-los");
		
		verifyMatches(match(0, "Convocá-los".length(), span(0, "Convocá-".length(), 0), 
				span("Convocá-".length(), "Convocá-los".length(), 1)
			));
	}

	@Test
	public void testInfinitiv_ar_las() throws Exception {
		cg.add("Convocar-");
		cg.add("las");
		
		runOver("Convocá-las");
		
		verifyMatches(match(0, "Convocá-las".length(), span(0, "Convocá-".length(), 0), 
				span("Convocá-".length(), "Convocá-las".length(), 1)
			));
	}
	
	@Test
	public void testInfinitiv_er_lo() throws Exception {
		cg.add("fazer-");
		cg.add("lo");
		
		runOver("fazê-lo");
		
		verifyMatches(match(0, "fazê-lo".length(), span(0, "fazê-".length(), 0), 
				span("fazê-".length(), "fazê-lo".length(), 1)
			));
	}

	@Test
	public void testInfinitiv_er_la() throws Exception {
		cg.add("fazer-");
		cg.add("la");
		
		runOver("fazê-la");
		
		verifyMatches(match(0, "fazê-la".length(), span(0, "fazê-".length(), 0), 
				span("fazê-".length(), "fazê-la".length(), 1)
			));
	}
	
	@Test
	public void testInfinitiv_er_los() throws Exception {
		cg.add("fazer-");
		cg.add("los");
		
		runOver("fazê-los");
		
		verifyMatches(match(0, "fazê-los".length(), span(0, "fazê-".length(), 0), 
				span("fazê-".length(), "fazê-los".length(), 1)
			));
	}

	@Test
	public void testInfinitiv_er_las() throws Exception {
		cg.add("fazer-");
		cg.add("las");
		
		runOver("fazê-las");
		
		verifyMatches(match(0, "fazê-las".length(), span(0, "fazê-".length(), 0), 
				span("fazê-".length(), "fazê-las".length(), 1)
			));
	}
	
	@Test
	public void testInfinitiv_ir_lo() throws Exception {
		cg.add("assistir-");
		cg.add("lo");
		
		runOver("assisti-lo");
		
		verifyMatches(match(0, "assisti-lo".length(), span(0, "assisti-".length(), 0), 
				span("assisti-".length(), "assisti-lo".length(), 1)
			));
	}

	@Test
	public void testInfinitiv_ir_la() throws Exception {
		cg.add("assistir-");
		cg.add("la");
		
		runOver("assisti-la");
		
		verifyMatches(match(0, "assisti-la".length(), span(0, "assisti-".length(), 0), 
				span("assisti-".length(), "assisti-la".length(), 1)
			));
	}
	
	@Test
	public void testInfinitiv_ir_los() throws Exception {
		cg.add("assistir-");
		cg.add("los");
		
		runOver("assisti-los");
		
		verifyMatches(match(0, "assisti-los".length(), span(0, "assisti-".length(), 0), 
				span("assisti-".length(), "assisti-los".length(), 1)
			));
	}

	@Test
	public void testInfinitiv_ir_las() throws Exception {
		cg.add("assistir-");
		cg.add("las");
		
		runOver("assisti-las");
		
		verifyMatches(match(0, "assisti-las".length(), span(0, "assisti-".length(), 0), 
				span("assisti-".length(), "assisti-las".length(), 1)
			));
	}
	
	@Test
	public void testInfinitiv_por_lo() throws Exception {
		cg.add("assistir-");
		cg.add("lo");
		
		runOver("assisti-lo");
		
		verifyMatches(match(0, "assisti-lo".length(), span(0, "assisti-".length(), 0), 
				span("assisti-".length(), "assisti-lo".length(), 1)
			));
	}

	@Test
	public void testInfinitiv_por_la() throws Exception {
		cg.add("assistir-");
		cg.add("la");
		
		runOver("assisti-la");
		
		verifyMatches(match(0, "assisti-la".length(), span(0, "assisti-".length(), 0), 
				span("assisti-".length(), "assisti-la".length(), 1)
			));
	}
	
	@Test
	public void testInfinitiv_por_los() throws Exception {
		cg.add("assistir-");
		cg.add("los");
		
		runOver("assisti-los");
		
		verifyMatches(match(0, "assisti-los".length(), span(0, "assisti-".length(), 0), 
				span("assisti-".length(), "assisti-los".length(), 1)
			));
	}

	@Test
	public void testInfinitiv_por_las() throws Exception {
		cg.add("assistir-");
		cg.add("las");
		
		runOver("assisti-las");
		
		verifyMatches(
				match(0, "assisti-las".length(),
						span(0, "assisti-".length(), 0), 
						span("assisti-".length(), "assisti-las".length(), 1)
					)
			);
	}
	
	@Test
	public void testManyMatchesOfSameEnclitic() throws Exception {
		cg.add("assistir-");
		cg.add("las");
		
		runOver("assisti-las assisti-las");
		
		verifyMatches(
				match(0, "assisti-las".length(),
						span(0, "assisti-".length(), 0), 
						span("assisti-".length(), "assisti-las".length(), 1)
					),
				match("assisti-las ".length(), "assisti-las assisti-las".length(),
						span("assisti-las ".length(), "assisti-las assisti-".length(), 0), 
						span("assisti-las assisti-".length(), "assisti-las assisti-las".length(), 1)
					)
			);
	}
}
