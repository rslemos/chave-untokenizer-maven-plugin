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
