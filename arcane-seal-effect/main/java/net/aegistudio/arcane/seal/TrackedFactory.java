package net.aegistudio.arcane.seal;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.config.ConfigurationSection;

public class TrackedFactory implements PainterFactory {
	private double scale = 5.0; public static final String SCALE = "scale";
	
	public Spawnable effect;
	public static final String EFFECT_CLASS = "effectClass";
	public static final String EFFECT_CONFIG = "effectConfig";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		if(config.contains(SCALE)) scale = config.getDouble(SCALE);
		this.effect = config.loadInstance(Spawnable.class, EFFECT_CLASS, 
				null, EFFECT_CONFIG, null);
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		config.set(SCALE, scale);
		
		config.saveInstance(this.effect, EFFECT_CLASS, EFFECT_CONFIG);
	}

	@Override
	public Painter newPainter(Entity entity) {
		return new TrackedPainter(entity, effect, scale);
	}
}
