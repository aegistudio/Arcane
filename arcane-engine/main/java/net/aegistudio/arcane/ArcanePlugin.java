package net.aegistudio.arcane;

import java.net.URL;

import org.bukkit.plugin.java.JavaPlugin;

import net.aegistudio.arcane.map.ClassAbbreviation;
import net.aegistudio.arcane.map.PropertyClassMapper;
import net.aegistudio.arcane.map.Traverser;
import net.aegistudio.arcane.map.TraverserComposite;

/**
 * The plugin class for arcane project. Serving
 * as context provider, engine provider, etc.
 * 
 * @author aegistudio
 */

public class ArcanePlugin extends JavaPlugin {
	public ClassAbbreviation abbreviation = new ClassAbbreviation();
	public Traverser traverser = new TraverserComposite();

	public void mapClass(ClassAbbreviation abbreviation, ClassLoader classloader) throws Exception {
		PropertyClassMapper mapper = new PropertyClassMapper(classloader, abbreviation);
		URL enumerate = classloader.getResource("map");
		if(enumerate == null) return;
		traverser.mapClass(enumerate, mapper);
	}
	
	public void onEnable() {
		this.saveDefaultConfig();
		this.reloadConfig();
		
		try {
			mapClass(abbreviation, getClassLoader());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
