/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "chave-untokenizer-maven-plugin"
 * Copyright 2013  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
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

	@Test
	public void testParenthesis() throws Exception {
		cg.add("$¶");
		runOver("sample text)");
		
		verifyMatches(
				match("sample".length(), "sample".length(), span("sample".length(), "sample".length(), 0)),
				match("sample text".length(), "sample text".length(), span("sample text".length(), "sample text".length(), 0)),
				match("sample text)".length(), "sample text)".length(), span("sample text)".length(), "sample text)".length(), 0))
			);
	}
}
