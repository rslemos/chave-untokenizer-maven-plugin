package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.StringReader;
import java.util.Arrays;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

public class SGMLSAXParserUnitTest {
	private SGMLSAXHandler handler;
	private SGMLSAXParser parser;

	@Before
	public void setUp() {
		handler = mock(SGMLSAXHandler.class);
		parser = new SGMLSAXParser(handler);
		
	}
	
	@Test
	public void testTextOnly() throws Exception {
		final String TEXT = "this is a text-only SGML";
		
		parser.parse(new StringReader(TEXT));

		verify(handler).text(argThat(isSuperArrayOf(TEXT.toCharArray(), 0, TEXT.length())), eq(0), eq(TEXT.length()));
		verify(handler, never()).tag(anyCharArray(), anyInt(), anyInt());
		verify(handler, never()).whitespace(anyCharArray(), anyInt(), anyInt());
	}

	@Test
	public void testTagOnly() throws Exception {
		final String TEXT = "<A TAG>";
		
		parser.parse(new StringReader(TEXT));

		verify(handler).tag(argThat(isSuperArrayOf(TEXT.substring(1, TEXT.length() - 1).toCharArray(), 1, TEXT.length() - 2)), eq(1), eq(TEXT.length() - 2));
		verify(handler, never()).text(anyCharArray(), anyInt(), anyInt());
		verify(handler, never()).whitespace(anyCharArray(), anyInt(), anyInt());
	}

	@Test
	public void testWhitespaceOnly() throws Exception {
		final String TEXT = "  \t\n\n\t\t\t\t\n\n\n\r\r\r\n\t\n\t\n    \t  \t \n \t\t\n   \r\r\n\r     ";
		
		parser.parse(new StringReader(TEXT));

		verify(handler).whitespace(argThat(isSuperArrayOf(TEXT.toCharArray(), 0, TEXT.length())), eq(0), eq(TEXT.length()));
		verify(handler, never()).text(anyCharArray(), anyInt(), anyInt());
		verify(handler, never()).tag(anyCharArray(), anyInt(), anyInt());
	}
	
	private static Matcher<char[]> isSuperArrayOf(final char[] array, final int off, final int len) {
		return new BaseMatcher<char[]>() {

			public boolean matches(Object arg) {
				char[] actual = new char[len];
				System.arraycopy(arg, off, actual, 0, len);
				return Arrays.equals(actual, array);
			}

			public void describeTo(Description dest) {
				dest.appendText("is super-array of ");
				dest.appendText(Arrays.toString(array));
			}
			
		};
	}

	private static char[] anyCharArray() {
		return (char[]) anyObject();
	}
}
