package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Test;

public class SentenceMarkerMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public SentenceMarkerMatchStrategyUnitTest() {
		super(new SentenceMarkerMatchStrategy());
	}
	
	@Test
	public void testStartSentenceMarker() throws Exception {
		cg.add("<s>");
		MatchResult result = match("");
		
		verifyNoToken(result);
	}
	
	@Test
	public void testEndSentenceMarker() throws Exception {
		cg.add("</s>");
		MatchResult result = match("");
		
		verifyNoToken(result);
	}
}
