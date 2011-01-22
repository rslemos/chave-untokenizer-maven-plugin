import java.util.Collections;

import gate.AnnotationSet;
import gate.Document;
import gate.DocumentContent;
import gate.FeatureMap;
import gate.Gate;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.util.SimpleFeatureMapImpl;

public class TestGate {

	public static void main(String[] args) throws Exception {
		ClassLoader ccl = Thread.currentThread().getContextClassLoader();
		ClassLoader gcl = Gate.class.getClassLoader();
		//Gate.init();
		Document doc = new DocumentImpl();
		doc.init();
		DocumentContent content = new DocumentContentImpl("This is the content of my document");
		doc.setContent(content);
		{
			FeatureMap features = doc.getFeatures();
			features.put("myMap", Collections.singletonMap("key", "value"));
			features.put("myInt", 10);
			features.put("myLong[]", new long[] { 10, 20, 30, 40 } );
		}
		
		{
			AnnotationSet annotationSet = doc.getAnnotations("Original annotations");
			FeatureMap features = new SimpleFeatureMapImpl();
			annotationSet.add(5L, 10L, "type-1", features);
			annotationSet.add(2L, 15L, "type-2", features);
			annotationSet.add(1L, 7L, "type-3", features);
			annotationSet.add(4L, 6L, "type-4", features);
		}
		String xml = doc.toXml();
		System.out.println(xml);
	}

}
