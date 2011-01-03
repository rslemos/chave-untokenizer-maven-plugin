package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClumsySGMLLexer {
	public enum Event { CHARACTERS, TAG, WHITESPACE }

	private Reader source;
	private CharBuffer buffer;
	
	private Set<Event> filterEvents;
	private String localName;
	private String characters;

	public ClumsySGMLLexer(Reader source) {
		this(source, 8192);
	}

	public ClumsySGMLLexer(Reader source, int bufferSize) {
		this.source = source;
		this.buffer = CharBuffer.allocate(bufferSize);
		this.buffer.flip();
		this.filterEvents = Collections.emptySet();
	}

	public boolean hasNext() throws IOException {
		Event next;
		while (filterEvents.contains(next = classifyInput())) {
			fetch(next, false);
		}
		
		return hasMoreData();
	}

	public Event next() throws IOException {
		Event next;
		do {
			fetch(next = classifyInput(), true);
		} while (filterEvents.contains(next));
		
		return next;
	}

	private void fetch(Event next, boolean overwrite) throws IOException {
		String characters = null;
		String localName = null;
		
		switch (next) {
		case CHARACTERS:
			characters = fetchCharacters();
			break;
		case TAG:
			localName = fetchLocalName();
			break;
		case WHITESPACE:
			characters = fetchWhitespace();
			break;
		}
		
		if (overwrite) {
			this.characters = characters;
			this.localName = localName;
		}
	}

	public String getCharacters() throws IOException {
		return characters; 
	}

	public String getLocalName() throws IOException {
		return localName;
	}

	public void filter(Event... events) {
		this.filterEvents = new HashSet<Event>(Arrays.asList(events));
	}

	private String fetchWhitespace() throws IOException {
		char c;
		StringBuilder result = new StringBuilder();
		while (isWhitespace(c = buffer.get())) {
			result.append(c);
			
			if (!hasMoreData())
				break;
		}
		
		if (!isWhitespace(c))
			buffer.position(buffer.position() - 1);
		
		return result.toString();
	}

	private String fetchCharacters() throws IOException {
		char c;
		StringBuilder result = new StringBuilder();
		while ((c = buffer.get()) != '<') {
			result.append(c);
			
			if (!hasMoreData())
				break;
		}
		
		if (c == '<')
			buffer.position(buffer.position() - 1);
		
		return result.toString();
	}

	private String fetchLocalName() throws IOException {
		// skip <
		buffer.get();
		if (!hasMoreData())
			return "";
		
		char c;
		StringBuilder result = new StringBuilder();
		while ((c = buffer.get()) != '>') {
			result.append(c);
			
			if (!hasMoreData())
				break;
		}
		
		return result.toString();
	}

	private boolean loadMoreData() throws IOException {
		buffer.compact();
		source.read(buffer);
		buffer.flip();
		
		return buffer.hasRemaining();
	}

	private boolean hasMoreData() throws IOException {
		return buffer.hasRemaining() || loadMoreData();
	}

	private Event classifyInput() throws IOException {
		if (!hasMoreData())
			return null;
		
		return classifyInput(buffer.charAt(0));
	}

	private Event classifyInput(char c) {
		if (isWhitespace(c)) {
			return Event.WHITESPACE;
		} else if (c == '<') {
			return Event.TAG;
		} else {
			return Event.CHARACTERS;
		}
	}
}
