package net.aegistudio.arcane.config;

import net.aegistudio.arcane.Module;

public class ConfigurableObject implements Module {

	@Override
	public void load(ConfigurationSection config) throws Exception {
		config.loadConfigurable(config);
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		config.saveConfigurable(config);
	}
}
