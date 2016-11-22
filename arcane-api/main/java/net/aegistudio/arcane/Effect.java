package net.aegistudio.arcane;

import org.bukkit.entity.Entity;

public interface Effect extends Module {
	public void execute(Context element, Entity sender, String[] params);
}
