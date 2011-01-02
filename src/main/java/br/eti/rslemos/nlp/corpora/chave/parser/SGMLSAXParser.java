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
		
		while (reader.read(buffer) != -1 || buffer.hasRemaining()) {
			System.out.printf("limit: %d\n", buffer.limit());
			System.out.printf("position: %d\n", buffer.position());
			buffer.flip();
			if (!buffer.hasRemaining())
				break;
			
			System.out.printf("limit: %d\n", buffer.limit());
			System.out.printf("position: %d\n", buffer.position());
			char c = buffer.get();
			if (isWhitespace(c)) {
				int length;
				try {
					while (isWhitespace(buffer.get()));
					length = buffer.position() - 1;
				} catch (BufferUnderflowException e) {
					length = buffer.position();
				}
				buffer.rewind();
				char[] c1 = new char[length];
				buffer.get(c1);
				handler.whitespace(new String(c1));
			} else if (c == '<') {
				while (buffer.get() != '>');
				int length = buffer.position();
				
				buffer.rewind();
				char[] c1 = new char[length];
				buffer.get(c1);
				handler.tag(new String(c1, 1, length - 2));
			} else {
				int length;
				try {
					while (buffer.get() != '<');
					length = buffer.position() - 1;
				} catch (BufferUnderflowException e) {
					length = buffer.position();
				}
				buffer.rewind();
				char[] c1 = new char[length];
				buffer.get(c1);
				handler.text(new String(c1));
			}
			
			buffer.compact();
		}
	}

}
