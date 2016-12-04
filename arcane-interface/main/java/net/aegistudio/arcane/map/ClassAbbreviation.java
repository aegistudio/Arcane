package net.aegistudio.arcane.map;

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
	
	public ClassAbbreviation() {
		// Just register some api common classes.
		// Easier usage for minor plugins.
		try {
			register("connector.deft", net.aegistudio.arcane.stub.DeftConnector.class);
			register("connector.lazy", net.aegistudio.arcane.stub.LazyConnector.class);
			
			register("stub.deft", net.aegistudio.arcane.stub.DeftConnector.class);
			register("stub.lazy", net.aegistudio.arcane.stub.LazyConnector.class);
		}
		catch(ClassNotFoundException e) {
			throw new AssertionError(
					"Acane interface classes should be in the same artifact!");
		}
	}
	
	public Class<?> lookForClass(String name) throws ClassNotFoundException {
		Class<?> clazz = abbrClassTable.get(name);
		return (clazz == null)? Class.forName(name) : clazz;
	}
	
	public String lookForName(Class<?> clazz) {
		String className = classAbbrTable.get(clazz);
		return (className == null)? clazz.getName() : className;
	}
	
	public void register(String abbreviation, Class<?> clazz) throws ClassNotFoundException {
		if(abbrClassTable.containsKey(abbreviation)) return;
		abbrClassTable.put(abbreviation, clazz);
		classAbbrTable.put(clazz, abbreviation);
	}
}