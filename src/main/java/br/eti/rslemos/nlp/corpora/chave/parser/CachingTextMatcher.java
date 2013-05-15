/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "chave-untokenizer-maven-plugin"
 * Copyright 2013  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.nlp.corpora.chave.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CachingTextMatcher extends TextMatcherDecorator {

	private Map<TextMatcherKey, List<FragmentMatchSet>> cache;

	public CachingTextMatcher(TextMatcher concreteTextMatcher) {
		super(concreteTextMatcher);
		
		cache = new HashMap<TextMatcherKey, List<FragmentMatchSet>>();
	}

	@Override
	public Set<Match> matchKey(int from, int to, String key, Span... inSpans) {
		TextMatcherKey k = new TextMatcherKey(key, inSpans);
		
		List<FragmentMatchSet> fragments = cache.get(k);
		
		if (fragments == null) {
			fragments = new ArrayList<FragmentMatchSet>();
			cache.put(k, fragments);
		}
		
		if (fragments.isEmpty()) {
			fragments.add(new FragmentMatchSet(from, to, super.matchKey(from, to, key, inSpans)));
		}
		
		Set<Match> fullResult = new LinkedHashSet<Match>();
		
		BitSet bs = new BitSet(to);
		
		bs.set(from, to);
		for (FragmentMatchSet fragment : fragments) {
//			if (fragment.from == from && fragment.to == to)
//				return fragment.result;

			int maxFrom = Math.max(from, fragment.from);
			int minTo = Math.min(to, fragment.to);
			
			if (minTo > maxFrom) {
				bs.clear(maxFrom, minTo);
				fullResult.addAll(filter(fragment, maxFrom, minTo));
			}
		}
		
		from = bs.nextSetBit(0);
		
		while (from >= 0) {
			to = bs.nextClearBit(from);
			
			Set<Match> partial = super.matchKey(from, to, key, inSpans);
			fragments.add(new FragmentMatchSet(from, to, partial));
			fullResult.addAll(partial);
			
			from = bs.nextSetBit(to);
		}

		
		return fullResult;
	}


	private Set<Match> filter(FragmentMatchSet fragment, int from, int to) {
		if (fragment.from == from && fragment.to == to)
			return fragment.result;
		
		Set<Match> result = new HashSet<Match>();
		
		for (Match match : fragment.result) {
			if (match.from >= from && match.to <= to)
				result.add(match);
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
	
	private static class FragmentMatchSet {
		private final int from;
		private final int to;
		private final Set<Match> result;
		
		private FragmentMatchSet(int from, int to, Set<Match> result) {
			this.from = from;
			this.to = to;
			this.result = result;
		}
	}
}
