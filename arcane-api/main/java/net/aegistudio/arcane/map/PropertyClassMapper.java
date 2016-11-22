package net.aegistudio.arcane.map;

import java.util.Properties;
import java.io.InputStream;
import java.util.Map.Entry;

public class PropertyClassMapper implements ClassMapper {
	private final ClassLoader classloader;
	private final ClassAbbreviation abbreviation;
	
	public PropertyClassMapper(ClassLoader loader, ClassAbbreviation abbreviation) {
		this.abbreviation = abbreviation;
		this.classloader = loader;
	}
	
	public void map(InputStream inputStream) throws Exception {
		Properties properties = new Properties();
		properties.load(inputStream);
		
		for(Entry<Object, Object> mapEntry: properties.entrySet()) {
			String mapEntryKey = mapEntry.getKey().toString();
			Class<?> mapEntryValue = classloader.loadClass(mapEntry.getValue().toString());
			abbreviation.register(mapEntryKey, mapEntryValue);
		}
	}
}
