package net.aegistudio.arcane;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

/**
 * Load, hold and save effects.
 * May also support other predefined capabilities, like 
 * describing effects, or hooked into effect loading.
 * 
 * Could be embeded into an arcane api user, or obtained 
 * via service provider of a bukkit server.
 * 
 * @author aegistudio
 */

public interface Engine {
	/**
	 * Request for a connection via the context.
	 * 
	 * @param server the bukkit server.
	 * @param plugin the plugin requesting.
	 * @return the connection with with effect could be performed.
	 */
	public Context connect(Server server, Plugin plugin);
	
	/**
	 * Perform an effect as the request of another plugin.
	 * 
	 * @param context the context provided.
	 * @param effect the effect name. (if not exists, nothing will be performed.)
	 * @param parameters the parameters for effect execution.
	 */
	public void execute(Context context, String effect, String[] parameters);
	
	/**
	 * Request for a capability of the provided engine.
	 * 
	 * @param capability the class of the capability.
	 * @return object if supported, or null if unsupported.
	 */
	public <T extends Engine> T capable(Class<T> capability);
	
	/**
	 * @return the name of effects available in the engine.
	 */
	public Iterable<String> all();
}