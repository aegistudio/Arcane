package net.aegistudio.arcane.seal;

import net.aegistudio.arcane.config.ConfigurationSection;

public abstract class WrappedPainterFactory implements PainterFactory{
	protected PainterFactory wrapped = new TrackedFactory();	
	public static final String WRAPPED_CLASS = "wrappedClass";
	public static final String WRAPPED_CONFIG = "wrappedConfig";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		this.wrapped = config.loadInstance(PainterFactory.class, WRAPPED_CLASS, 
				TrackedFactory.class, WRAPPED_CONFIG, null);
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		config.saveInstance(this.wrapped, WRAPPED_CLASS, WRAPPED_CONFIG);
	}
}
