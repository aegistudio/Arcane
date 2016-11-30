package net.aegistudio.arcane.stub;

import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Engine;
import net.aegistudio.arcane.EngineModule;
import net.aegistudio.arcane.capable.Capability;

public abstract class Connector implements EngineModule {

	@Override
	public Context connect(Server server, Plugin plugin) {
		subconnect(server, plugin);
		return connectEngine().connect(server, plugin);
	}

	@Override
	public void execute(Context context, Entity target, String effect, String[] parameters) {
		connectEngine().execute(context, target, effect, parameters);
	}

	@Override
	public <T extends Capability> T capable(Class<T> capability) {
		return connectEngine().capable(capability);
	}

	@Override
	public Iterable<String> all() {
		return connectEngine().all();
	}

	protected abstract Engine connectEngine();
	
	protected abstract void subconnect(Server server, Plugin plugin);
}