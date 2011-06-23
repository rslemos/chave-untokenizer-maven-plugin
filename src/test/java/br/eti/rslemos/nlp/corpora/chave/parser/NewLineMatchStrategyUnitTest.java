package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.match;
import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.Test;

public class NewLineMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest {

	public NewLineMatchStrategyUnitTest() {
		super(new NewLineMatchStrategy());
	}
	
	@Test
	public void testNewLine() throws Exception {
		cg.add("$¶");
		runOver("sampletext\n");
		
		verifyMatches(match("sampletext".length(), "sampletext\n".length(), span("sampletext".length(), "sampletext\n".length(), 0)));
	}

	@Test
	public void testEmptyNewLine() throws Exception {
		cg.add("$¶");
		runOver("sampletext");
		
		verifyMatches(match("sampletext".length(), "sampletext".length(), span("sampletext".length(), "sampletext".length(), 0)));
	}

	@Test
	public void testWordBoundaries() throws Exception {
		cg.add("$¶");
		runOver("sample text");
		
		verifyMatches(
				match("sample".length(), "sample".length(), span("sample".length(), "sample".length(), 0)),
				match("sample text".length(), "sample text".length(), span("sample text".length(), "sample text".length(), 0))
			);
	}


	@Test
	public void testPunctuationMark() throws Exception {
		cg.add("$¶");
		runOver("sample text?");
		
		verifyMatches(
				match("sample".length(), "sample".length(), span("sample".length(), "sample".length(), 0)),
				match("sample text".length(), "sample text".length(), span("sample text".length(), "sample text".length(), 0)),
				match("sample text?".length(), "sample text?".length(), span("sample text?".length(), "sample text?".length(), 0))
			);
	}
}
