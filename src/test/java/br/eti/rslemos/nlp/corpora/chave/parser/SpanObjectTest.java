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
