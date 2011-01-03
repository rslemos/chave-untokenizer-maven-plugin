package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

public class ClumsySGMLParser {
	public enum Event { CHARACTERS, TAG, WHITESPACE }

	private Reader source;
	private CharBuffer buffer;
	
	private Event next;

	public ClumsySGMLParser(Reader source) {
		this(source, 8192);
	}

	public ClumsySGMLParser(Reader source, int bufferSize) {
		this.source = source;
		this.buffer = CharBuffer.allocate(bufferSize);
		this.buffer.flip();
	}

	public boolean hasNext() throws IOException {
		if (!buffer.hasRemaining()) {
			if (!loadMoreData()) {
				next = null;
				return false;
			}
		}
		
		char c = buffer.charAt(0);
		if (isWhitespace(c))
			next = Event.WHITESPACE;
		else if (c == '<')
			next = Event.TAG;
		else
			next = Event.CHARACTERS;
		
		return true;
	}

	public Event next() {
		return next;
	};
	
	public String getCharacters() throws IOException {

		switch (next) {
		case WHITESPACE:
			return fetchWhitespace();
		case CHARACTERS:
			return fetchCharacters();
		default:
				return null;
		}
	}

	public String getLocalName() throws IOException {
		return fetchLocalName();
	}

	private boolean loadMoreData() throws IOException {
		buffer.compact();
		source.read(buffer);
		buffer.flip();
		
		return buffer.hasRemaining();
	}

	private String fetchWhitespace() throws IOException {
		char c;
		StringBuilder result = new StringBuilder();
		while (isWhitespace(c = buffer.get())) {
			result.append(c);
			
			if (!buffer.hasRemaining()) {
				if (!loadMoreData())
					break;
			}
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
			
			if (!buffer.hasRemaining()) {
				if (!loadMoreData())
					break;
			}
		}
		
		if (c == '<')
			buffer.position(buffer.position() - 1);
		
		return result.toString();
	}

	private String fetchLocalName() throws IOException {
		// skip <
		buffer.get();
		if (!buffer.hasRemaining())
			if (!loadMoreData())
				return "";
		
		char c;
		StringBuilder result = new StringBuilder();
		while ((c = buffer.get()) != '>') {
			result.append(c);
			
			if (!buffer.hasRemaining()) {
				if (!loadMoreData())
					break;
			}
		}
		
		return result.toString();
	}
}
