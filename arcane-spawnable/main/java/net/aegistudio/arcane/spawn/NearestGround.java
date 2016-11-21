package net.aegistudio.arcane.spawn;

import org.bukkit.Location;
import org.bukkit.Material;

import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.ConfigurationSection;

public class NearestGround implements Spawnable {
	public NearestGround() {}
	
	public NearestGround(Spawnable wrapped) {
		this.wrapped = wrapped;
	}
	
	public Spawnable wrapped;
	public static final String WRAPPED_CLASS = "wrappedClass";
	public static final String WRAPPED_CONFIG = "wrappedConfig";
	
	private int allowedRange = 15;
	public static final String ALLOWED_RANGE = "allowedRange";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		if(config.contains(ALLOWED_RANGE)) allowedRange = config.getInt(ALLOWED_RANGE);
		wrapped = config.loadInstance(Spawnable.class, WRAPPED_CLASS, null, WRAPPED_CONFIG, null);
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		config.set(ALLOWED_RANGE, allowedRange);
		config.saveInstance(wrapped, WRAPPED_CLASS, WRAPPED_CONFIG);
	}
	
	@Override
	public void spawn(Context context, Location location, String[] arguments) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		if(location.getWorld().getBlockAt(x, y, z).getType() == Material.AIR) {
			// Search down.
			for(int i = 1; i <= allowedRange; i ++) 
				if(location.getWorld().getBlockAt(x, y - i, z).getType() != Material.AIR) {
					this.wrapped.spawn(context, location.clone().add(0, -i + 1, 0), arguments);
					break;
				}
		}
		else {
			// Search up.
			for(int i = 1; i <= allowedRange; i ++) 
				if(location.getWorld().getBlockAt(x, y + i, z).getType() == Material.AIR) {
					this.wrapped.spawn(context, location.clone().add(0, i, 0), arguments);
					break;
				}
		}
	}
}
