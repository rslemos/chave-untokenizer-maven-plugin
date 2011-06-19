package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.Document;

import java.io.IOException;
import java.util.Formatter;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;


@RunWith(Theories.class)
public class UntokenizerFunctionalTest extends UntokenizerAbstractFunctionalTest implements WorkingData, DataThatNeedDamerauLevenshtein, NotWorkingData, BigCases {
	
	private Untokenizer parser;

	@Before
	public void setUp() {
		parser = new Untokenizer();
	}
	
	@Override
	protected void untokenize(Document document, List<CGEntry> cgLines) throws IOException, ParserException {
		parser.parse(cgLines, document);
	}

	@Test
	public void testUniverseCount() throws Exception {
		System.err.printf("Memory: %d\n", Runtime.getRuntime().totalMemory());
		String basename = "/pt_BR/CHAVEFolha/1994/01/01/002";
		
		List<CGEntry> cgLines = CGEntry.loadFromReader(open(basename + ".cg"));
		
		Document document = GateLoader.load(UntokenizerUnitTest.class.getResource(basename + ".sgml"), "UTF-8");
		
		List<String> cg = CGEntry.onlyKeys(cgLines);
		String text = document.getContent().toString();
		System.err.printf("Text: %d characters; CG: %d entries\n", text.length(), cg.size());
		
//		System.out.println("<html>\n<head>\n<style type=\"text/css\">\ntd { border: 1pt solid; }\n</style>\n</head>\n<body>\n<table>");
//		System.out.println("<tr>");
//		System.out.print("<td></td><td></td>");
//		for (char c : text.toCharArray()) {
//			System.out.printf("<td>%c</td>", c);
//		}
//		System.out.println();
//		System.out.println("</tr>");
		
		@SuppressWarnings("unchecked")
		Set<Span>[] matchesByEntry = new Set[cg.size()];
		for (int i = 0; i < matchesByEntry.length; i++) {
			matchesByEntry[i] = new TreeSet<Span>(new Span.SpanComparatorByOffsets());
		}
		
		final MatchStrategy[] STRATEGIES = new MatchStrategy[] {
				new ContractionDeMatchStrategy(),
				new ContractionEmMatchStrategy(),
				new ContractionAMatchStrategy(),
				new ContractionPorMatchStrategy(),
				new ContractionParaMatchStrategy(),
				new ContractionComMatchStrategy(),
				new EncliticMatchStrategy(),
				new DirectMatchStrategy(),
			};
		
		//@SuppressWarnings("unchecked")
		//Set<Match>[] matches = new Set[STRATEGIES.length];
		
		for (int i = 0; i < STRATEGIES.length; i++) {
			MatchStrategy strategy = STRATEGIES[i];
			
			long t0 = System.nanoTime();
			strategy.setData(new DamerauLevenshteinTextMatcher(text), cg);
			Set<Match> match = strategy.matchAll();

			long t1 = System.nanoTime();

			for (Match match2 : match) {
				for (Span span : match2.getSpans()) {
					matchesByEntry[span.entry].add(span);
				}
			}
			
			long t2 = System.nanoTime();
			
			int size = match.size();
			System.err.printf("%-28s (%13fms + %9fms) : %4d = %11f²\n", strategy.getClass().getSimpleName(), (float)(t1 - t0)/1000000, (float)(t2 - t1)/1000000, size, Math.sqrt(size));
		}

		long t0 = System.nanoTime();
		for (int i = 0; i < matchesByEntry.length; i++) {
			System.err.printf("%4d. (%-15s) %3d : %s\n", i, cg.get(i), matchesByEntry[i].size(), toString(matchesByEntry[i]));
//			System.out.println("<tr>");
//			System.out.printf("<td>%d</td><td>%s</td>", i, cg.get(i));
//			int j = 0;
//			for (Span span : matchesByEntry[i]) {
//				for (; j<span.from; j++)
//					System.out.print("<td></td>");
//				System.out.printf("<td colspan=\"%d\" style=\"bgcolor: blue;\">&nbsp;</td>", span.to - span.from);
//				j = span.to;
//			}
//			System.out.println("\n</tr>");
		}
		
//		System.out.println("</table></body></html>");
		long t1 = System.nanoTime();
		System.err.printf("%fms\n", (float)(t1 - t0)/1000000);

		int prevsample = 0;
		int count = 0;
		
		long sum = 0;
		double sumsq = 0;
		
		float A = 0;
		float Q = 0;
		int i;
		
		for (i = 0; i < matchesByEntry.length; i++) {
			if (matchesByEntry[i].size() == 1) {
				count++;
				
				int length = i - prevsample;
				
				sum += length;
				sumsq += length*length;
				prevsample = i;

				// http://en.wikipedia.org/wiki/Standard_deviation#Rapid_calculation_methods
				Q += (length - A)*(length - (A += (length - A)/count));
			}
		}

		count++;
		int length = i - prevsample;
		sum += length;
		
		// http://en.wikipedia.org/wiki/Standard_deviation#Rapid_calculation_methods
		Q += (length - A)*(length - (A += (length - A)/count));

		System.err.printf("Fixed entries: %d\n", count);
		System.err.printf("Mean length: %f\n", A);
		System.err.printf("Mean length: %f\n", (float)sum/count);
		System.err.printf("Standard deviation: %f\n", Math.sqrt(Q/(count-1)));
		System.err.printf("Standard deviation: %f\n", Math.sqrt(count*sumsq - (double)sum*sum)/count);
		
		System.err.printf("Memory: %d\n", Runtime.getRuntime().totalMemory());
	}

	private String toString(Set<Span> spans) {
		Formatter result = new Formatter();
		
		for (Span span : spans) {
			result.format("%3d-%-3d (%2d); ", span.from, span.to, span.to - span.from);
		}
		
		return result.toString();
	}


}
