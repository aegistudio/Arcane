package net.aegistudio.arcane.seal;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Module;

public interface PainterFactory extends Module {
	public Painter newPainter(Entity entity);
}
