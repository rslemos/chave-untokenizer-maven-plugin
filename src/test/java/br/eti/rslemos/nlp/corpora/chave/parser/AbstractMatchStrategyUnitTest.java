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

	protected void verifyTextButNoToken(Match result, String text) {
		assertThat(result.getFrom(), is(equalTo(0)));
		assertThat(result.getTo(), is(equalTo(text.length())));
		assertThat(result.getMatches().length, is(equalTo(0)));
	}

	protected void verifyNoToken(Match result) {
		assertThat(result.getFrom(), is(equalTo(0)));
		assertThat(result.getTo(), is(equalTo(0)));
		assertThat(result.getMatches().length, is(equalTo(0)));
	}
	
	protected void verifyTokensInSequence(Match result, String... text) {
		Span[] matches = result.getMatches();
		
		assertThat(matches.length, is(equalTo(text.length)));
		int t = 0;
		for (int i = 0; i < text.length; i++) {
			assertThat(matches[i].entry, is(equalTo(i)));
			assertThat(matches[i].from, is(equalTo(t)));
			assertThat(matches[i].to, is(equalTo(t+text[i].length())));
			t += text[i].length();
		}
		
		assertThat(result.getFrom(), is(equalTo(0)));
		assertThat(result.getTo(), is(equalTo(t)));
	}

	protected void verifyLeftAlignedOverlappingTokens(Match result, String left, String right) {
		assertThat(result.getFrom(), is(equalTo(0)));
		assertThat(result.getTo(), is(equalTo(left.length() + right.length())));
		
		assertThat(result.getMatches()[0].entry, is(equalTo(0)));
		assertThat(result.getMatches()[0].from, is(equalTo(0)));
		assertThat(result.getMatches()[0].to, is(equalTo(left.length())));
		
		assertThat(result.getMatches()[1].entry, is(equalTo(1)));
		assertThat(result.getMatches()[1].from, is(equalTo(0)));
		assertThat(result.getMatches()[1].to, is(equalTo(left.length() + right.length())));
	}

	protected void verifyRightAlignedOverlappingTokens(Match result, String left, String right) {
		assertThat(result.getFrom(), is(equalTo(0)));
		assertThat(result.getTo(), is(equalTo(left.length() + right.length())));
		
		assertThat(result.getMatches()[0].entry, is(equalTo(0)));
		assertThat(result.getMatches()[0].from, is(equalTo(0)));
		assertThat(result.getMatches()[0].to, is(equalTo(left.length() + right.length())));
		
		assertThat(result.getMatches()[1].entry, is(equalTo(1)));
		assertThat(result.getMatches()[1].from, is(equalTo(left.length())));
		assertThat(result.getMatches()[1].to, is(equalTo(left.length() + right.length())));
	}

	protected void verifyFullOverlappingTokens(Match result, String full) {
		assertThat(result.getFrom(), is(equalTo(0)));
		assertThat(result.getTo(), is(equalTo(full.length())));
		
		assertThat(result.getMatches()[0].entry, is(equalTo(0)));
		assertThat(result.getMatches()[0].from, is(equalTo(0)));
		assertThat(result.getMatches()[0].to, is(equalTo(full.length())));
		
		assertThat(result.getMatches()[1].entry, is(equalTo(1)));
		assertThat(result.getMatches()[1].from, is(equalTo(0)));
		assertThat(result.getMatches()[1].to, is(equalTo(full.length())));
	}
	
	protected void verifyIntersectingPseudoToken(Match result, String left, String middle, String right) {
		assertThat(result.getFrom(), is(equalTo(0)));
		assertThat(result.getTo(), is(equalTo(left.length() + middle.length() + right.length())));
		
		assertThat(result.getMatches()[0].entry, is(equalTo(0)));
		assertThat(result.getMatches()[0].from, is(equalTo(0)));
		assertThat(result.getMatches()[0].to, is(equalTo(left.length() + middle.length())));
		
		assertThat(result.getMatches()[1].entry, is(equalTo(1)));
		assertThat(result.getMatches()[1].from, is(equalTo(left.length())));
		assertThat(result.getMatches()[1].to, is(equalTo(left.length() + middle.length() + right.length())));
	}
}
