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
