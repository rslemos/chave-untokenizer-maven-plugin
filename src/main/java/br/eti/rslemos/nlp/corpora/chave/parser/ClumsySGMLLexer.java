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
	
	private Event next;
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
		return hasMoreData();
	}

	public Event next() throws IOException {
		do {
			characters = null;
			localName = null;
			
			char c = buffer.charAt(0);
			if (isWhitespace(c)) {
				next = Event.WHITESPACE;
				characters = fetchWhitespace();
			} else if (c == '<') {
				next = Event.TAG;
				localName = fetchLocalName();
			} else {
				next = Event.CHARACTERS;
				characters = fetchCharacters();
			}
			
		} while (next != null && filterEvents.contains(next));
		
		return next;
	};
	
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

}
