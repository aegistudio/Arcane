package net.aegistudio.arcane.map;

import java.net.URL;

public interface Traverser {
	public void mapClass(URL searchUrl, ClassMapper mapper) throws Exception;
}
