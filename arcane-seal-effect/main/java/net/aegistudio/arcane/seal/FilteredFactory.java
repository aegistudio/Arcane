package net.aegistudio.arcane.seal;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.config.ConfigurationSection;

public class FilteredFactory extends WrappedPainterFactory {
	protected int scanInterval = 3;
	public static final String SCAN_INTERVAL = "scanInterval";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		super.load(config);
		if(config.contains(SCAN_INTERVAL)) this.scanInterval = config.getInt(SCAN_INTERVAL);
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		super.save(config);
		config.set(SCAN_INTERVAL, scanInterval);
	}

	@Override
	public Painter newPainter(Entity entity) {
		return new FilteredPainter(this.wrapped.newPainter(entity), scanInterval);
	}
}
