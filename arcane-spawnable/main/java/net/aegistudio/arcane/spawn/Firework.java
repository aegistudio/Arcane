package net.aegistudio.arcane.spawn;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.inventory.meta.FireworkMeta;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.config.VolatileObject;

public class Firework extends VolatileObject implements Spawnable {
	@Override
	public void spawn(Context contet, Location location, String[] arguments) {
		org.bukkit.entity.Firework firework = location.getWorld().spawn(location, org.bukkit.entity.Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.addEffect(FireworkEffect.builder().withColor(Color.RED)
				.with(FireworkEffect.Type.BURST).build());
		meta.setPower(0);
		firework.setFireworkMeta(meta);
		firework.detonate();
	}
}
