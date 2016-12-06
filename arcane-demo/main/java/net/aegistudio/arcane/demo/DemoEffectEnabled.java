package net.aegistudio.arcane.demo;

import net.aegistudio.arcane.Module;
import net.aegistudio.arcane.config.Configurable;
import net.aegistudio.arcane.config.ConfigurableObject;

public class DemoEffectEnabled extends ConfigurableObject implements Module {
	public @Configurable(Configurable.Type.CONSTANT) boolean enabled = true;
}
