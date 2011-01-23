package br.eti.rslemos.nlp.corpora.chave.parser;

import gate.Document;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.FileUtils;

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
    private boolean overwrite;
    
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
				List fileNames = FileUtils.getFileNames(new File(resource.getDirectory()), "**/*.sgml", "", false);
				getLog().info("Sorting");
				Collections.sort(fileNames);
				getLog().info("Untokenizing");
				for (Iterator iterator = fileNames.iterator(); iterator.hasNext();) {
					String fileName = (String) iterator.next();
					
					File in = new File(resource.getDirectory(), fileName);
					File out = new File(outputDirectory, FileUtils.removeExtension(fileName) + ".xml");

					getLog().info(out.toString());
					out.getParentFile().mkdirs();
					untokenize(in, out);
				}
			} catch (IOException e) {
				throw new MojoExecutionException("Iterating throw input directories", e);
			}
		}
	}

	private void process(File base, File file) {
		File in = file;
		if (in.isDirectory()) {
			File[] files = in.listFiles();
			for (File e : files) {
				process(base, e);
			}
		} else {
			if (!in.toString().endsWith(".sgml"))
				return;

			String suffix = FileUtils.basename(getSuffix(base, in), ".sgml") + ".xml";
			File out = new File(outputDirectory, suffix);
			out.getParentFile().mkdirs();
			getLog().info(out.toString());
			untokenize(in, out);
		}
	}

	private void untokenize(File in, File out) {
		try {
			Document document = GateLoader.load(in.toURI().toURL(), encoding);
			FileUtils.fileWrite(out.toString(), document.toXml());
		} catch (Exception e) {
			getLog().error(e);
		}
	}

	private static String getSuffix(File base, File file) {
		if (base.equals(file))
			return ".";
		else 
			return getSuffix(base, file.getParentFile()) + File.separator + file.getName(); 
	}
}
