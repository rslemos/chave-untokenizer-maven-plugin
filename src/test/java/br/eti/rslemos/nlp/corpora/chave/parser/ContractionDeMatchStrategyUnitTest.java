package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.inOrder;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public class ContractionDeMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	
	@Before
	public void setUp() {
		super.setUp(new ContractionDeMatchStrategy());
	}
	
	@Test
	public void testContraction_de_o() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("o", " [o] DET M S <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "do");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("o".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_a() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("a", " [a] DET F S <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "da");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("a".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_os() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("os", " [os] DET M P <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "dos");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("os".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_as() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("as", " [as] DET F P <artd> <-sam> @>N")
			);
		
		matchAndApply(cg, "das");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("as".toCharArray());
		order.verify(handler).endToken();
	}
}
