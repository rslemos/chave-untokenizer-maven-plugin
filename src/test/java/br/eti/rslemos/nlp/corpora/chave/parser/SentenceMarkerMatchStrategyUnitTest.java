package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry.entry;

import org.junit.Before;
import org.junit.Test;

public class SentenceMarkerMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {
	@Before
	public void setUp() {
		super.setUp(new SentenceMarkerMatchStrategy());
	}
	
	@Test
	public void testStartSentenceMarker() throws Exception {
		cg.add(entry("<s>", (String)null));
		MatchResult result = match("");
		
		verifyNoToken(result);
	}
	
	@Test
	public void testEndSentenceMarker() throws Exception {
		cg.add(entry("</s>", (String)null));
		MatchResult result = match("");
		
		verifyNoToken(result);
	}
}
