package net.aegistudio.arcane;

import net.aegistudio.arcane.config.ConfigurationSection;

public interface Module {
	public void load(ConfigurationSection config) throws Exception;
	
	public void save(ConfigurationSection config) throws Exception;
}
