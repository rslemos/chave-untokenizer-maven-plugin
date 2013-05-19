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

public class IdiossincraticMatchStrategyUnitTest extends AbstractMatchStrategyUnitTest<IdiossincraticMatchStrategy> {

	public IdiossincraticMatchStrategyUnitTest() {
		super(new IdiossincraticMatchStrategy());
	}

	@Test
	public void testMatch() {
		strategy.addMatch("d'Água", "dÁgua");
		
		cg.add("dÁgua ALT xxxua");
		
		runOver(" d'Água' ");
		
		verifyMatches(
				match(1, 7, span(1, 7, 0))
			);
		
	}
}
