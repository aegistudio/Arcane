package net.aegistudio.arcane.spawn;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.ConfigurationSection;

public class Entity implements Spawnable {
	public org.bukkit.entity.EntityType type;
	public static final String ENTITY_TYPE = "entityType";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		if(config.contains(ENTITY_TYPE))
			type = EntityType.valueOf(config.getString(ENTITY_TYPE));
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		config.set(ENTITY_TYPE, type.name());
	}

	@Override
	public void spawn(Context context, Location location, String[] arguments) {
		try {
			org.bukkit.entity.Entity entity = location.getWorld().spawnEntity(location, type);
			Method getHandle = entity.getClass().getDeclaredMethod("getHandle");
			getHandle.setAccessible(true);
			Object target = getHandle.invoke(entity);
			for(Field field : target.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				System.out.println(field.getType() + " " + target.toString() + "." + field.getName() + " = " + field.get(target));
				field.setAccessible(false);
			}
			System.out.println();
			getHandle.setAccessible(false);
		}
		catch(Exception e) {
			
		}
	}
}
