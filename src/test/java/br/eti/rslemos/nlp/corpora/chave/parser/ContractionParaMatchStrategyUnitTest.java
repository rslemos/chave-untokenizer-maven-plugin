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

public class ContractionParaMatchStrategyUnitTest extends ContractionMatchStrategyUnitTest {
	
	// para + x
	@Test
	public void testContraction_para_o() throws Exception {
		cg.add("para");
		cg.add("o");
		
		runOver("pro");
		
		verifyMatches(match(0, "pro".length(), span(0, "pr".length(), 0), 
				span("pr".length(), "pro".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_a() throws Exception {
		cg.add("para");
		cg.add("a");
		
		runOver("pra");
		
		verifyMatches(match(0, "pra".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "pra".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_os() throws Exception {
		cg.add("para");
		cg.add("os");
		
		runOver("pros");
		
		verifyMatches(match(0, "pros".length(), span(0, "pr".length(), 0), 
				span("pr".length(), "pros".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_as() throws Exception {
		cg.add("para");
		cg.add("as");
		
		runOver("pras");
		
		verifyMatches(match(0, "pras".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "pras".length(), 1)
			));
	}

	@Test
	public void testContraction_para_aquele() throws Exception {
		cg.add("para");
		cg.add("aquele");
		
		runOver("praquele");
		
		verifyMatches(match(0, "praquele".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praquele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_aquela() throws Exception {
		cg.add("para");
		cg.add("aquela");
		
		runOver("praquela");
		
		verifyMatches(match(0, "praquela".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praquela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_aqueles() throws Exception {
		cg.add("para");
		cg.add("aqueles");
		
		runOver("praqueles");
		
		verifyMatches(match(0, "praqueles".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praqueles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_aquelas() throws Exception {
		cg.add("para");
		cg.add("aquelas");
		
		runOver("praquelas");
		
		verifyMatches(match(0, "praquelas".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praquelas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_para_aquilo() throws Exception {
		cg.add("para");
		cg.add("aquilo");
		
		runOver("praquilo");
		
		verifyMatches(match(0, "praquilo".length(), span(0, "pra".length(), 0), 
				span("pr".length(), "praquilo".length(), 1)
			));
	}
	
	// Para + x
	@Test
	public void testContraction_Para_o() throws Exception {
		cg.add("Para");
		cg.add("o");
		
		runOver("Pro");
		
		verifyMatches(match(0, "Pro".length(), span(0, "Pr".length(), 0), 
				span("Pr".length(), "Pro".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Para_a() throws Exception {
		cg.add("Para");
		cg.add("a");
		
		runOver("Pra");
		
		verifyMatches(match(0, "Pra".length(), span(0, "Pra".length(), 0), 
				span("Pr".length(), "Pra".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Para_os() throws Exception {
		cg.add("Para");
		cg.add("os");
		
		runOver("Pros");
		
		verifyMatches(match(0, "Pros".length(), span(0, "Pr".length(), 0), 
				span("Pr".length(), "Pros".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Para_as() throws Exception {
		cg.add("Para");
		cg.add("as");
		
		runOver("Pras");
		
		verifyMatches(match(0, "Pras".length(), span(0, "Pra".length(), 0), 
				span("Pr".length(), "Pras".length(), 1)
			));
	}

	@Test
	public void testContraction_Para_aquele() throws Exception {
		cg.add("Para");
		cg.add("aquele");
		
		runOver("Praquele");
		
		verifyMatches(match(0, "Praquele".length(), span(0, "Pra".length(), 0), 
				span("Pr".length(), "Praquele".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Para_aquela() throws Exception {
		cg.add("Para");
		cg.add("aquela");
		
		runOver("Praquela");
		
		verifyMatches(match(0, "Praquela".length(), span(0, "Pra".length(), 0), 
				span("Pr".length(), "Praquela".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Para_aqueles() throws Exception {
		cg.add("Para");
		cg.add("aqueles");
		
		runOver("Praqueles");
		
		verifyMatches(match(0, "Praqueles".length(), span(0, "Pra".length(), 0), 
				span("Pr".length(), "Praqueles".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Para_aquelas() throws Exception {
		cg.add("Para");
		cg.add("aquelas");
		
		runOver("Praquelas");
		
		verifyMatches(match(0, "Praquelas".length(), span(0, "Pra".length(), 0), 
				span("Pr".length(), "Praquelas".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Para_aquilo() throws Exception {
		cg.add("Para");
		cg.add("aquilo");
		
		runOver("Praquilo");
		
		verifyMatches(match(0, "Praquilo".length(), span(0, "Pra".length(), 0), 
				span("Pr".length(), "Praquilo".length(), 1)
			));
	}
	
	// PARA + X
	@Test
	public void testContraction_PARA_O() throws Exception {
		cg.add("PARA");
		cg.add("O");
		
		runOver("PRO");
		
		verifyMatches(match(0, "PRO".length(), span(0, "PR".length(), 0), 
				span("PR".length(), "PRO".length(), 1)
			));
	}
	
	@Test
	public void testContraction_PARA_A() throws Exception {
		cg.add("PARA");
		cg.add("A");
		
		runOver("PRA");
		
		verifyMatches(match(0, "PRA".length(), span(0, "PRA".length(), 0), 
				span("PR".length(), "PRA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_PARA_OS() throws Exception {
		cg.add("PARA");
		cg.add("OS");
		
		runOver("PROS");
		
		verifyMatches(match(0, "PROS".length(), span(0, "PR".length(), 0), 
				span("PR".length(), "PROS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_PARA_AS() throws Exception {
		cg.add("PARA");
		cg.add("AS");
		
		runOver("PRAS");
		
		verifyMatches(match(0, "PRAS".length(), span(0, "PRA".length(), 0), 
				span("PR".length(), "PRAS".length(), 1)
			));
	}

	@Test
	public void testContraction_PARA_AQUELE() throws Exception {
		cg.add("PARA");
		cg.add("AQUELE");
		
		runOver("PRAQUELE");
		
		verifyMatches(match(0, "PRAQUELE".length(), span(0, "PRa".length(), 0), 
				span("PR".length(), "PRAQUELE".length(), 1)
			));
	}
	
	@Test
	public void testContraction_PARA_AQUELA() throws Exception {
		cg.add("PARA");
		cg.add("AQUELA");
		
		runOver("PRAQUELA");
		
		verifyMatches(match(0, "PRAQUELA".length(), span(0, "PRa".length(), 0), 
				span("PR".length(), "PRAQUELA".length(), 1)
			));
	}
	
	@Test
	public void testContraction_PARA_AQUELES() throws Exception {
		cg.add("PARA");
		cg.add("AQUELES");
		
		runOver("PRAQUELES");
		
		verifyMatches(match(0, "PRAQUELES".length(), span(0, "PRa".length(), 0), 
				span("PR".length(), "PRAQUELES".length(), 1)
			));
	}
	
	@Test
	public void testContraction_PARA_AQUELAS() throws Exception {
		cg.add("PARA");
		cg.add("AQUELAS");
		
		runOver("PRAQUELAS");
		
		verifyMatches(match(0, "PRAQUELAS".length(), span(0, "PRa".length(), 0), 
				span("PR".length(), "PRAQUELAS".length(), 1)
			));
	}
	
	@Test
	public void testContraction_PARA_AQUILO() throws Exception {
		cg.add("PARA");
		cg.add("AQUILO");
		
		runOver("PRAQUILO");
		
		verifyMatches(match(0, "PRAQUILO".length(), span(0, "PRa".length(), 0), 
				span("PR".length(), "PRAQUILO".length(), 1)
			));
	}
}
