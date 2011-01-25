package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class EncliticMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public EncliticMatchStrategyUnitTest() {
		super(new EncliticMatchStrategy());
	}
	
	@Test
	public void testInfinitiv_ar_lo() throws Exception {
		cg.add("Convocar-");
		cg.add("lo");
		
		MatchResult result = match("Convocá-lo");
		
		verifyTokensInSequence(result, "Convocá-", "lo");
	}

	@Test
	public void testInfinitiv_ar_la() throws Exception {
		cg.add("Convocar-");
		cg.add("la");
		
		MatchResult result = match("Convocá-la");
		
		verifyTokensInSequence(result, "Convocá-", "la");
	}
	
	@Test
	public void testInfinitiv_ar_los() throws Exception {
		cg.add("Convocar-");
		cg.add("los");
		
		MatchResult result = match("Convocá-los");
		
		verifyTokensInSequence(result, "Convocá-", "los");
	}

	@Test
	public void testInfinitiv_ar_las() throws Exception {
		cg.add("Convocar-");
		cg.add("las");
		
		MatchResult result = match("Convocá-las");
		
		verifyTokensInSequence(result, "Convocá-", "las");
	}
	
	@Test
	public void testInfinitiv_er_lo() throws Exception {
		cg.add("fazer-");
		cg.add("lo");
		
		MatchResult result = match("fazê-lo");
		
		verifyTokensInSequence(result, "fazê-", "lo");
	}

	@Test
	public void testInfinitiv_er_la() throws Exception {
		cg.add("fazer-");
		cg.add("la");
		
		MatchResult result = match("fazê-la");
		
		verifyTokensInSequence(result, "fazê-", "la");
	}
	
	@Test
	public void testInfinitiv_er_los() throws Exception {
		cg.add("fazer-");
		cg.add("los");
		
		MatchResult result = match("fazê-los");
		
		verifyTokensInSequence(result, "fazê-", "los");
	}

	@Test
	public void testInfinitiv_er_las() throws Exception {
		cg.add("fazer-");
		cg.add("las");
		
		MatchResult result = match("fazê-las");
		
		verifyTokensInSequence(result, "fazê-", "las");
	}
	
	@Test
	public void testInfinitiv_ir_lo() throws Exception {
		cg.add("assistir-");
		cg.add("lo");
		
		MatchResult result = match("assisti-lo");
		
		verifyTokensInSequence(result, "assisti-", "lo");
	}

	@Test
	public void testInfinitiv_ir_la() throws Exception {
		cg.add("assistir-");
		cg.add("la");
		
		MatchResult result = match("assisti-la");
		
		verifyTokensInSequence(result, "assisti-", "la");
	}
	
	@Test
	public void testInfinitiv_ir_los() throws Exception {
		cg.add("assistir-");
		cg.add("los");
		
		MatchResult result = match("assisti-los");
		
		verifyTokensInSequence(result, "assisti-", "los");
	}

	@Test
	public void testInfinitiv_ir_las() throws Exception {
		cg.add("assistir-");
		cg.add("las");
		
		MatchResult result = match("assisti-las");
		
		verifyTokensInSequence(result, "assisti-", "las");
	}
	
	@Test
	public void testInfinitiv_por_lo() throws Exception {
		cg.add("assistir-");
		cg.add("lo");
		
		MatchResult result = match("assisti-lo");
		
		verifyTokensInSequence(result, "assisti-", "lo");
	}

	@Test
	public void testInfinitiv_por_la() throws Exception {
		cg.add("assistir-");
		cg.add("la");
		
		MatchResult result = match("assisti-la");
		
		verifyTokensInSequence(result, "assisti-", "la");
	}
	
	@Test
	public void testInfinitiv_por_los() throws Exception {
		cg.add("assistir-");
		cg.add("los");
		
		MatchResult result = match("assisti-los");
		
		verifyTokensInSequence(result, "assisti-", "los");
	}

	@Test
	public void testInfinitiv_por_las() throws Exception {
		cg.add("assistir-");
		cg.add("las");
		
		MatchResult result = match("assisti-las");
		
		verifyTokensInSequence(result, "assisti-", "las");
	}
}
