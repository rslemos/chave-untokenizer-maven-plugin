package br.eti.rslemos.nlp.corpora.chave.parser;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

import br.eti.rslemos.nlp.corpora.chave.parser.ClumsySGMLLexer.Event;

public class ClumsySGMLFilter {

	public static void skipToTag(ClumsySGMLLexer parser, String localName) throws IOException {
		readToTag(parser, localName).close();
	}

	public static Reader readToTag(final ClumsySGMLLexer lexer, final String localName) throws IOException {
		return new Reader() {
			CharBuffer buffer = CharBuffer.allocate(8192);
			boolean done = !lexer.hasNext();
			
			{
				lexer.filter();
				buffer.flip();
			}
			
			@Override
			public int read(char[] cbuf, int off, int len) throws IOException {
				if (done)
					return -1;
				
				if (buffer.remaining() == 0) {
					buffer.compact();
					try {
						while (true) {
							String toAppend;
							if (lexer.currentEvent() == Event.TAG) {
								if (localName.equals(lexer.getLocalName())) {
									done = true;
									break;
								}
								toAppend = "<" + lexer.getLocalName() + ">";
							} else {
								toAppend = lexer.getCharacters();
							}
							if (buffer.remaining() >= toAppend.length()) {
								buffer.append(toAppend);
								if (lexer.hasNext())
									lexer.next();
								else {
									done = true;
									break;
								}
							} else {
								break;
							}
						}
					} finally {
						buffer.flip();
					}
				}
				
				len = Math.min(len, buffer.remaining());
				buffer.get(cbuf, off, len);
				return len;
			}

			@Override
			public void close() throws IOException {
				char[] buffer = new char[8192];
				
				while(read(buffer, 0, buffer.length) != -1);
			}
			
		};
	}

}
