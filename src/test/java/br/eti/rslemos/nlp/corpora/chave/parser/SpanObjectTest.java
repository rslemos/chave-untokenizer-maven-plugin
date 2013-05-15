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

import static br.eti.rslemos.nlp.corpora.chave.parser.Span.span;

import org.junit.experimental.theories.DataPoint;

import br.eti.rslemos.tools.test.ObjectTest;

public class SpanObjectTest extends ObjectTest {
	@DataPoint public static Span a0 = span(0, 0, 0);
	@DataPoint public static Span b0 = span(0, 10, 0);
	@DataPoint public static Span c0 = span(0, 10, 1);
	@DataPoint public static Span d0 = span(0, 0, 2);
	@DataPoint public static Span e0 = span(5, 10, 0);
	@DataPoint public static Span f0 = span(5, 10, 1);
	
	@DataPoint public static Span a1 = span(0, 0, 0);
	@DataPoint public static Span b1 = span(0, 10, 0);
	@DataPoint public static Span c1 = span(0, 10, 1);
	@DataPoint public static Span d1 = span(0, 0, 2);
	@DataPoint public static Span e1 = span(5, 10, 0);
	@DataPoint public static Span f1 = span(5, 10, 1);
}
