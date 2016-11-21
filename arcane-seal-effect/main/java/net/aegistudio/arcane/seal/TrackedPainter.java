package net.aegistudio.arcane.seal;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.Context;

public class TrackedPainter implements Painter {
	private final Entity target;
	private final Spawnable effect;
	private final double scale;
	
	public TrackedPainter(Entity target, Spawnable effect, double scale) {
		this.target = target;
		this.effect = effect;
		this.scale = scale;
	}
	
	@Override
	public void paint(Context context, double x, double z, double y, String[] arguments) {
		this.effect.spawn(context, this.target.getLocation()
				.add(x * scale, y * scale, z * scale), arguments);
	}

	@Override
	public void end(Context context) {	}
}
