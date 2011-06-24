package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.Document;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.Collections;
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

	private void untokenize(File inText, File inCG, File out) {
		try {
			Document document = GateLoader.load(inText.toURI().toURL(), encoding);
			List<CGEntry> cg = CGEntry.loadFromReader(new FileReader(inCG));

			try {
				String text = document.getContent().toString();
				
				Untokenizer untokenizer = new Untokenizer();
				untokenizer.untokenize(document, cg);
				
				List<Span>[] spans = untokenizer.getProcessingResults();
				BitSet fixedEntries = Untokenizer.getFixedEntries(spans, 0, cg.size());
				if (fixedEntries.cardinality() < cg.size()) {
					System.out.printf("%s failed\n", inText.toString());
					
					int firstSet = 0;
					int firstClear;
					
					while ((firstClear = fixedEntries.nextClearBit(firstSet)) < cg.size()) {
						firstSet = fixedEntries.nextSetBit(firstClear);

						int from = firstClear > 0 ? spans[firstClear - 1].get(0).from : 0;
						int to = firstSet >= 0 ? spans[firstSet].get(0).to : text.length();

						System.out.println("========== cut here ==========");
						System.out.println(text.substring(from, to));
						System.out.println("\n========== cut here ==========");
						
						if (firstClear > 0) {
							CGEntry entry = cg.get(firstClear - 1);
							System.out.printf("%4d. %s (%s)\n", firstClear - 1, entry.getKey(), entry.getValue());
						}
						
						for (int i = firstClear; i < firstSet; i++) {
							CGEntry entry = cg.get(i);
							System.out.printf("%4d. %s (%s) : %s\n", i, entry.getKey(), entry.getValue(), spans[i].toString());
						}
						
						if (firstSet >= 0) {
							CGEntry entry = cg.get(firstSet);
							System.out.printf("%4d. %s (%s)\n", firstSet, entry.getKey(), entry.getValue());
						}
					}
				}
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
			FileUtils.fileWrite(out.toString(), document.toXml());
		} catch (Exception e) {
			getLog().error(e);
		}
	}

}
