package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.MatchResult.result;
import org.junit.experimental.theories.DataPoint;

import br.eti.rslemos.nlp.corpora.chave.parser.MatchResult.Match;
import br.eti.rslemos.tools.test.ObjectTest;

public class MatchObjectTest extends ObjectTest {
	@DataPoint public static Match a0 = result(0, 0, 0);
	@DataPoint public static Match b0 = result(0, 10, 0);
	@DataPoint public static Match c0 = result(0, 10, 1);
	@DataPoint public static Match d0 = result(0, 0, 2);
	@DataPoint public static Match e0 = result(5, 10, 0);
	@DataPoint public static Match f0 = result(5, 10, 1);
	
	@DataPoint public static Match a1 = result(0, 0, 0);
	@DataPoint public static Match b1 = result(0, 10, 0);
	@DataPoint public static Match c1 = result(0, 10, 1);
	@DataPoint public static Match d1 = result(0, 0, 2);
	@DataPoint public static Match e1 = result(5, 10, 0);
	@DataPoint public static Match f1 = result(5, 10, 1);
}
