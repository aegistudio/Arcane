package net.aegistudio.arcane.map;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;

public class JarTraverser implements Traverser {
	@Override
	public void mapClass(URL searchUrl, ClassMapper mapper) throws Exception {
		JarURLConnection connection = (JarURLConnection) searchUrl.openConnection();
		Enumeration<JarEntry> entries = connection.getJarFile().entries();
		while(entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if(entry.getName().startsWith(connection.getEntryName())) 
				mapper.map(connection.getJarFile().getInputStream(entry));
		}
	}
}