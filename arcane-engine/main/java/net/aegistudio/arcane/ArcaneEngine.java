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
import net.aegistudio.arcane.capable.Decorative;
import net.aegistudio.arcane.capable.Descriptive;
import net.aegistudio.arcane.config.ConfigurationSection;

public class ArcaneEngine implements Engine, Descriptive {
	
	public Map<String, ArcaneEffect> allEffects = new TreeMap<>();
	
	private final Logger logger;
	private final ConfigurationSection root;
	private final ArcanePluginContext context;
	public ArcaneEngine(Logger logger, ConfigurationSection root, ArcanePluginContext context) {
		this.root = root;
		this.logger = logger;
		this.context = context;
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
		for(Entry<String, ArcaneEffect> effect : allEffects.entrySet()) try {
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
		return context.inherit(server, plugin);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Capability> T capable(Class<T> arg0) {
		// T == Descriptive
		if(arg0 == Descriptive.class) return (T) this;
		// T == Decorative
		if(arg0 == Decorative.class) return null;
		return null;
	}

	@Override
	public String describe(Context arg0, String arg1, String[] arg2) {
		ArcaneEffect effect = allEffects.get(arg1);
		if(effect == null) return null;
		return effect.get(root).getString("description");
	}
}
