package br.eti.rslemos.nlp.corpora.chave.parser;

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
		
		Match result = match("Convocá-lo");
		
		verifyMatch(result, 0, "Convocá-lo".length(), 
				span(0, "Convocá-".length(), 0),
				span("Convocá-".length(), "Convocá-lo".length(), 1)
			);
	}

	@Test
	public void testInfinitiv_ar_la() throws Exception {
		cg.add("Convocar-");
		cg.add("la");
		
		Match result = match("Convocá-la");
		
		verifyMatch(result, 0, "Convocá-la".length(), 
				span(0, "Convocá-".length(), 0),
				span("Convocá-".length(), "Convocá-la".length(), 1)
			);
	}
	
	@Test
	public void testInfinitiv_ar_los() throws Exception {
		cg.add("Convocar-");
		cg.add("los");
		
		Match result = match("Convocá-los");
		
		verifyMatch(result, 0, "Convocá-los".length(), 
				span(0, "Convocá-".length(), 0),
				span("Convocá-".length(), "Convocá-los".length(), 1)
			);
	}

	@Test
	public void testInfinitiv_ar_las() throws Exception {
		cg.add("Convocar-");
		cg.add("las");
		
		Match result = match("Convocá-las");
		
		verifyMatch(result, 0, "Convocá-las".length(), 
				span(0, "Convocá-".length(), 0),
				span("Convocá-".length(), "Convocá-las".length(), 1)
			);
	}
	
	@Test
	public void testInfinitiv_er_lo() throws Exception {
		cg.add("fazer-");
		cg.add("lo");
		
		Match result = match("fazê-lo");
		
		verifyMatch(result, 0, "fazê-lo".length(), 
				span(0, "fazê-".length(), 0),
				span("fazê-".length(), "fazê-lo".length(), 1)
			);
	}

	@Test
	public void testInfinitiv_er_la() throws Exception {
		cg.add("fazer-");
		cg.add("la");
		
		Match result = match("fazê-la");
		
		verifyMatch(result, 0, "fazê-la".length(), 
				span(0, "fazê-".length(), 0),
				span("fazê-".length(), "fazê-la".length(), 1)
			);
	}
	
	@Test
	public void testInfinitiv_er_los() throws Exception {
		cg.add("fazer-");
		cg.add("los");
		
		Match result = match("fazê-los");
		
		verifyMatch(result, 0, "fazê-los".length(), 
				span(0, "fazê-".length(), 0),
				span("fazê-".length(), "fazê-los".length(), 1)
			);
	}

	@Test
	public void testInfinitiv_er_las() throws Exception {
		cg.add("fazer-");
		cg.add("las");
		
		Match result = match("fazê-las");
		
		verifyMatch(result, 0, "fazê-las".length(), 
				span(0, "fazê-".length(), 0),
				span("fazê-".length(), "fazê-las".length(), 1)
			);
	}
	
	@Test
	public void testInfinitiv_ir_lo() throws Exception {
		cg.add("assistir-");
		cg.add("lo");
		
		Match result = match("assisti-lo");
		
		verifyMatch(result, 0, "assisti-lo".length(), 
				span(0, "assisti-".length(), 0),
				span("assisti-".length(), "assisti-lo".length(), 1)
			);
	}

	@Test
	public void testInfinitiv_ir_la() throws Exception {
		cg.add("assistir-");
		cg.add("la");
		
		Match result = match("assisti-la");
		
		verifyMatch(result, 0, "assisti-la".length(), 
				span(0, "assisti-".length(), 0),
				span("assisti-".length(), "assisti-la".length(), 1)
			);
	}
	
	@Test
	public void testInfinitiv_ir_los() throws Exception {
		cg.add("assistir-");
		cg.add("los");
		
		Match result = match("assisti-los");
		
		verifyMatch(result, 0, "assisti-los".length(), 
				span(0, "assisti-".length(), 0),
				span("assisti-".length(), "assisti-los".length(), 1)
			);
	}

	@Test
	public void testInfinitiv_ir_las() throws Exception {
		cg.add("assistir-");
		cg.add("las");
		
		Match result = match("assisti-las");
		
		verifyMatch(result, 0, "assisti-las".length(), 
				span(0, "assisti-".length(), 0),
				span("assisti-".length(), "assisti-las".length(), 1)
			);
	}
	
	@Test
	public void testInfinitiv_por_lo() throws Exception {
		cg.add("assistir-");
		cg.add("lo");
		
		Match result = match("assisti-lo");
		
		verifyMatch(result, 0, "assisti-lo".length(), 
				span(0, "assisti-".length(), 0),
				span("assisti-".length(), "assisti-lo".length(), 1)
			);
	}

	@Test
	public void testInfinitiv_por_la() throws Exception {
		cg.add("assistir-");
		cg.add("la");
		
		Match result = match("assisti-la");
		
		verifyMatch(result, 0, "assisti-la".length(), 
				span(0, "assisti-".length(), 0),
				span("assisti-".length(), "assisti-la".length(), 1)
			);
	}
	
	@Test
	public void testInfinitiv_por_los() throws Exception {
		cg.add("assistir-");
		cg.add("los");
		
		Match result = match("assisti-los");
		
		verifyMatch(result, 0, "assisti-los".length(), 
				span(0, "assisti-".length(), 0),
				span("assisti-".length(), "assisti-los".length(), 1)
			);
	}

	@Test
	public void testInfinitiv_por_las() throws Exception {
		cg.add("assistir-");
		cg.add("las");
		
		Match result = match("assisti-las");
		
		verifyMatch(result, 0, "assisti-las".length(), 
				span(0, "assisti-".length(), 0),
				span("assisti-".length(), "assisti-las".length(), 1)
			);
	}
}
