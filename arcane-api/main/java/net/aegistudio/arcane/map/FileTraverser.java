package net.aegistudio.arcane.map;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

public class FileTraverser implements Traverser {
	@Override
	public void mapClass(URL searchUrl, ClassMapper mapper) throws Exception {
		File root = new File(searchUrl.toURI());
		for(File file : root.listFiles())
			mapper.map(new FileInputStream(file));
	}
}
