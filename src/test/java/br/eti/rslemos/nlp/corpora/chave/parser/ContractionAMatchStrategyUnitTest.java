package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class ContractionAMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	@Before
	public void setUp() {
		super.setUp(new ContractionAMatchStrategy());
	}
	
	@Test
	public void testContraction_a_o() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("o", " [o] DET M S <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "ao");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("a".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("o".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_a_a() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("a", " [o] DET F S <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "à");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler, times(2)).endToken();
	}
	
	@Test
	public void testContraction_a_os() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("os", " [o] DET M P <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "aos");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("a".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("os".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_a_as() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("as", " [o] DET F P <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "às");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler).endToken();
		order.verify(handler).characters("s".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_a_aquele() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("aquele", "[aquele] DET M S <dem> @>N")
			);
		
		matchAndApply(cg, "àquele");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler).endToken();
		order.verify(handler).characters("quele".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_a_aquela() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("aquela", "[aquele] DET F S <dem> @>N")
			);
		
		matchAndApply(cg, "àquela");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler).endToken();
		order.verify(handler).characters("quela".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_a_aqueles() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("aqueles", " [aquele] DET M P <dem> @P<")
			);
		
		matchAndApply(cg, "àqueles");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler).endToken();
		order.verify(handler).characters("queles".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_a_aquelas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("aquelas", " [aquele] DET F P <dem> <-sam> @P<")
			);
		
		matchAndApply(cg, "àquelas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler).endToken();
		order.verify(handler).characters("quelas".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_a_aquilo() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("aquilo", " [aquilo] SPEC M S <dem> <-sam> @P<")
			);
		
		matchAndApply(cg, "àquilo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler).endToken();
		order.verify(handler).characters("quilo".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testExpressionContraction_Quanto_a_as() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("Quanto=a", " [quanto=a] PRP <sam-> @ADVL>"),
				new Parser.Entry<String, String>("as", " [o] DET F P <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "Quanto às");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startPseudoToken(cg.get(0).getValue());
		order.verify(handler).characters("Quanto ".toCharArray());
		order.verify(handler).startPseudoToken(cg.get(1).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler).endPseudoToken();
		order.verify(handler).characters("s".toCharArray());
		order.verify(handler).endPseudoToken();
	}
	
	@Test
	public void testExpressionContraction_a_as_quais() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("a", " [a] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("as=quais", " [o=qual] SPEC F P @P< <rel> <-sam> @#FS-N<")
			);
		
		matchAndApply(cg, "às quais");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler).endToken();
		order.verify(handler).characters("s quais".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testExpressionContraction_devido_a_a() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("devido=a", " [devido=a] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("a", " [o] DET F S <-sam> <artd> @>N")
			);
		
		matchAndApply(cg, "devido à");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("devido ".toCharArray());
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("à".toCharArray());
		order.verify(handler, times(2)).endToken();
	}
	
	@Test
	public void testExpressionContraction_devido_a_os_quais() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("devido=a", " [devido=a] PRP <sam-> @<ADVL"),
				new Parser.Entry<String, String>("os=quais", " [o=qual] SPEC M P @P< <rel> <-sam> @#FS-N<")
			);
		
		matchAndApply(cg, "devido aos quais");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("devido a".toCharArray());
		order.verify(handler).endToken();
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("os quais".toCharArray());
		order.verify(handler).endToken();
	}
}
