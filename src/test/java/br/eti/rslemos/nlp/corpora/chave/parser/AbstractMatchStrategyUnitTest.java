package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class AbstractMatchStrategyUnitTest {

	private final MatchStrategy strategy;
	protected final List<String> cg = new LinkedList<String>();

	private Set<Match> matches;

	protected AbstractMatchStrategyUnitTest(MatchStrategy strategy) {
		this.strategy = strategy;
	}

	protected void runOver(String sgml) {
		strategy.setText(sgml);
		strategy.setCG(cg);
		matches = strategy.match();
	}

	public void verifyMatches(Match... matches) {
		assertThat(this.matches.size(), is(equalTo(matches.length)));
		assertThat(this.matches, hasItems(matches));
	}
}
