package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.result;
import org.junit.experimental.theories.DataPoint;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;
import br.eti.rslemos.tools.test.ObjectTest;

public class SpanObjectTest extends ObjectTest {
	@DataPoint public static Span a0 = result(0, 0, 0);
	@DataPoint public static Span b0 = result(0, 10, 0);
	@DataPoint public static Span c0 = result(0, 10, 1);
	@DataPoint public static Span d0 = result(0, 0, 2);
	@DataPoint public static Span e0 = result(5, 10, 0);
	@DataPoint public static Span f0 = result(5, 10, 1);
	
	@DataPoint public static Span a1 = result(0, 0, 0);
	@DataPoint public static Span b1 = result(0, 10, 0);
	@DataPoint public static Span c1 = result(0, 10, 1);
	@DataPoint public static Span d1 = result(0, 0, 2);
	@DataPoint public static Span e1 = result(5, 10, 0);
	@DataPoint public static Span f1 = result(5, 10, 1);
}
