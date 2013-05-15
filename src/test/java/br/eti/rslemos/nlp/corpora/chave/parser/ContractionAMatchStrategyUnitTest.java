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

public class ContractionAMatchStrategyUnitTest extends ContractionMatchStrategyUnitTest {
	
	// a + x
	@Test
	public void testContraction_a_o() throws Exception {
		cg.add("a");
		cg.add("o");
		
		runOver("ao");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_a_a() throws Exception {
		cg.add("a");
		cg.add("a");
		
		runOver("à");
		
		verifyMatches(match(0, 1, span(0, 1, 0), 
				span(0, 1, 1)
			));
	}
	
	@Test
	public void testContraction_a_os() throws Exception {
		cg.add("a");
		cg.add("os");
		
		runOver("aos");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_a_as() throws Exception {
		cg.add("a");
		cg.add("as");
		
		runOver("às");
		
		verifyMatches(match(0, 2, span(0, 1, 0),
				span(0, 2, 1)
			));
	}
	
	@Test
	public void testContraction_a_aquele() throws Exception {
		cg.add("a");
		cg.add("aquele");
		
		runOver("àquele");
		
		verifyMatches(match(0, "àquele".length(), span(0, 1, 0),
				span(0, "àquele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_a_aquela() throws Exception {
		cg.add("a");
		cg.add("aquela");
		
		runOver("àquela");
		
		verifyMatches(match(0, "àquela".length(), span(0, 1, 0),
				span(0, "àquela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_a_aqueles() throws Exception {
		cg.add("a");
		cg.add("aqueles");
		
		runOver("àqueles");
		
		verifyMatches(match(0, "àqueles".length(), span(0, 1, 0),
				span(0, "àqueles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_a_aquelas() throws Exception {
		cg.add("a");
		cg.add("aquelas");
		
		runOver("àquelas");
		
		verifyMatches(match(0, "àquelas".length(), span(0, 1, 0),
				span(0, "àquelas".length(), 1)
			));
	}

	@Test
	public void testContraction_a_aquilo() throws Exception {
		cg.add("a");
		cg.add("aquilo");
		
		runOver("àquilo");
		
		verifyMatches(match(0, "àquilo".length(), span(0, 1, 0),
				span(0, "àquilo".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_auanto_a_as() throws Exception {
		cg.add("quanto=a");
		cg.add("as");
		
		runOver("quanto às");
		
		verifyMatches(match(0, "quanto às".length(), span(0, "quanto à".length(), 0),  
			span("quanto ".length(), "quanto às".length(), 1)
		));
	}
	
	@Test
	public void testExpressionContraction_a_as_quais() throws Exception {
		cg.add("a");
		cg.add("as=quais");
		
		runOver("às quais");
		
		verifyMatches(match(0, "às quais".length(), span(0, 1, 0),
				span(0, "às quais".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_devido_a_a() throws Exception {
		cg.add("devido=a");
		cg.add("a");
		
		runOver("devido à");
		
		verifyMatches(match(0, "devido à".length(), span(0, "devido à".length(), 0), 
				span("devido ".length(), "devido à".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_devido_a_os_quais() throws Exception {
		cg.add("devido=a");
		cg.add("os=quais");
		
		runOver("devido aos quais");
		
		verifyMatches(match(0, "devido aos quais".length(), span(0, "devido a".length(), 0), 
				span("devido a".length(), "devido aos quais".length(), 1)
			));
	}
	
	// A + x
	@Test
	public void testContraction_A_o() throws Exception {
		cg.add("A");
		cg.add("o");
		
		runOver("Ao");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_A_a() throws Exception {
		cg.add("A");
		cg.add("a");
		
		runOver("À");
		
		verifyMatches(match(0, 1, span(0, 1, 0), 
				span(0, 1, 1)
			));
	}
	
	@Test
	public void testContraction_A_os() throws Exception {
		cg.add("A");
		cg.add("os");
		
		runOver("Aos");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_A_as() throws Exception {
		cg.add("A");
		cg.add("as");
		
		runOver("Às");
		
		verifyMatches(match(0, 2, span(0, 1, 0),
				span(0, 2, 1)
			));
	}
	
	@Test
	public void testContraction_A_aquele() throws Exception {
		cg.add("A");
		cg.add("aquele");
		
		runOver("Àquele");
		
		verifyMatches(match(0, "Àquele".length(), span(0, 1, 0),
				span(0, "Àquele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_A_aquela() throws Exception {
		cg.add("A");
		cg.add("aquela");
		
		runOver("Àquela");
		
		verifyMatches(match(0, "Àquela".length(), span(0, 1, 0),
				span(0, "Àquela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_A_aqueles() throws Exception {
		cg.add("A");
		cg.add("aqueles");
		
		runOver("Àqueles");
		
		verifyMatches(match(0, "Àqueles".length(), span(0, 1, 0),
				span(0, "Àqueles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_A_aquelas() throws Exception {
		cg.add("A");
		cg.add("aquelas");
		
		runOver("Àquelas");
		
		verifyMatches(match(0, "Àquelas".length(), span(0, 1, 0),
				span(0, "Àquelas".length(), 1)
			));
	}

	@Test
	public void testContraction_A_aquilo() throws Exception {
		cg.add("A");
		cg.add("aquilo");
		
		runOver("Àquilo");
		
		verifyMatches(match(0, "Àquilo".length(), span(0, 1, 0),
				span(0, "Àquilo".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_Quanto_a_as() throws Exception {
		cg.add("Quanto=a");
		cg.add("as");
		
		runOver("Quanto às");
		
		verifyMatches(match(0, "Quanto às".length(), span(0, "Quanto à".length(), 0),  
			span("Quanto ".length(), "Quanto às".length(), 1)
		));
	}
	
	@Test
	public void testExpressionContraction_A_as_quais() throws Exception {
		cg.add("A");
		cg.add("as=quais");
		
		runOver("Às quais");
		
		verifyMatches(match(0, "Às quais".length(), span(0, 1, 0),
				span(0, "Às quais".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_Devido_a_a() throws Exception {
		cg.add("Devido=a");
		cg.add("a");
		
		runOver("Devido à");
		
		verifyMatches(match(0, "Devido à".length(), span(0, "Devido à".length(), 0), 
				span("Devido ".length(), "Devido à".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_Devido_a_os_quais() throws Exception {
		cg.add("Devido=a");
		cg.add("os=quais");
		
		runOver("Devido aos quais");
		
		verifyMatches(match(0, "Devido aos quais".length(), span(0, "Devido a".length(), 0), 
				span("Devido a".length(), "Devido aos quais".length(), 1)
			));
	}
	
	// A + X
	@Test
	public void testContraction_A_O() throws Exception {
		cg.add("A");
		cg.add("O");
		
		runOver("AO");
		
		verifyMatches(match(0, 2, span(0, 1, 0), 
				span(1, 2, 1)
			));
	}
	
	@Test
	public void testContraction_A_A() throws Exception {
		cg.add("A");
		cg.add("A");
		
		runOver("À");
		
		verifyMatches(match(0, 1, span(0, 1, 0), 
				span(0, 1, 1)
			));
	}
	
	@Test
	public void testContraction_A_OS() throws Exception {
		cg.add("A");
		cg.add("OS");
		
		runOver("AOS");
		
		verifyMatches(match(0, 3, span(0, 1, 0), 
				span(1, 3, 1)
			));
	}
	
	@Test
	public void testContraction_A_AS() throws Exception {
		cg.add("A");
		cg.add("AS");
		
		runOver("ÀS");
		
		verifyMatches(match(0, 2, span(0, 1, 0),
				span(0, 2, 1)
			));
	}
	
	@Test
	public void testContraction_A_AQUELE() throws Exception {
		cg.add("A");
		cg.add("AQUELE");
		
		runOver("ÀQUELE");
		
		verifyMatches(match(0, "ÀQUELE".length(), span(0, 1, 0),
				span(0, "ÀQUELE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_A_AQUELA() throws Exception {
		cg.add("A");
		cg.add("AQUELA");
		
		runOver("ÀQUELA");
		
		verifyMatches(match(0, "ÀQUELA".length(), span(0, 1, 0),
				span(0, "ÀQUELA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_A_AQUELES() throws Exception {
		cg.add("A");
		cg.add("AQUELES");
		
		runOver("ÀQUELES");
		
		verifyMatches(match(0, "ÀQUELES".length(), span(0, 1, 0),
				span(0, "ÀQUELES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_A_AQUELAS() throws Exception {
		cg.add("A");
		cg.add("AQUELAS");
		
		runOver("ÀQUELAS");
		
		verifyMatches(match(0, "ÀQUELAS".length(), span(0, 1, 0),
				span(0, "ÀQUELAS".length(), 1)
			));
	}

	@Test
	public void testContraction_A_AQUILO() throws Exception {
		cg.add("A");
		cg.add("AQUILO");
		
		runOver("ÀQUILO");
		
		verifyMatches(match(0, "ÀQUILO".length(), span(0, 1, 0),
				span(0, "ÀQUILO".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_QUANTO_A_AS() throws Exception {
		cg.add("QUANTO=A");
		cg.add("AS");
		
		runOver("QUANTO ÀS");
		
		verifyMatches(match(0, "QUANTO ÀS".length(), span(0, "QUANTO À".length(), 0),  
			span("QUANTO ".length(), "QUANTO ÀS".length(), 1)
		));
	}
	
	@Test
	public void testExpressionContraction_A_AS_QUAIS() throws Exception {
		cg.add("A");
		cg.add("AS=QUAIS");
		
		runOver("ÀS QUAIS");
		
		verifyMatches(match(0, "ÀS QUAIS".length(), span(0, 1, 0),
				span(0, "ÀS QUAIS".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_DEVIDO_A_A() throws Exception {
		cg.add("DEVIDO=A");
		cg.add("A");
		
		runOver("DEVIDO À");
		
		verifyMatches(match(0, "DEVIDO À".length(), span(0, "DEVIDO À".length(), 0), 
				span("DEVIDO ".length(), "DEVIDO À".length(), 1)
			));
	}
	
	@Test
	public void testExpressionContraction_DEVIDO_A_OS_QUAIS() throws Exception {
		cg.add("DEVIDO=A");
		cg.add("OS=QUAIS");
		
		runOver("DEVIDO AOS QUAIS");
		
		verifyMatches(match(0, "DEVIDO AOS QUAIS".length(), span(0, "DEVIDO A".length(), 0), 
				span("DEVIDO A".length(), "DEVIDO AOS QUAIS".length(), 1)
			));
	}
}
