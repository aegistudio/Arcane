package net.aegistudio.arcane.config;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Give an abbreviation for a long class name.
 * And look them up in this class.
 * 
 * @author aegistudio
 */

public class ClassAbbreviation {
	protected final TreeMap<String, Class<?>> abbrClassTable = new TreeMap<>();
	protected final HashMap<Class<?>, String> classAbbrTable = new HashMap<>();
	
	public Class<?> lookForClass(String name) throws ClassNotFoundException {
		Class<?> clazz = abbrClassTable.get(name);
		return (clazz == null)? Class.forName(name) : clazz;
	}
	
	public String lookForName(Class<?> clazz) {
		String className = classAbbrTable.get(clazz);
		return (className == null)? clazz.getName() : className;
	}
	
	public void register(String abbreviation, String fullname) throws ClassNotFoundException {
		if(abbrClassTable.containsKey(abbreviation)) return;
		abbrClassTable.put(abbreviation, Class.forName(fullname));
	}
}