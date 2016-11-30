package net.aegistudio.arcane;

import net.aegistudio.arcane.capable.Capability;

public interface EngineExtension extends Capability {
	public void accept(String name, ArcaneEffect effect);
	
	public void save(String name, ArcaneEffect effect);
}
