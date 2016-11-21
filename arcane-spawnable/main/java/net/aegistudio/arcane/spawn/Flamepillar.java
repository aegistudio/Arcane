package net.aegistudio.arcane.spawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;

import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.CompatibleSound;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.VolatileObject;

public class Flamepillar extends VolatileObject implements Spawnable {
	public static final double PILLAR_FACTOR = 0.12;
	@SuppressWarnings("deprecation")
	@Override
	public void spawn(Context context, Location location, String[] arguments) {
		location.getWorld().playSound(location, CompatibleSound.FIZZ.get(context.getServer()), 1.0f, 1.0f);
		FallingBlock previous = null;
		for(int i = 0; i < 3; i ++) {
			FallingBlock block = location.getWorld()
					.spawnFallingBlock(location, Material.FIRE, (byte) 0);
			//block.setFireTicks(100);
			//block.setVelocity(new Vector(0, i * PILLAR_FACTOR, 0));
			if(previous != null) previous.setPassenger(block);
			previous = block;
		}
	}
}
