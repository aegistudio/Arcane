package net.aegistudio.arcane.capable;

import java.util.function.Consumer;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Engine;
import net.aegistudio.arcane.config.ConfigurationSection;

/**
 * Allowing storing plugin bound information
 * along with the side of effect configuration.
 * 
 * @author aegistudio
 */

public interface Decorative extends Engine {
	public String decorate(Context context, String identifier, 
			Consumer<ConfigurationSection> decorator);
}
