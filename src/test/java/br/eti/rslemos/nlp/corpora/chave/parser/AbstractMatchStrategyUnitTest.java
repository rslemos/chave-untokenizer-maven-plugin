package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
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

	public static void verifyMatch(Match match, int expectedFrom, int expectedTo, Span... expectedSpans) {
		assertThat(match.getFrom(), is(equalTo(expectedFrom)));
		assertThat(match.getTo(), is(equalTo(expectedTo)));
		
		Set<Span> actualSpans = match.getSpans();
		assertThat(actualSpans.size(), is(equalTo(expectedSpans.length)));
		assertThat(actualSpans, hasItems(expectedSpans));
	}
}
