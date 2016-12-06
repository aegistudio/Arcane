package net.aegistudio.arcane.select;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.ConfigurationSection;

public class NearbyPlayerFilter implements EntityFilter {
	public static final String RADIUM = "radium"; public float radium = 3.0f;
	public static final String SELF_INCLUDED = "selfIncluded"; public boolean selfIncluded = true;
	
	@Override
	public List<Entity> filter(Context context, Entity source) {
		Location location = source.getLocation();
		ArrayList<Entity> entity = new ArrayList<Entity>();
		for(Player player : context.getServer().getOnlinePlayers()) 
			if(player.getWorld().equals(location.getWorld())) 
				if(player.equals(source)) {
					if(selfIncluded) entity.add(player);
				}
				else if(player.getLocation().distance(location) <= radium) 
					entity.add(player);
		
		return entity;
	}

	@Override
	public void load(ConfigurationSection config) throws Exception {
		
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		
	}
}
