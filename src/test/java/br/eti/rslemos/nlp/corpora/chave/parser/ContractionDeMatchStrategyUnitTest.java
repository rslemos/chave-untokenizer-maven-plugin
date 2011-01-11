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

	@Test
	public void testContraction_de_este() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("este", " [este] DET M S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "deste");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("este".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_esse() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("esse", " [esse] DET M S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "desse");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("esse".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_esta() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("esta", " [este] DET F S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "desta");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("esta".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_essa() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("essa", " [esse] DET F S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "dessa");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("essa".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_estes() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("estes", " [este] DET M P <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "destes");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("estes".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_esses() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("esses", " [esse] DET M P <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "desses");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("esses".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_estas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("estas", " [este] DET F P <dem> @>N")
			);
		
		matchAndApply(cg, "destas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("estas".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_essas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("essas", " [esse] DET F P <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "dessas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("essas".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_isto() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("isto", " [isto] SPEC M S <dem> @<ACC")
			);
		
		matchAndApply(cg, "disto");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("isto".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_isso() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("isso", " [isso] SPEC M S <dem> @SUBJ>")
			);
		
		matchAndApply(cg, "disso");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("isso".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_aquele() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("aquele", " [aquele] DET M S <dem> <-sam> @>N")
			);
		
		matchAndApply(cg, "daquele");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aquele".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_aquela() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("aquela", "[aquele] DET F S <dem> @>N")
			);
		
		matchAndApply(cg, "daquela");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aquela".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_aqueles() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("aqueles", " [aquele] DET M P <dem> @P<")
			);
		
		matchAndApply(cg, "daqueles");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aqueles".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_aquelas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("aquelas", " [aquele] DET F P <dem> <-sam> @P<")
			);
		
		matchAndApply(cg, "daquelas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aquelas".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_aquilo() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("aquilo", " [aquilo] SPEC M S <dem> <-sam> @P<")
			);
		
		matchAndApply(cg, "daquilo");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("aquilo".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_ele() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("ele", " [ele] PERS M 3S NOM @SUBJ>")
			);
		
		matchAndApply(cg, "dele");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("ele".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_ela() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("ela", " [ela] PERS F 3S NOM/PIV <-sam> @P<")
			);
		
		matchAndApply(cg, "dela");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("ela".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_eles() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("eles", " [eles] PERS M 3P NOM/PIV <-sam> @P<")
			);
		
		matchAndApply(cg, "deles");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("eles".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testContraction_de_elas() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("de", " [de] PRP <sam-> @N<"),
				new Parser.Entry<String, String>("elas", " [elas] PERS F 3P NOM/PIV <-sam> @P<")
			);
		
		matchAndApply(cg, "delas");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("elas".toCharArray());
		order.verify(handler).endToken();
	}
	
	@Test
	public void testExpressionContraction_Alem_de_isso() throws Exception {
		@SuppressWarnings("unchecked")
		List<Entry<String, String>> cg = Arrays.asList(
				new Parser.Entry<String, String>("Além=de", " [além=de] PRP <sam-> @ADVL>"),
				new Parser.Entry<String, String>("isso", " [isso] SPEC M S <-sam> <dem> @P<")
			);
		
		matchAndApply(cg, "Além disso");
		
		InOrder order = inOrder(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters("Além d".toCharArray());
		order.verify(handler).endToken();
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters("isso".toCharArray());
		order.verify(handler).endToken();
	}
}
