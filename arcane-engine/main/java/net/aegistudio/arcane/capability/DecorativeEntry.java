package net.aegistudio.arcane.capability;

import net.aegistudio.arcane.config.ConfigurationSection;

public interface DecorativeEntry {
	public void loadSingleEffect(String key, ConfigurationSection context) throws Exception;
	
	public void saveSingleEffect(String key, ConfigurationSection context) throws Exception;
	
	public Object getValue(String effectName);
	
	public void setValue(String effectName, Object value);
}
