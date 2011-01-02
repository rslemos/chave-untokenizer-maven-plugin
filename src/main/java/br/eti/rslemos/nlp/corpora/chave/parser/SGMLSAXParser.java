package br.eti.rslemos.nlp.corpora.chave.parser;

import static java.lang.Character.isWhitespace;

import java.io.IOException;
import java.io.Reader;
import java.nio.BufferUnderflowException;
import java.nio.CharBuffer;

public class SGMLSAXParser {

	private SGMLSAXHandler handler;
	private CharBuffer buffer = CharBuffer.allocate(8192);

	public SGMLSAXParser(SGMLSAXHandler handler) {
		this.handler = handler;
	}

	public void parse(Reader reader) throws IOException {
		buffer.clear();
		
		reader.read(buffer);

		buffer.flip();
		char c = buffer.get();
		if (isWhitespace(c)) {
			try {
				while (isWhitespace(buffer.get()));
				handler.whitespace(buffer.array(), 0, buffer.position() - 1);
			} catch (BufferUnderflowException e) {
				handler.whitespace(buffer.array(), 0, buffer.position());
			}
		} else if (c == '<') {
			int off = buffer.position();
			while (buffer.get() != '>');
			handler.tag(buffer.array(), off, buffer.position() - off - 1);
		} else {
			try {
				while (buffer.get() != '<');
				handler.text(buffer.array(), 0, buffer.position() - 1);
			} catch (BufferUnderflowException e) {
				handler.text(buffer.array(), 0, buffer.position());
			}
		}
		
		buffer.compact();
		
	}

}
