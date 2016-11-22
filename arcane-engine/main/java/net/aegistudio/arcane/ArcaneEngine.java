package net.aegistudio.arcane;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import net.aegistudio.arcane.capable.Capability;

public class ArcaneEngine implements Engine {
	public Map<String, Effect> allEffects = new TreeMap<>();
	
	@Override
	public Iterable<String> all() {
		return (Iterable<String>) allEffects.keySet();
	}

	@Override
	public Context connect(Server arg0, Plugin arg1) {
		return null;
	}

	@Override
	public void execute(Context context, Entity target, String name, String[] arguments) {
		allEffects.get(name).execute(context, target, arguments);
	}

	@Override
	public <T extends Capability> T capable(Class<T> arg0) {
		return null;
	}
}
