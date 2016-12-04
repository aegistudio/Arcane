package net.aegistudio.arcane;

import org.bukkit.entity.Entity;

public interface BuffManager extends Module {
	public void buff(Context context, Entity entity, Buff buff, long duration, String[] parameters);
	
	public void unbuff(Context context, Entity entity, Buff buff);
}