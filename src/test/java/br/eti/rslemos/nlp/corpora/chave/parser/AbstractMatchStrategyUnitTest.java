package br.eti.rslemos.nlp.corpora.chave.parser;

import static br.eti.rslemos.nlp.corpora.chave.parser.Match.Span.span;
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

	protected void verifyTextButNoToken(Match result, String text) {
		verifyMatch(result, 0, text.length());
	}

	protected void verifyNoToken(Match result) {
		verifyMatch(result, 0, 0);
	}
	
	protected void verifyTokensInSequence(Match result, String... text) {
		Span[] expectedSpans = new Span[text.length];
		
		int t = 0;
		for (int i = 0; i < text.length; i++) {
			expectedSpans[i] = span(t, t+text[i].length(), i);
			t += text[i].length();
		}

		verifyMatch(result, 0, t, expectedSpans);
	}

	protected void verifyLeftAlignedOverlappingTokens(Match result, String left, String right) {
		verifyMatch(result, 0, left.length() + right.length(),
				span(0, left.length(), 0),
				span(0, left.length() + right.length(), 1)
			);
	}

	protected void verifyRightAlignedOverlappingTokens(Match result, String left, String right) {
		verifyMatch(result, 0, left.length() + right.length(), 
				span(0, left.length() + right.length(), 0),
				span(left.length(), left.length() + right.length(), 1)
			);
	}

	protected void verifyFullOverlappingTokens(Match result, String full) {
		verifyMatch(result, 0, full.length(), span(0, full.length(), 0), span(0, full.length(), 1));
	}
	
	protected void verifyIntersectingPseudoToken(Match result, String left, String middle, String right) {
		verifyMatch(result, 0, left.length() + middle.length() + right.length(),  
				span(0, left.length() + middle.length(), 0),
				span(left.length(), left.length() + middle.length() + right.length(), 1)
			);
	}

	private void verifyMatch(Match match, int expectedFrom, int expectedTo, Span... expectedSpans) {
		assertThat(match.getFrom(), is(equalTo(expectedFrom)));
		assertThat(match.getTo(), is(equalTo(expectedTo)));
		
		Set<Span> actualSpans = match.getMatches();
		assertThat(actualSpans.size(), is(equalTo(expectedSpans.length)));
		assertThat(actualSpans, hasItems(expectedSpans));
	}
}
