package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry.entry;

import org.junit.Test;

public class EncliticMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	public EncliticMatchStrategyUnitTest() {
		super(new EncliticMatchStrategy());
	}
	
	@Test
	public void testInfinitiv_ar_lo() throws Exception {
		cg.add(entry("Convocar-", " [convocar] V INF @IMV <hyfen> @#ICL-SUBJ>"));
		cg.add(entry("lo", " [ele] PERS M 3S ACC @<ACC"));
		
		MatchResult result = match("Convocá-lo");
		
		verifyTokensInSequence(result, "Convocá-", "lo");
	}

	@Test
	public void testInfinitiv_ar_la() throws Exception {
		cg.add(entry("Convocar-", " [convocar] V INF @IMV <hyfen> @#ICL-SUBJ>"));
		cg.add(entry("la", " [ela] PERS F 3S ACC @<ACC"));
		
		MatchResult result = match("Convocá-la");
		
		verifyTokensInSequence(result, "Convocá-", "la");
	}
	
	@Test
	public void testInfinitiv_ar_los() throws Exception {
		cg.add(entry("Convocar-", " [convocar] V INF @IMV <hyfen> @#ICL-SUBJ>"));
		cg.add(entry("los", " [ele] PERS M 3P ACC @<ACC"));
		
		MatchResult result = match("Convocá-los");
		
		verifyTokensInSequence(result, "Convocá-", "los");
	}

	@Test
	public void testInfinitiv_ar_las() throws Exception {
		cg.add(entry("Convocar-", " [convocar] V INF @IMV <hyfen> @#ICL-SUBJ>"));
		cg.add(entry("las", " [ela] PERS F 3M ACC @<ACC"));
		
		MatchResult result = match("Convocá-las");
		
		verifyTokensInSequence(result, "Convocá-", "las");
	}
	
	@Test
	public void testInfinitiv_er_lo() throws Exception {
		cg.add(entry("fazer-", " [fazer] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("lo", " [ele] PERS M 3S ACC @<ACC"));
		
		MatchResult result = match("fazê-lo");
		
		verifyTokensInSequence(result, "fazê-", "lo");
	}

	@Test
	public void testInfinitiv_er_la() throws Exception {
		cg.add(entry("fazer-", " [fazer] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("la", " [ela] PERS F 3S ACC @<ACC"));
		
		MatchResult result = match("fazê-la");
		
		verifyTokensInSequence(result, "fazê-", "la");
	}
	
	@Test
	public void testInfinitiv_er_los() throws Exception {
		cg.add(entry("fazer-", " [fazer] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("los", " [ele] PERS M 3P ACC @<ACC"));
		
		MatchResult result = match("fazê-los");
		
		verifyTokensInSequence(result, "fazê-", "los");
	}

	@Test
	public void testInfinitiv_er_las() throws Exception {
		cg.add(entry("fazer-", " [fazer] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("las", " [ela] PERS F 3M ACC @<ACC"));
		
		MatchResult result = match("fazê-las");
		
		verifyTokensInSequence(result, "fazê-", "las");
	}
	
	@Test
	public void testInfinitiv_ir_lo() throws Exception {
		cg.add(entry("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("lo", " [ele] PERS M 3S ACC @<ACC"));
		
		MatchResult result = match("assisti-lo");
		
		verifyTokensInSequence(result, "assisti-", "lo");
	}

	@Test
	public void testInfinitiv_ir_la() throws Exception {
		cg.add(entry("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("la", " [ela] PERS F 3S ACC @<ACC"));
		
		MatchResult result = match("assisti-la");
		
		verifyTokensInSequence(result, "assisti-", "la");
	}
	
	@Test
	public void testInfinitiv_ir_los() throws Exception {
		cg.add(entry("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("los", " [ele] PERS M 3P ACC @<ACC"));
		
		MatchResult result = match("assisti-los");
		
		verifyTokensInSequence(result, "assisti-", "los");
	}

	@Test
	public void testInfinitiv_ir_las() throws Exception {
		cg.add(entry("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("las", " [ela] PERS F 3M ACC @<ACC"));
		
		MatchResult result = match("assisti-las");
		
		verifyTokensInSequence(result, "assisti-", "las");
	}
	
	@Test
	public void testInfinitiv_por_lo() throws Exception {
		cg.add(entry("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("lo", " [ele] PERS M 3S ACC @<ACC"));
		
		MatchResult result = match("assisti-lo");
		
		verifyTokensInSequence(result, "assisti-", "lo");
	}

	@Test
	public void testInfinitiv_por_la() throws Exception {
		cg.add(entry("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("la", " [ela] PERS F 3S ACC @<ACC"));
		
		MatchResult result = match("assisti-la");
		
		verifyTokensInSequence(result, "assisti-", "la");
	}
	
	@Test
	public void testInfinitiv_por_los() throws Exception {
		cg.add(entry("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("los", " [ele] PERS M 3P ACC @<ACC"));
		
		MatchResult result = match("assisti-los");
		
		verifyTokensInSequence(result, "assisti-", "los");
	}

	@Test
	public void testInfinitiv_por_las() throws Exception {
		cg.add(entry("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"));
		cg.add(entry("las", " [ela] PERS F 3M ACC @<ACC"));
		
		MatchResult result = match("assisti-las");
		
		verifyTokensInSequence(result, "assisti-", "las");
	}
}
