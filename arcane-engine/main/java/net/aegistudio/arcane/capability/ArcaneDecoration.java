package net.aegistudio.arcane.capability;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;

import net.aegistudio.arcane.ArcaneEffect;
import net.aegistudio.arcane.ArcaneEngine;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Module;
import net.aegistudio.arcane.config.ConfigurationSection;

public class ArcaneDecoration {
	protected final Map<String, DecorationEntry> decorations = new TreeMap<>();
	protected class DecorationEntry {
		final String name;
		final Supplier<? extends Module> supplier;
		final Map<String, Module> moduleMap = new TreeMap<>();
		final Function<String, ConfigurationSection> backup;
		
		public DecorationEntry(String name, Supplier<? extends Module> supplier, 
				Function<String, ConfigurationSection> backup) {
			
			this.name = name;
			this.supplier = supplier;
			this.backup = backup;
		}
		
		public void loadSingleEffect(String key, ArcaneEffect value) {
			Module newModule = supplier.get();
			ConfigurationSection context = getContextSection(value);
			
			try {
				if(context.contains(name)) 
					newModule.load(context.getConfigurationSection(name));
				else if(backup != null) {
					ConfigurationSection backupSection = backup.apply(key);
					if(backupSection != null) newModule.load(backupSection);
				}
				
				moduleMap.put(key, newModule);
				
				// Fire effect saving.
				saveSingleEffect(key, value);
				engine.saveEffect(key, value);
			}
			catch(Exception e) {
				engine.getLogger().log(Level.SEVERE, 
						"Error while loading decoration " + name + " for effect " + key, e);
			}
		}
		
		public void saveSingleEffect(String key, ArcaneEffect value) {
			ConfigurationSection context = getContextSection(value);
			
			Module decoration = moduleMap.get(key);
			if(decoration != null) try {
				if(!context.contains(name)) 
					context.createSection(name);
				decoration.save(context.getConfigurationSection(name));
			}
			catch(Exception e) {
				engine.getLogger().log(Level.SEVERE, 
						"Error while saving decoration " + name + " for effect " + key, e);
			}
		}
		
		public Module getModule(String effectName) {
			return moduleMap.get(effectName);
		}
	}
	
	protected final Context context;
	protected final ArcaneEngine engine;
	protected final String contextName;
	public ArcaneDecoration(ArcaneEngine engine, Context context) {
		this.context = context;
		this.engine = engine;
		this.contextName = getContextName();
	}
	
	protected String getContextName() {
		return context.getPlugin().getName();
	}
	
	private ConfigurationSection getContextSection(ArcaneEffect effect) {
		ConfigurationSection rootSection = engine.getConfig(effect);
		
		ConfigurationSection section;
		if(!rootSection.contains(contextName)) 
			section = rootSection.createSection(contextName);
		else section = rootSection.getConfigurationSection(contextName);
		
		return section;
	}
	
	private void loadSection(String name, DecorationEntry entry) {
		engine.allEffects((key, value) -> entry.loadSingleEffect(key, value));
	}
	
	public void register(String name, Supplier<? extends Module> supplier, 
			Function<String, ConfigurationSection> backup) {
		
		if(!decorations.containsKey(name)) {
			DecorationEntry entry = new DecorationEntry(name, supplier, backup);
			
			decorations.put(name, entry);
			loadSection(name, entry);
		}
		else throw new IllegalStateException(
				"Decoration " + name + " already registered!");
	}
	
	public void accept(String key, ArcaneEffect value) {
		decorations.forEach((name, entry) -> entry.loadSingleEffect(key, value));
	}
	
	public void save(String effectName, ArcaneEffect effect) {
		decorations.forEach((decorationName, decorationEntry) -> {
				decorationEntry.saveSingleEffect(effectName, effect);
		});
	}
	
	public Module getModule(String decoration, String effect) {
		DecorationEntry entry = decorations.get(decoration);
		if(entry == null) return null;
		return entry.getModule(effect);
	}
}