package net.aegistudio.arcane.capable;

import java.util.function.Supplier;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Module;

/**
 * Allowing storing plugin bound information
 * along with the side of effect configuration.
 * 
 * @author aegistudio
 */

public interface Decorative extends Capability {
	public <T extends Module> void register(Context context, String identifier, Supplier<T> factory);
	
	public <T extends Module> T get(Context context, String identifier, String effect, Class<T> type);
}
