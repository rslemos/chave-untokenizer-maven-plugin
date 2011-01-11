package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.inOrder;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class ContractionPorMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	@Before
	public void setUp() {
		super.setUp(new ContractionPorMatchStrategy());
	}
	
	@Test
	public void testContraction_de_o() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("por", " [por] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("o", " [o] DET M S <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "pelo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("pel".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("o".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("por", " [por] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("a", " [a] DET F S <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "pela");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("pel".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("a".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("por", " [por] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("os", " [os] DET M P <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "pelos");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("pel".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("os".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("por", " [por] PRP <sam-> @ADVL"),
				new Parser.Entry<String, String>("as", " [as] DET F P <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "pelas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("pel".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("as".toCharArray());
		order.verify(handler).endToken();
	}
}