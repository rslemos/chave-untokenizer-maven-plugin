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

public class ContractionComMatchStrategyUnitTest extends ContractionMatchStrategyUnitTest {
	
	// com + x
	@Test
	public void testContraction_com_mim() throws Exception {
		cg.add("com");
		cg.add("mim");
		
		runOver("comigo");
		
		verifyMatches(match(0, "comigo".length(), span(0, "com".length(), 0),  
				span("co".length(), "comigo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_com_ti() throws Exception {
		cg.add("com");
		cg.add("ti");
		
		runOver("contigo");
		
		verifyMatches(match(0, "contigo".length(), span(0, "con".length(), 0), 
				span("con".length(), "contigo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_com_si() throws Exception {
		cg.add("com");
		cg.add("si");
		
		runOver("consigo");
		
		verifyMatches(match(0, "consigo".length(), span(0, "con".length(), 0), 
				span("con".length(), "consigo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_com_nos() throws Exception {
		cg.add("com");
		cg.add("nos");
		
		runOver("conosco");
		
		verifyMatches(match(0, "conosco".length(), span(0, "con".length(), 0),  
				span("co".length(), "conosco".length(), 1)
			));
	}
	
	@Test
	public void testContraction_com_vos() throws Exception {
		cg.add("com");
		cg.add("vos");
		
		runOver("convosco");
		
		verifyMatches(match(0, "convosco".length(), span(0, "con".length(), 0), 
				span("con".length(), "convosco".length(), 1)
			));
	}
	
	// Com + x
	@Test
	public void testContraction_Com_mim() throws Exception {
		cg.add("Com");
		cg.add("mim");
		
		runOver("Comigo");
		
		verifyMatches(match(0, "Comigo".length(), span(0, "Com".length(), 0),  
				span("Co".length(), "Comigo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Com_ti() throws Exception {
		cg.add("Com");
		cg.add("ti");
		
		runOver("Contigo");
		
		verifyMatches(match(0, "Contigo".length(), span(0, "Con".length(), 0), 
				span("Con".length(), "Contigo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Com_si() throws Exception {
		cg.add("Com");
		cg.add("si");
		
		runOver("Consigo");
		
		verifyMatches(match(0, "Consigo".length(), span(0, "Con".length(), 0), 
				span("Con".length(), "Consigo".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Com_nos() throws Exception {
		cg.add("Com");
		cg.add("nos");
		
		runOver("Conosco");
		
		verifyMatches(match(0, "Conosco".length(), span(0, "Con".length(), 0),  
				span("Co".length(), "Conosco".length(), 1)
			));
	}
	
	@Test
	public void testContraction_Com_vos() throws Exception {
		cg.add("Com");
		cg.add("vos");
		
		runOver("Convosco");
		
		verifyMatches(match(0, "Convosco".length(), span(0, "Con".length(), 0), 
				span("Con".length(), "Convosco".length(), 1)
			));
	}
	
	// COM + X
	@Test
	public void testCONtraction_COM_MIM() throws Exception {
		cg.add("COM");
		cg.add("MIM");
		
		runOver("COMIGO");
		
		verifyMatches(match(0, "COMIGO".length(), span(0, "COM".length(), 0),  
				span("CO".length(), "COMIGO".length(), 1)
			));
	}
	
	@Test
	public void testCONtraction_COM_TI() throws Exception {
		cg.add("COM");
		cg.add("TI");
		
		runOver("CONTIGO");
		
		verifyMatches(match(0, "CONTIGO".length(), span(0, "CON".length(), 0), 
				span("CON".length(), "CONTIGO".length(), 1)
			));
	}
	
	@Test
	public void testCONtraction_COM_SI() throws Exception {
		cg.add("COM");
		cg.add("SI");
		
		runOver("CONSIGO");
		
		verifyMatches(match(0, "CONSIGO".length(), span(0, "CON".length(), 0), 
				span("CON".length(), "CONSIGO".length(), 1)
			));
	}
	
	@Test
	public void testCONtraction_COM_NOS() throws Exception {
		cg.add("COM");
		cg.add("NOS");
		
		runOver("CONOSCO");
		
		verifyMatches(match(0, "CONOSCO".length(), span(0, "CON".length(), 0),  
				span("CO".length(), "CONOSCO".length(), 1)
			));
	}
	
	@Test
	public void testCONtraction_COM_VOS() throws Exception {
		cg.add("COM");
		cg.add("VOS");
		
		runOver("CONVOSCO");
		
		verifyMatches(match(0, "CONVOSCO".length(), span(0, "CON".length(), 0), 
				span("CON".length(), "CONVOSCO".length(), 1)
			));
	}
}
