package net.aegistudio.arcane;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

/**
 * A dedicated context for a plugin via Arcane Engine.
 * 
 * @author aegistudio
 */

public class ArcanePluginContext implements Context {
	protected final Server server;
	protected final Plugin plugin;
	protected final BuffManager buffManager;
	
	public ArcanePluginContext(Server server, Plugin plugin, BuffManager manager) {
		this.server = server;
		this.plugin = plugin;
		this.buffManager = manager;
	}
	
	@Override
	public BuffManager getBuffManager() {
		return buffManager;
	}

	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	@Override
	public Server getServer() {
		return server;
	}
	
	public ArcanePluginContext inherit(Server server, Plugin plugin) {
		return new ArcanePluginContext(server, plugin, buffManager);
	}
	
	public boolean equals(Object another) {
		if(!(another instanceof ArcanePluginContext)) return false;
		ArcanePluginContext anotherContext = (ArcanePluginContext) another;
		if(server != anotherContext.server) return false;
		if(plugin != anotherContext.plugin) return false;
		if(buffManager != anotherContext.buffManager) return false;
		return true;
	}
	
	public int hashCode() {
		return server.hashCode() 
				+ plugin.hashCode() 
				+ buffManager.hashCode();
	}
}
