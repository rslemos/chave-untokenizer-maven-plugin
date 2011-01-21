package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry.entry;

import org.junit.Before;
import org.junit.Test;

public class NewLineMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	@Before
	public void setUp() {
		super.setUp(new NewLineMatchStrategy());
	}
	
	@Test
	public void testNewLine() throws Exception {
		cg.add(entry("$¶", " [$¶] PU <<<"));
		MatchResult result = match("\n");
		
		verifyTokensInSequence(result, "\n");
	}

	@Test
	public void testOptionalNewLine() throws Exception {
		cg.add(entry("$¶", " [$¶] PU <<<"));
		MatchResult result = match("abcd");
		
		verifyTokensInSequence(result, "");
	}

	@Test
	public void testEmptyInput() throws Exception {
		cg.add(entry("$¶", " [$¶] PU <<<"));
		MatchResult result = match("");
		
		verifyTokensInSequence(result, "");
	}
}
