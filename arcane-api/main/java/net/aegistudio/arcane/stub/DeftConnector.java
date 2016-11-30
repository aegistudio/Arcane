package net.aegistudio.arcane.stub;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.aegistudio.arcane.Engine;
import net.aegistudio.arcane.config.ConfigurationSection;

public class DeftConnector extends Connector {

	@Override
	public void load(ConfigurationSection config) throws Exception {	}

	@Override
	public void save(ConfigurationSection config) throws Exception {	}

	protected Server server;
	
	@Override
	protected Engine connectEngine() {
		if(server == null) throw new IllegalStateException("You've not been connected to the server.");
		RegisteredServiceProvider<Engine> engine = server.getServicesManager().getRegistration(Engine.class);
		if(engine == null) throw new IllegalStateException("There's no arcane engine installed on this server.");
		return engine.getProvider();
	}

	@Override
	protected void subconnect(Server server, Plugin plugin) {
		this.server = server;
	}
}
