package net.aegistudio.arcane.capability;

import java.util.function.Function;
import java.util.function.Supplier;

import net.aegistudio.arcane.ArcaneEffect;
import net.aegistudio.arcane.ArcaneEngine;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.ContextualMap;
import net.aegistudio.arcane.EngineExtension;
import net.aegistudio.arcane.Module;
import net.aegistudio.arcane.capable.Decorative;
import net.aegistudio.arcane.config.Configurable.Type;
import net.aegistudio.arcane.config.ConfigurationSection;

public class ArcaneDecorative implements EngineExtension, Decorative {
	protected final ArcaneEngine engine;
	protected final ContextualMap<ArcaneDecoration> decorativeMap;
	public ArcaneDecorative(ArcaneEngine engine) {
		this.engine = engine;
		decorativeMap = new ContextualMap<>(context -> new ArcaneDecoration(engine, context));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Context context, String decoration, String effect, Class<T> arg3) {
		engine.validate(context);
		return (T) decorativeMap.get(context).get(decoration, effect);
	}

	@Override
	public <T extends Module> void register(Context context, String decoration, 
			Supplier<T> factory, Function<String, ConfigurationSection> backup) {		
		engine.validate(context);
		
		DecorativeSectionEntry entry = new DecorativeSectionEntry(decoration, factory, backup);
		decorativeMap.get(context).register(decoration, entry);
	}

	@Override
	public <T> void register(Context context, String decoration, 
			Type valueType, Function<String, T> backup) {
		engine.validate(context);
		
		SingleValueEntry<T> entry = new SingleValueEntry<T>(decoration, valueType, backup);
		decorativeMap.get(context).register(decoration, entry);
	}
	
	@Override
	public void accept(String name, ArcaneEffect effect) {
		decorativeMap.entrySet().forEach(entry -> entry.getValue().accept(name, effect));
	}

	@Override
	public void save(String name, ArcaneEffect effect) {
		decorativeMap.entrySet().forEach(entry -> entry.getValue().save(name, effect));
	}
}
