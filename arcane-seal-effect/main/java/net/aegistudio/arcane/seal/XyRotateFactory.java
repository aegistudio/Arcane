package net.aegistudio.arcane.seal;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.ConfigurationSection;

public class XyRotateFactory extends WrappedPainterFactory {
	private double rotationSpeed = 0.10;
	public static final String ROTATION_SPEED = "rotationSpeed";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		super.load(config);
		this.rotationSpeed = config.getDouble(ROTATION_SPEED);
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		super.save(config);
		config.set(ROTATION_SPEED, rotationSpeed);
	}

	@Override
	public Painter newPainter(Entity entity) {
		double cos = Math.cos(Math.PI * rotationSpeed);
		double sin = Math.sin(Math.PI * rotationSpeed);
		double[][] rotation = new double[][] {
				{+cos, -sin, 0, 0},
				{+sin, +cos, 0, 0},
				{   0,    0, 1, 0},
				{   0,    0, 0, 1},
		};
		return new TransformPainter(this.wrapped.newPainter(entity), rotation) {
			@Override
			public void end(Context context) {
				this.multTrans();
				next.end(context);
			}
		};
	}
}
