package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.inOrder;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class ContractionComMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	@Before
	public void setUp() {
		super.setUp(new ContractionComMatchStrategy());
	}
	
	@Test
	public void testContraction_com_mim() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("com", " [com] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("mim", " [eu] PERS M/F 1S PIV @P<")
			);
		
		matchAndApply(cg, "comigo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startPseudoToken(cg.get(0).getValue());
		order.verify(handler).characters("co".toCharArray());
		order.verify(handler).startPseudoToken(cg.get(1).getValue());
		order.verify(handler).characters("m".toCharArray());
		order.verify(handler).endPseudoToken();
		order.verify(handler).characters("igo".toCharArray());
		order.verify(handler).endPseudoToken();
	}
	
	@Test
	public void testContraction_com_ti() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("com", " [com] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("ti", " [tu] PERS M/F 2S PIV @P<")
			);
		
		matchAndApply(cg, "contigo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("con".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("tigo".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_com_si() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("com", " [com] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("si", " [se] <refl> PERS M/F 3S/P PIV @P<")
			);
		
		matchAndApply(cg, "consigo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("con".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("sigo".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_com_nos() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("com", " [com] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("nos", " [nós] PERS M/F 1P PIV @P<")
			);
		
		matchAndApply(cg, "conosco");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startPseudoToken(cg.get(0).getValue());
		order.verify(handler).characters("co".toCharArray());
		order.verify(handler).startPseudoToken(cg.get(1).getValue());
		order.verify(handler).characters("n".toCharArray());
		order.verify(handler).endPseudoToken();
		order.verify(handler).characters("osco".toCharArray());
		order.verify(handler).endPseudoToken();
	}
	
	@Test
	public void testContraction_com_vos() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("com", " [com] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("vos", " [vós] PERS M/F 2P PIV @P<")
			);
		
		matchAndApply(cg, "convosco");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("con".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("vosco".toCharArray());
		order.verify(handler).endToken();
	}
}
