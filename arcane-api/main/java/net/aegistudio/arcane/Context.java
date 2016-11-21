package net.aegistudio.arcane;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public interface Context {
	public Server getServer();
	
	public Plugin getPlugin();
	
	public BuffManager getBuffManager();
}
