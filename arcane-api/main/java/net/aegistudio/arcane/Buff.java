package net.aegistudio.arcane;

import org.bukkit.entity.Entity;

/**
 * A lasting effect applied to entities.
 * 
 * @author aegistudio
 */

public interface Buff extends Module {
	/**
	 * The name of the buff to display.
	 * 
	 * @return if not null, the name. If null, stay anonymous.
	 */
	public String name();
	
	/**
	 * Start applying buff to the entity.
	 * 
	 * @param context the buff context.
	 * @param entity whom to apply buff.
	 * @param parameter the parameter for buff.
	 */
	public void buff(Context context, Entity entity, String[] parameter);
	
	/**
	 * Stop applying buff to the entity.
	 * 
	 * @param context the buff context.
	 * @param entity whom to apply buff.
	 */
	public void remove(Context context, Entity entity);
}
