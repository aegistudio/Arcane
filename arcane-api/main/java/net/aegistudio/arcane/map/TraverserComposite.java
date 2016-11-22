package net.aegistudio.arcane.map;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

public class TraverserComposite implements Traverser {
	public final Map<String, Traverser> dispatcher = new TreeMap<>(); {
		dispatcher.put("jar", new JarTraverser());
		dispatcher.put("file", new FileTraverser());
	}
	
	@Override
	public void mapClass(URL searchUrl, ClassMapper mapper) throws Exception {
		dispatcher.get(searchUrl.getProtocol()).mapClass(searchUrl, mapper);
	}
}