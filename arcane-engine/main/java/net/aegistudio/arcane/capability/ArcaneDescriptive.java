package net.aegistudio.arcane.capability;

import java.util.Map;
import java.util.TreeMap;

import net.aegistudio.arcane.ArcaneEffect;
import net.aegistudio.arcane.ArcaneEngine;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.EngineExtension;
import net.aegistudio.arcane.capable.Descriptive;

public class ArcaneDescriptive implements EngineExtension, Descriptive {
	private final Map<String, String> descriptions = new TreeMap<>();
	private final ArcaneEngine engine;
	
	public ArcaneDescriptive(ArcaneEngine engine) {
		this.engine = engine;
	}
	
	@Override
	public String describe(Context arg0, String arg1, String[] arg2) {
		engine.validate(arg0);
		return descriptions.get(arg1);
	}

	@Override
	public void accept(String name, ArcaneEffect effect) {
		String description = engine.getConfig(effect).getString("description");
		descriptions.put(name, description);
	}

	@Override
	public void save(String name, ArcaneEffect effect) {	}
}
