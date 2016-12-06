package net.aegistudio.arcane.capable;

import java.util.function.Function;
import java.util.function.Supplier;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Module;
import net.aegistudio.arcane.config.ConfigurationSection;

/**
 * Allowing storing plugin bound information
 * along with the side of effect configuration.
 * 
 * @author aegistudio
 */

public interface Decorative extends Capability {
	/**
	 * Register a decorative section on engine database.
	 * 
	 * @param context The plugin context.
	 * @param identifier The decorative identifier inside the plugin.
	 * @param factory The decorative object factory.
	 * @param backup When a preset object absent, provides a default section.
	 */
	public <T extends Module> void register(Context context, String identifier, 
			Supplier<T> factory, Function<String, ConfigurationSection> backup);
	
	public <T extends Module> T get(Context context, String identifier, String effect, Class<T> type);
}
