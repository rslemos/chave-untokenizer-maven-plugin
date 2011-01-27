package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

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

	@SuppressWarnings("unchecked")
	public static void verifyMatch(Match match, int expectedFrom, int expectedTo, Span... expectedSpans) {
		assertThat(match, allOf(
				is(not(nullValue(Match.class))),
				is(instanceOf(Match.class)),
				hasProperty("from", equalTo(expectedFrom)),
				hasProperty("to", equalTo(expectedTo)),
				hasProperty("spans", allOf(
						hasNItems(expectedSpans.length),
						hasItems(expectedSpans)
					))
			));
	}

	public static Matcher<Set<Span>> hasNItems(final int count) {
		return new BaseMatcher<Set<Span>>() {

			@SuppressWarnings("rawtypes")
			public boolean matches(Object arg) {
				if (arg != null && arg instanceof Set) {
					return ((Set)arg).size() == count;
				} else
					return false;
			}

			public void describeTo(Description arg) {
				arg.appendText("has ").appendValue(count).appendText(" item(s)");
			}
		};
	}
}
