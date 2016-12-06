package net.aegistudio.arcane.capability;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import net.aegistudio.arcane.ArcaneEffect;
import net.aegistudio.arcane.ArcaneEngine;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.ConfigurationSection;

public class ArcaneDecoration {
	protected final Map<String, DecorativeEntry> decorations = new TreeMap<>();
	
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
	
	private void loadSingle(String name, String key, ArcaneEffect value, DecorativeEntry entry) {
		try {
			ConfigurationSection contextSection = getContextSection(value);
			
			entry.loadSingleEffect(key, contextSection);
			
			// Fire effect saving.
			saveSingle(name, key, value, entry);
			engine.saveEffect(key, value);
		}
		catch(Exception e) {
			engine.getLogger().log(Level.SEVERE, 
					"Error while loading decoration " + name + " for effect " + key, e);
		}
	}
	
	private void saveSingle(String name, String key, ArcaneEffect value, DecorativeEntry entry) {
		try {
			ConfigurationSection contextSection = getContextSection(value);
			
			entry.saveSingleEffect(key, contextSection);
		}
		catch(Exception e) {
			engine.getLogger().log(Level.SEVERE, 
					"Error while saving decoration " + name + " for effect " + key, e);
		}
	}
	
	private void loadSection(String name, DecorativeEntry entry) {
		engine.allEffects((key, value) -> loadSingle(name, key, value, entry));
	}
	
	public void register(String name, DecorativeEntry entry) {
		
		if(!decorations.containsKey(name)) {
			decorations.put(name, entry);
			loadSection(name, entry);
		}
		else throw new IllegalStateException(
				"Decoration " + name + " already registered!");
	}
	
	public void accept(String key, ArcaneEffect value) {
		decorations.forEach((name, entry) -> loadSingle(name, key, value, entry));
	}
	
	public void save(String effectName, ArcaneEffect effect) {
		decorations.forEach((decorationName, decorationEntry) -> 
			saveSingle(decorationName, effectName, effect, decorationEntry));
	}
	
	public Object get(String decoration, String effect) {
		DecorativeEntry entry = decorations.get(decoration);
		if(entry == null) return null;
		return entry.getValue(effect);
	}
}