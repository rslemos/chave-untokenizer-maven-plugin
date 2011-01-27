package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;

import org.junit.Test;

public class ContractionAMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public ContractionAMatchStrategyUnitTest() {
		super(new ContractionAMatchStrategy());
	}
	
	@Test
	public void testContraction_a_o() throws Exception {
		cg.add("a");
		cg.add("o");
		
		Match result = match("ao");
		
		verifyMatch(result, 0, 2, 
				span(0, 1, 0),
				span(1, 2, 1)
			);
	}
	
	@Test
	public void testContraction_a_a() throws Exception {
		cg.add("a");
		cg.add("a");
		
		Match result = match("à");
		
		verifyMatch(result, 0, 1, 
				span(0, 1, 0), 
				span(0, 1, 1)
			);
	}
	
	@Test
	public void testContraction_a_os() throws Exception {
		cg.add("a");
		cg.add("os");
		
		Match result = match("aos");
		
		verifyMatch(result, 0, 3, 
				span(0, 1, 0),
				span(1, 3, 1)
			);
	}
	
	@Test
	public void testContraction_a_as() throws Exception {
		cg.add("a");
		cg.add("as");
		
		Match result = match("às");
		
		verifyMatch(result, 0, 2,
				span(0, 1, 0),
				span(0, 2, 1)
			);
	}
	
	@Test
	public void testContraction_a_aquele() throws Exception {
		cg.add("a");
		cg.add("aquele");
		
		Match result = match("àquele");
		
		verifyMatch(result, 0, "àquele".length(),
				span(0, 1, 0),
				span(0, "àquele".length(), 1)
			);
	}
	
	@Test
	public void testContraction_a_aquela() throws Exception {
		cg.add("a");
		cg.add("aquela");
		
		Match result = match("àquela");
		
		verifyMatch(result, 0, "àquela".length(),
				span(0, 1, 0),
				span(0, "àquela".length(), 1)
			);
	}
	
	@Test
	public void testContraction_a_aqueles() throws Exception {
		cg.add("a");
		cg.add("aqueles");
		
		Match result = match("àqueles");
		
		verifyMatch(result, 0, "àqueles".length(),
				span(0, 1, 0),
				span(0, "àqueles".length(), 1)
			);
	}
	
	@Test
	public void testContraction_a_aquelas() throws Exception {
		cg.add("a");
		cg.add("aquelas");
		
		Match result = match("àquelas");
		
		verifyMatch(result, 0, "àquelas".length(),
				span(0, 1, 0),
				span(0, "àquelas".length(), 1)
			);
	}

	@Test
	public void testContraction_a_aquilo() throws Exception {
		cg.add("a");
		cg.add("aquilo");
		
		Match result = match("àquilo");
		
		verifyMatch(result, 0, "àquilo".length(),
				span(0, 1, 0),
				span(0, "àquilo".length(), 1)
			);
	}
	
	@Test
	public void testExpressionContraction_Quanto_a_as() throws Exception {
		cg.add("Quanto=a");
		cg.add("as");
		
		Match result = match("Quanto às");
		
		verifyMatch(result, 0, "Quanto às".length(),  
			span(0, "Quanto à".length(), 0),
			span("Quanto ".length(), "Quanto às".length(), 1)
		);
	}
	
	@Test
	public void testExpressionContraction_a_as_quais() throws Exception {
		cg.add("a");
		cg.add("as=quais");
		
		Match result = match("às quais");
		
		verifyMatch(result, 0, "às quais".length(),
				span(0, 1, 0),
				span(0, "às quais".length(), 1)
			);
	}
	
	@Test
	public void testExpressionContraction_devido_a_a() throws Exception {
		cg.add("devido=a");
		cg.add("a");
		
		Match result = match("devido à");
		
		verifyMatch(result, 0, "devido à".length(), 
				span(0, "devido à".length(), 0),
				span("devido ".length(), "devido à".length(), 1)
			);
	}
	
	@Test
	public void testExpressionContraction_devido_a_os_quais() throws Exception {
		cg.add("devido=a");
		cg.add("os=quais");
		
		Match result = match("devido aos quais");
		
		verifyMatch(result, 0, "devido aos quais".length(), 
				span(0, "devido a".length(), 0),
				span("devido a".length(), "devido aos quais".length(), 1)
			);
	}
}
