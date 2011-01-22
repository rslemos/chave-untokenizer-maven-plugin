import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.DocumentContent;
import gate.DocumentFormat;
import gate.FeatureMap;
import gate.Gate;
import gate.GateConstants;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.corpora.SgmlDocumentFormat;
import gate.creole.ResourceInstantiationException;
import gate.util.DocumentFormatException;
import gate.util.InvalidOffsetException;

public class TestGate {

	private static DocumentFormat format;

	public static void main(String[] args) throws Exception {
		URL url = TestGate.class.getResource("/pt_BR/CHAVEFolha/1994/01/01/001.sgml");

		ClassLoader ccl = Thread.currentThread().getContextClassLoader();
		ClassLoader gcl = Gate.class.getClassLoader();
		
		//Gate.init();
		Document doc = openDocument(url);
		
		String xml = doc.toXml();
		System.out.println(xml);
	}

	static {
		format = new SgmlDocumentFormat();
		try {
			format.init();
		} catch (ResourceInstantiationException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private static Document openDocument(URL url) throws ResourceInstantiationException, IOException, DocumentFormatException, InvalidOffsetException {
		Document doc = loadFromURL(url);
		
		parseSGML(doc);
		
		FeatureMap features = doc.getFeatures();

		doc.setSourceUrl(url);
		features.put("gate.SourceURL", url.toString());
		
		return doc;
	}

	private static void parseSGML(Document doc) throws DocumentFormatException, InvalidOffsetException {
		format.unpackMarkup(doc);
		
		FeatureMap features = doc.getFeatures();

		AnnotationSet originalMarkup = doc.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
		
		DocumentContent content = doc.getContent();
		HashSet<String> types = new HashSet<String>();
		types.add("DOCNO");
		types.add("DOCID");
		types.add("DATE");
		
		AnnotationSet set;
		while (!(set = originalMarkup.get(types)).isEmpty()) {
			Annotation annotation = set.iterator().next();
			String type = annotation.getType();
			Long start = annotation.getStartNode().getOffset();
			Long end = annotation.getEndNode().getOffset();
			
			String text = content.getContent(start, end).toString();
			
			features.put(type, text);
			doc.edit(start, end, null);
		}
		
		Annotation docAnnotation = originalMarkup.get("DOC").iterator().next();
		Annotation textAnnotation = originalMarkup.get("TEXT").iterator().next();
		
		doc.edit(textAnnotation.getEndNode().getOffset(), docAnnotation.getEndNode().getOffset(), null);
		doc.edit(docAnnotation.getStartNode().getOffset(), textAnnotation.getStartNode().getOffset(), null);
	}

	private static Document loadFromURL(URL url) throws ResourceInstantiationException, IOException {
		Document doc = new DocumentImpl();
		doc.init();
		
		DocumentContent content = new DocumentContentImpl(url, "UTF-8", null, null);
		doc.setContent(content);
		
		return doc;
	}

}
