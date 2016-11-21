package net.aegistudio.arcane.seal;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.ConfigurationSection;

public class ZoomFactory extends WrappedPainterFactory {
	private double init = 0.0;
	public static final String INIT = "init";
	private double zoom = 0.1;
	public static final String ZOOM = "zoom";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		super.load(config);
		this.init = config.getDouble(INIT);
		this.zoom = config.getDouble(ZOOM);
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		super.save(config);
		config.set(INIT, init);
		config.set(ZOOM, zoom);
	}

	@Override
	public Painter newPainter(Entity entity) {
		return new TransformPainter(this.wrapped.newPainter(entity), new double[][] {
			{zoom, 0, 0, 0},
			{0,	zoom, 0, 0},
			{0,	0, zoom, 0},
			{0, 0, 0, 0}
		}, new double[][] {
			{init, 0, 0, 0},
			{0,	init, 0, 0},
			{0,	0, init, 0},
			{0, 0, 0, 1}
		}){
			@Override
			public void end(Context context) {
				this.addTrans();
				this.next.end(context);
			}
		};
	}
}
