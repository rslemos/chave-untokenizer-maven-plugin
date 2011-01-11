package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.inOrder;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class ContractionEmMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	@Before
	public void setUp() {
		super.setUp(new ContractionEmMatchStrategy());
	}
	
	@Test
	public void testContraction_em_o() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("o", " [o] DET M S <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "no");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("o".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_a() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("a", " [o] DET F S <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "na");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("a".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_os() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("os", " [o] DET M P <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "nos");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("os".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_as() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("as", " [o] DET F P <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "nas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("as".toCharArray());
		order.verify(handler).endToken();
	}

	@Test
	public void testContraction_em_este() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("este", " [este] DET M S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "neste");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("este".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_esse() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("esse", " [esse] DET M S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "nesse");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("esse".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_esta() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("esta", " [este] DET F S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "nesta");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("esta".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_essa() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("essa", " [esse] DET F S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "nessa");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("essa".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_estes() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("estes", " [este] DET M P <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "nestes");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("estes".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_esses() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("esses", " [esse] DET M P <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "nesses");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("esses".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_estas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("estas", " [este] DET F P <dem> @>N")
			);
		
		matchAndApply(cg, "nestas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("estas".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_essas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("essas", " [esse] DET F P <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "nessas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("essas".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_isto() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("isto", " [isto] SPEC M S <dem> @<ACC")
			);
		
		matchAndApply(cg, "nisto");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("isto".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_isso() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("isso", " [isso] SPEC M S <dem> @SUBJ>")
			);
		
		matchAndApply(cg, "nisso");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("isso".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_aquele() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("aquele", " [aquele] DET M S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "naquele");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aquele".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_aquela() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("aquela", "[aquele] DET F S <dem> @>N")
			);
		
		matchAndApply(cg, "naquela");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aquela".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_aqueles() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("aqueles", " [aquele] DET M P <dem> @P<")
			);
		
		matchAndApply(cg, "naqueles");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aqueles".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_aquelas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("aquelas", " [aquele] DET F P <dem> <-sam> @P<")
			);
		
		matchAndApply(cg, "naquelas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aquelas".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_aquilo() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("aquilo", " [aquilo] SPEC M S <dem> <-sam> @P<")
			);
		
		matchAndApply(cg, "naquilo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aquilo".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_ele() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("ele", " [ele] PERS M 3S NOM @SUBJ>")
			);
		
		matchAndApply(cg, "nele");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("ele".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_ela() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("ela", " [ela] PERS F 3S NOM/PIV <-sam> @P<")
			);
		
		matchAndApply(cg, "nela");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("ela".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_eles() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("eles", " [eles] PERS M 3P NOM/PIV <-sam> @P<")
			);
		
		matchAndApply(cg, "neles");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("eles".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_elas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("elas", " [elas] PERS F 3P NOM/PIV <-sam> @P<")
			);
		
		matchAndApply(cg, "nelas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("elas".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_um() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("um", " [um] DET M S <arti> @>N")
			);
		
		matchAndApply(cg, "num");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("um".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_uma() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("uma", " [um] DET F S <arti> @>N")
			);
		
		matchAndApply(cg, "numa");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("uma".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_uns() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("uns", " [um] DET M P <arti> @>N")
			);
		
		matchAndApply(cg, "nuns");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("uns".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_em_umas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("em", " [em] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("umas", " [um] DET F P <arti> @>N")
			);
		
		matchAndApply(cg, "numas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("umas".toCharArray());
		order.verify(handler).endToken();
	}
}
