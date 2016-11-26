package net.aegistudio.arcane;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import net.aegistudio.arcane.capable.Capability;
import net.aegistudio.arcane.config.ConfigurationSection;

public class ArcaneEngine implements Engine {
	
	public Map<String, Effect> allEffects = new TreeMap<>();
	
	private final Logger logger;
	private final ConfigurationSection root;
	public ArcaneEngine(Logger logger, ConfigurationSection root) {
		this.root = root;
		this.logger = logger;
	}
	
	public void accept(String name, File effectFile) {
		try {
			ArcaneEffect effect = new ArcaneEffect(effectFile);
			effect.load(root);
			allEffects.put(name, effect);
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Error while loading effect " + name, e);
		}
	}
	
	public void save() {
		for(Entry<String, Effect> effect : allEffects.entrySet()) try {
			effect.getValue().save(root);
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Error while saving effect " + effect.getKey(), e);
		}
	}
	
	@Override
	public Iterable<String> all() {
		return (Iterable<String>) allEffects.keySet();
	}

	@Override
	public void execute(Context context, Entity target, String name, String[] arguments) {
		allEffects.get(name).execute(context, target, arguments);
	}
	
	@Override
	public Context connect(Server server, Plugin plugin) {
		return null;
	}

	@Override
	public <T extends Capability> T capable(Class<T> arg0) {
		return null;
	}
}
