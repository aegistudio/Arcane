package net.aegistudio.arcane;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import net.aegistudio.arcane.capability.ArcaneDecorative;
import net.aegistudio.arcane.capability.ArcaneDescriptive;
import net.aegistudio.arcane.capable.Capability;
import net.aegistudio.arcane.capable.Decorative;
import net.aegistudio.arcane.capable.Descriptive;
import net.aegistudio.arcane.config.ConfigurationSection;

public class ArcaneEngine implements Engine {
	private final Map<String, ArcaneEffect> allEffects = Collections.synchronizedMap(new TreeMap<>());
	public void allEffects(BiConsumer<String, ArcaneEffect> consumer) { allEffects.forEach(consumer); }
	
	private final Logger logger;
	public Logger getLogger() { return logger; }

	private final ConfigurationSection root;
	private final ArcanePluginContext context;
	
	private final Map<Class<? extends Capability>, EngineExtension> extensions = new HashMap<>();
	
	public ArcaneEngine(Logger logger, ConfigurationSection root, ArcanePluginContext context) {
		this.root = root;
		this.logger = logger;
		this.context = context;
		
		extensions.put(Descriptive.class, new ArcaneDescriptive(this));
		extensions.put(Decorative.class, new ArcaneDecorative(this));
	}
	
	public ConfigurationSection getConfig(ArcaneEffect effect) {
		return effect.get(root);
	}
	
	public ArcanePluginContext validate(Context context) {
		if(!(context instanceof ArcanePluginContext)) 
			throw new IllegalArgumentException("Invalid context!");	
		return (ArcanePluginContext) context;
	}
	
	public void accept(String name, File effectFile) {
		try {
			ArcaneEffect effect = new ArcaneEffect(effectFile);
			effect.load(root);
			extensions.forEach((capability, extension) 
					-> extension.accept(name, effect));
			allEffects.put(name, effect);
			effect.save(root);
		}
		catch(Exception e) {
			logger.log(Level.SEVERE, "Error while loading effect " + name, e);
		}
	}
	
	public void save() {
		for(Entry<String, ArcaneEffect> effect : allEffects.entrySet()) try {
			extensions.forEach((capability, extension) 
					-> extension.save(effect.getKey(), effect.getValue()));
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
		validate(context);
		allEffects.get(name).execute(context, target, arguments);
	}
	
	@Override
	public Context connect(Server server, Plugin plugin) {
		return context.inherit(server, plugin);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Capability> T capable(Class<T> arg0) {
		return (T)extensions.get(arg0);
	}
}
