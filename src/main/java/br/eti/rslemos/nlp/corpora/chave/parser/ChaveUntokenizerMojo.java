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

import gate.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.BitSet;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.FileUtils;

import br.eti.rslemos.nlp.corpora.chave.parser.Match.Span;

/**
 * @goal untokenize
 * @phase compile
 */
public class ChaveUntokenizerMojo extends AbstractMojo {

    /**
     * The character encoding scheme to be applied when filtering resources.
     *
     * @parameter expression="${encoding}" default-value="${project.build.sourceEncoding}"
     */
    protected String encoding;

    /**
     * The output directory into which to copy the resources.
     *
     * @parameter default-value="${project.build.outputDirectory}"
     * @required
     */
    private File outputDirectory;

    /**
     * Overwrite existing files even if the destination files are newer.
     * @parameter expression="${maven.resources.overwrite}" default-value="false"
     * @since 2.3
     */
    //private boolean overwrite;
    
    /**
     * Copy any empty directories included in the Ressources.
     * @parameter expression="${maven.resources.includeEmptyDirs}" default-value="false"
     * @since 2.3
     */    
    protected boolean includeEmptyDirs;
    
    /**
     * Whether to escape backslashes and colons in windows-style paths.
     * @parameter expression="${maven.resources.escapeWindowsPaths}" default-value="true"
     * @since 2.4
     */
    protected boolean escapeWindowsPaths;
    
    /**
     * The list of resources we want to transfer.
     *
     * @parameter default-value="${project.resources}"
     * @required
     * @readonly
     */
    private List<Resource> resources;

	public void execute() throws MojoExecutionException, MojoFailureException {
		Log log = getLog();
		log.info("Hello, world.");
		
		for (Resource resource : resources) {
			//process(new File(resource.getDirectory()), new File(resource.getDirectory()));
			try {
				getLog().info("Processing " + resource.getDirectory());
				getLog().info("Collecting filenames");
				
				@SuppressWarnings("unchecked")
				List<String> fileNames = FileUtils.getFileNames(new File(resource.getDirectory()), "**/*.sgml", "", false);
				
				getLog().info("Sorting");
				Collections.sort(fileNames);
				getLog().info("Untokenizing");
				for (Iterator<String> iterator = fileNames.iterator(); iterator.hasNext();) {
					String fileName = (String) iterator.next();
					
					File inText = new File(resource.getDirectory(), fileName);
					File inCG = new File(resource.getDirectory(), FileUtils.removeExtension(fileName) + ".cg");
					File out = new File(outputDirectory, FileUtils.removeExtension(fileName) + ".xml");

					getLog().info(out.toString());
					out.getParentFile().mkdirs();
					untokenize(inText, inCG, out);
				}
			} catch (IOException e) {
				throw new MojoExecutionException("Iterating throw input directories", e);
			}
		}
	}

	private void untokenize(File inText, File inCG, File outDoc) {
		try {
			Document document = GateLoader.load(inText.toURI().toURL(), encoding);
			List<CGEntry> cg = CGEntry.loadFromReader(new InputStreamReader(new FileInputStream(inCG), encoding));

			try {
				String text = document.getContent().toString();
				
				document.getFeatures().put("br.eti.rslemos.nlp.corpora.chave.parser.textLength", text.length());
				document.getFeatures().put("br.eti.rslemos.nlp.corpora.chave.parser.cgSize", cg.size());
				
				Untokenizer untokenizer = new Untokenizer();
				long before = System.nanoTime();
				untokenizer.untokenize(document, cg);
				long after = System.nanoTime();
				
				document.getFeatures().put("br.eti.rslemos.nlp.corpora.chave.parser.processingTime", after - before);
				
				List<Span>[] spans = untokenizer.getProcessingResults();
				BitSet fixedEntries = Untokenizer.getFixedEntries(spans, 0, cg.size());
				
				if (fixedEntries.cardinality() < cg.size()) {
					Formatter failure = new Formatter();
					
					getLog().warn(inText.toString() + " failed");
					
					final int CONTEXT = 4;
					
					int firstSet = 0;
					int firstClear;
					
					while ((firstClear = fixedEntries.nextClearBit(firstSet)) < cg.size()) {
						int nextClear = firstClear;
						do {
							firstSet = fixedEntries.nextSetBit(nextClear);
							firstSet = firstSet >= 0 ? firstSet : cg.size();
							nextClear = fixedEntries.nextClearBit(firstSet);
						} while (nextClear < cg.size() && nextClear < firstSet + CONTEXT*2 + 1);

						int firstReported = Math.max(0, firstClear - CONTEXT);
						int lastReported = Math.min(cg.size(), firstSet + CONTEXT) - 1;

						int from = 0;
						int to = text.length();
						
						if (spans[firstReported].size() == 1)
							from = spans[firstReported].get(0).from;
						
						if (spans[lastReported].size() == 1)
							to = spans[lastReported].get(0).to;
						
						failure.format("========== [%5d;%5d[ ==========\n", from, to);
						failure.format("%s\n", text.substring(from, to));
						failure.format("===================================\n");
						
						for (int i = firstReported; i <= lastReported; i++) {
							CGEntry entry = cg.get(i);
							if (spans[i].size() == 1) {
								Span matchSpan = spans[i].get(0);
								failure.format("%5d.   %-30s (%s) : \"%s\"\n", i, '"' + entry.getKey() + '"', entry.getValue(), text.substring(matchSpan.from, spans[i].get(0).to));
							} else {
								failure.format("%5d. * %-30s (%s) : #%d - %s\n", i, '"' + entry.getKey() + '"', entry.getValue(), spans[i].size(), spans[i].toString());
							}
						}
					}
					
					document.getFeatures().put("br.eti.rslemos.nlp.corpora.chave.parser.failure", failure.toString());
				}
				
			} catch (Exception e) {
				getLog().warn(inText.toString() + " exception: " + e.getMessage());

				StringWriter trace = new StringWriter();
				e.printStackTrace(new PrintWriter(trace));
				document.getFeatures().put("br.eti.rslemos.nlp.corpora.chave.parser.exception", e.getMessage());
				document.getFeatures().put("br.eti.rslemos.nlp.corpora.chave.parser.exceptionTrace", trace.toString());
			}
			
			FileUtils.fileWrite(outDoc.toString(), document.toXml());
		} catch (Exception e) {
			getLog().error(e);
		}
	}

}
