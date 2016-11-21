package net.aegistudio.arcane.spawn;

import org.bukkit.Location;

import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.VolatileObject;

public class Lightning extends VolatileObject implements Spawnable {
	@Override
	public void spawn(Context context, Location location, String[] arguments) {
		location.getWorld().strikeLightning(location);
	}
}
