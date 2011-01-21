package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.List;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public abstract class AbstractMatchStrategyUnitTest {

	@Mock
	private Handler handler;
	
	protected MatchStrategy strategy;
	protected List<Entry<String, String>> cg;

	private InOrder order;

	protected void setUp(final MatchStrategy strategy) {
		this.strategy = new MatchStrategy() {

			public MatchResult match(CharBuffer buffer, List<Entry<String, String>> cg, boolean noMoreData) {
				return strategy.match(buffer, new LinkedList<Entry<String, String>>(cg), noMoreData);
			}
			
		};
		
		cg = new LinkedList<Entry<String, String>>();
		MockitoAnnotations.initMocks(this);
		order = inOrder(handler);
	}

	protected MatchResult match(String sgml) {
		return strategy.match(CharBuffer.wrap(sgml), cg, true);
	}

	protected void verifyTextButNoToken(MatchResult result, String text) {
		result.apply(handler);
		
		order.verify(handler).characters(text.toCharArray());
		
		verifyNoMoreInteractions(handler);
	}

	protected void verifyNoToken() {
		verifyNoMoreInteractions(handler);
	}
	
	protected void verifyNoToken(MatchResult result) {
		result.apply(handler);
		verifyNoMoreInteractions(handler);
	}
	
	protected void verifyTokensInSequence(MatchResult result, String... text) {
		result.apply(handler);
		
		for (int i = 0; i < text.length; i++) {
			order.verify(handler).startToken(cg.get(i).getValue());
			order.verify(handler).characters(text[i].toCharArray());
			order.verify(handler).endToken();
		}
	}

	protected void verifyLeftAlignedOverlappingTokens(MatchResult result, String left, String right) {
		result.apply(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters(left.toCharArray());
		order.verify(handler).endToken();
		order.verify(handler).characters(right.toCharArray());
		order.verify(handler).endToken();
	}

	protected void verifyRightAlignedOverlappingTokens(MatchResult result, String left, String right) {
		result.apply(handler);
		
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters(left.toCharArray());
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).characters(right.toCharArray());
		order.verify(handler, times(2)).endToken();
	}

	protected void verifyFullOverlappingTokens(MatchResult result, String full) {
		result.apply(handler);
		
		order.verify(handler).startToken(cg.get(1).getValue());
		order.verify(handler).startToken(cg.get(0).getValue());
		order.verify(handler).characters(full.toCharArray());
		order.verify(handler, times(2)).endToken();
	}
	
	protected void verifyIntersectingPseudoToken(MatchResult result, String left, String middle, String right) {
		result.apply(handler);
		
		order.verify(handler).startPseudoToken(cg.get(0).getValue());
		order.verify(handler).characters(left.toCharArray());
		order.verify(handler).startPseudoToken(cg.get(1).getValue());
		order.verify(handler).characters(middle.toCharArray());
		order.verify(handler).endPseudoToken();
		order.verify(handler).characters(right.toCharArray());
		order.verify(handler).endPseudoToken();
	}
}
