package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CachingTextMatcher extends TextMatcherDecorator {

	private Map<TextMatcherKey, Set<Match>> cache;

	public CachingTextMatcher(TextMatcher concreteTextMatcher) {
		super(concreteTextMatcher);
		
		cache = new HashMap<TextMatcherKey, Set<Match>>();
	}

	@Override
	public Set<Match> matchKey(String key, Span... inSpans) {
		TextMatcherKey k = new TextMatcherKey(key, inSpans);
		
		Set<Match> result = cache.get(k);
		
		if (result == null) {
			result = super.matchKey(key, inSpans);
			cache.put(k, result);
		}
		
		return result;
	}


	private static class TextMatcherKey {

		private final String key;
		private final Span[] inSpans;

		public TextMatcherKey(String key, Span[] inSpans) {
			this.key = key;
			this.inSpans = inSpans;
		}

		@Override
		public int hashCode() {
			int hashCode = 0;
			
			hashCode += key != null ? key.hashCode() : 0;
			hashCode *= 3;
			hashCode += inSpans != null ? Arrays.deepHashCode(inSpans) : 0;
			
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			
			if (obj == this)
				return true;
			
			if (!(obj instanceof TextMatcherKey))
				return false;
			
			TextMatcherKey other = (TextMatcherKey)obj;
			
			return (key != null ? key.equals(other.key) : other.key == null) &&
					(inSpans != null ? Arrays.deepEquals(inSpans, other.inSpans) : other.inSpans == null);
		}
		
		
	}
}
