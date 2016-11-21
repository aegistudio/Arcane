package net.aegistudio.arcane.seal;

import org.bukkit.entity.Entity;
import net.aegistudio.arcane.config.ConfigurationSection;

public class PitchOrientFactory extends WrappedPainterFactory {
	protected double initPhase = 0;
	public static final String INIT_PHASE = "initPhase";
	
	public void load(ConfigurationSection config) throws Exception {
		super.load(config);
		if(config.contains(INIT_PHASE)) 
			initPhase = config.getDouble(INIT_PHASE);
	}
	
	public void save(ConfigurationSection config) throws Exception {
		super.save(config);
		config.set(INIT_PHASE, initPhase);
	}
	
	@Override
	public Painter newPainter(Entity entity) {
		return new PitchOrientPainter(this.wrapped.newPainter(entity), entity, initPhase);
	}
}
