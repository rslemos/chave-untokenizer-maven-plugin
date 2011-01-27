package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;

public abstract class AbstractMatchStrategyUnitTest {

	private final MatchStrategy strategy;
	protected final List<String> cg = new LinkedList<String>();

	protected AbstractMatchStrategyUnitTest(MatchStrategy strategy) {
		this.strategy = strategy;
	}

	protected Match match(String sgml) {
		Set<Match> set = strategy.match(sgml, cg);
		if (set.isEmpty())
			return null;
		else
			return set.iterator().next();
	}

	public static void verifyMatch(Match match, int from, int to, Span... spans) {
		assertThat(match, is(equalTo(Match.match(from, to, spans))));
	}
}
