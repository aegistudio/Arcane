package net.aegistudio.arcane;

import org.bukkit.Location;

public interface Spawnable extends Module {
	public void spawn(Context context, Location location, String[] arguments);
}
