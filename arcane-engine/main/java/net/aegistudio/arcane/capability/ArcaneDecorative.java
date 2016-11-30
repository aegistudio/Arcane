package net.aegistudio.arcane.capability;

import java.util.function.Supplier;

import net.aegistudio.arcane.ArcaneEffect;
import net.aegistudio.arcane.ArcaneEngine;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.ContextualMap;
import net.aegistudio.arcane.EngineExtension;
import net.aegistudio.arcane.Module;
import net.aegistudio.arcane.capable.Decorative;

public class ArcaneDecorative implements EngineExtension, Decorative {
	protected final ArcaneEngine engine;
	protected final ContextualMap<ArcaneDecoration> decorativeMap;
	public ArcaneDecorative(ArcaneEngine engine) {
		this.engine = engine;
		decorativeMap = new ContextualMap<>(context -> new ArcaneDecoration(engine, context));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Module> T get(Context context, String decoration, String effect, Class<T> arg3) {
		engine.validate(context);
		return (T) decorativeMap.get(context).getModule(decoration, effect);
	}

	@Override
	public <T extends Module> void register(Context context, String decoration, Supplier<T> arg2) {
		engine.validate(context);
		decorativeMap.get(context).register(decoration, arg2);
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
