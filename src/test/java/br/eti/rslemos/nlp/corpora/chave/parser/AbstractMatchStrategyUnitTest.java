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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class AbstractMatchStrategyUnitTest {

	private final MatchStrategy strategy;
	protected final List<String> cg = new LinkedList<String>();

	private Set<Match> matches;

	protected AbstractMatchStrategyUnitTest(MatchStrategy strategy) {
		this.strategy = strategy;
	}

	protected void runOver(String sgml) {
		strategy.setData(new DamerauLevenshteinTextMatcher(sgml), cg);
		matches = strategy.matchAll(0, sgml.length(), 0, cg.size());
	}

	public void verifyMatches(Match... matches) {
		assertThat(this.matches.size(), is(equalTo(matches.length)));
		assertThat(this.matches, hasItems(matches));
	}
}
