package br.eti.rslemos.nlp.corpora.chave.parser;

import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.eti.rslemos.nlp.corpora.chave.parser.Parser.Entry;

public abstract class AbstractMatchStrategyUnitTest {

	@Mock
	protected Handler handler;
	
	protected MatchStrategy strategy;

	protected void setUp(MatchStrategy strategy) {
		this.strategy = strategy;
		MockitoAnnotations.initMocks(this);
	}

	protected void matchAndApply(List<Entry<String, String>> cg, String sgml) {
		strategy.match(CharBuffer.wrap(sgml), new LinkedList<Entry<String, String>>(cg)).apply(handler);
	}

}
