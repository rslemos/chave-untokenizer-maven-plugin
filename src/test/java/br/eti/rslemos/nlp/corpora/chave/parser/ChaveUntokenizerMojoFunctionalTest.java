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

import java.io.File;

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ChaveUntokenizerMojoFunctionalTest {

	@Test
	public void test() throws Exception {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(), "/chave-untokenizer-test");
		Verifier verifier = new Verifier(testDir.getAbsolutePath());
		verifier.executeGoal("br.eti.rslemos.nlp.corpora:chave-untokenizer-maven-plugin:untokenize");
		verifier.verifyErrorFreeLog();
		
	}
}
