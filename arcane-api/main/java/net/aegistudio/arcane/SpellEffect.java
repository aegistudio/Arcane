package net.aegistudio.arcane;

import org.bukkit.entity.Entity;

public interface SpellEffect extends Module {
	public void spell(Context element, Entity sender, String[] params);
}
