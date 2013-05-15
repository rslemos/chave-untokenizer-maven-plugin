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

import org.junit.experimental.theories.DataPoint;

import br.eti.rslemos.tools.test.ObjectTest;

public class MatchObjectTest extends ObjectTest {
	@DataPoint public static Match a0 = match(0, 0);
	@DataPoint public static Match b0 = match(0, 10, span(0, 10, 0));
	@DataPoint public static Match c0 = match(5, 10, span(5, 10, 0), span(5, 10, 1));
	@DataPoint public static Match d0 = match(0, 15, span(0, 10, 0), span(5, 15, 1));
	@DataPoint public static Match e0 = match(0, 10, span(0, 10, 1));
	@DataPoint public static Match f0 = match(0, 10, span(5, 10, 0), span(5, 10, 1));

	@DataPoint public static Match a1 = match(0, 0);
	@DataPoint public static Match b1 = match(0, 10, span(0, 10, 0));
	@DataPoint public static Match c1 = match(5, 10, span(5, 10, 0), span(5, 10, 1));
	@DataPoint public static Match d1 = match(0, 15, span(0, 10, 0), span(5, 15, 1));
	@DataPoint public static Match e1 = match(0, 10, span(0, 10, 1));
	@DataPoint public static Match f1 = match(0, 10, span(5, 10, 0), span(5, 10, 1));
}
