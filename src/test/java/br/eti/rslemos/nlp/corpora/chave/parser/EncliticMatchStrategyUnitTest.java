package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.inOrder;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class EncliticMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	@Before
	public void setUp() {
		super.setUp(new EncliticMatchStrategy());
	}
	
	@Test
	public void testInfinitiv_ar_lo() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("Convocar-", " [convocar] V INF @IMV <hyfen> @#ICL-SUBJ>"),
				new Parser.Entry<String, String>("lo", " [ele] PERS M 3S ACC @<ACC")
			);
		
		matchAndApply(cg, "Convocá-lo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("Convocá-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("lo".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testInfinitiv_ar_la() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("Convocar-", " [convocar] V INF @IMV <hyfen> @#ICL-SUBJ>"),
				new Parser.Entry<String, String>("la", " [ela] PERS F 3S ACC @<ACC")
			);
		
		matchAndApply(cg, "Convocá-la");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("Convocá-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("la".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testInfinitiv_ar_los() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("Convocar-", " [convocar] V INF @IMV <hyfen> @#ICL-SUBJ>"),
				new Parser.Entry<String, String>("los", " [ele] PERS M 3P ACC @<ACC")
			);
		
		matchAndApply(cg, "Convocá-los");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("Convocá-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("los".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testInfinitiv_ar_las() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("Convocar-", " [convocar] V INF @IMV <hyfen> @#ICL-SUBJ>"),
				new Parser.Entry<String, String>("las", " [ela] PERS F 3M ACC @<ACC")
			);
		
		matchAndApply(cg, "Convocá-las");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("Convocá-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("las".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testInfinitiv_er_lo() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("fazer-", " [fazer] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("lo", " [ele] PERS M 3S ACC @<ACC")
			);
		
		matchAndApply(cg, "fazê-lo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("fazê-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("lo".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testInfinitiv_er_la() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("fazer-", " [fazer] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("la", " [ela] PERS F 3S ACC @<ACC")
			);
		
		matchAndApply(cg, "fazê-la");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("fazê-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("la".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testInfinitiv_er_los() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("fazer-", " [fazer] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("los", " [ele] PERS M 3P ACC @<ACC")
			);
		
		matchAndApply(cg, "fazê-los");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("fazê-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("los".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testInfinitiv_er_las() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("fazer-", " [fazer] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("las", " [ela] PERS F 3M ACC @<ACC")
			);
		
		matchAndApply(cg, "fazê-las");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("fazê-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("las".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testInfinitiv_ir_lo() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("lo", " [ele] PERS M 3S ACC @<ACC")
			);
		
		matchAndApply(cg, "assisti-lo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("assisti-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("lo".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testInfinitiv_ir_la() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("la", " [ela] PERS F 3S ACC @<ACC")
			);
		
		matchAndApply(cg, "assisti-la");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("assisti-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("la".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testInfinitiv_ir_los() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("los", " [ele] PERS M 3P ACC @<ACC")
			);
		
		matchAndApply(cg, "assisti-los");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("assisti-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("los".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testInfinitiv_ir_las() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("las", " [ela] PERS F 3M ACC @<ACC")
			);
		
		matchAndApply(cg, "assisti-las");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("assisti-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("las".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testInfinitiv_por_lo() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("lo", " [ele] PERS M 3S ACC @<ACC")
			);
		
		matchAndApply(cg, "assisti-lo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("assisti-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("lo".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testInfinitiv_por_la() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("la", " [ela] PERS F 3S ACC @<ACC")
			);
		
		matchAndApply(cg, "assisti-la");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("assisti-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("la".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testInfinitiv_por_los() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("los", " [ele] PERS M 3P ACC @<ACC")
			);
		
		matchAndApply(cg, "assisti-los");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("assisti-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("los".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testInfinitiv_por_las() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("assistir-", " [assistir] V INF @IMV <hyfen> @#ICL-P<"),
				new Parser.Entry<String, String>("las", " [ela] PERS F 3M ACC @<ACC")
			);
		
		matchAndApply(cg, "assisti-las");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("assisti-".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("las".toCharArray());
		order.verify(handler).endToken();
	}
}
