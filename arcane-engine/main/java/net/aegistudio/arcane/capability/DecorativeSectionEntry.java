package net.aegistudio.arcane.capability;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;

import net.aegistudio.arcane.Module;
import net.aegistudio.arcane.config.ConfigurationSection;

public class DecorativeSectionEntry implements DecorativeEntry {
	final String name;
	final Supplier<? extends Module> supplier;
	final Map<String, Module> moduleMap = new TreeMap<>();
	final Function<String, ConfigurationSection> backup;
	
	public DecorativeSectionEntry(String name, Supplier<? extends Module> supplier, 
			Function<String, ConfigurationSection> backup) {
		
		this.name = name;
		this.supplier = supplier;
		this.backup = backup;
	}
	
	public void loadSingleEffect(String key, ConfigurationSection context) throws Exception {
		Module newModule = supplier.get();
		
		if(context.contains(name)) 
			newModule.load(context.getConfigurationSection(name));
		else if(backup != null) {
			ConfigurationSection backupSection = backup.apply(key);
			if(backupSection != null) newModule.load(backupSection);
		}
		
		moduleMap.put(key, newModule);
	}
	
	public void saveSingleEffect(String key, ConfigurationSection context) throws Exception{
		Module decoration = getValue(key);
		if(decoration != null)  {
			if(!context.contains(name)) 
				context.createSection(name);
			decoration.save(context.getConfigurationSection(name));
		}
	}

	@Override
	public Module getValue(String effectName) {
		return moduleMap.get(effectName);
	}

	@Override
	public void setValue(String effectName, Object value) {
		moduleMap.put(effectName, (Module) value);
	}
}
