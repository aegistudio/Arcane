package net.aegistudio.arcane.seal;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Effect;
import net.aegistudio.arcane.config.ConfigurationSection;

public class SealEffect implements Effect {
	Generator generator;
	private static final String GENERATOR_CLAZZ = "generatorClass";
	private static final String GENERATOR_CONFIG = "generatorConfig";
	
	PainterFactory painterFactory;
	private static final String PAINTER_CLAZZ = "painterClass";
	private static final String PAINTER_CONFIG = "painterConfig";
	
	int totalPaintTick = 20;
	private static final String TOTAL_PAINT_TICK = "totalPaintTick";
	
	long repaintTick = 3;
	private static final String REPAINT_TICK = "repaintTick";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		if(config.contains(TOTAL_PAINT_TICK)) totalPaintTick = config.getInt(TOTAL_PAINT_TICK);
		if(config.contains(REPAINT_TICK)) repaintTick = config.getInt(REPAINT_TICK);
		
		painterFactory = config.loadInstance(PainterFactory.class, PAINTER_CLAZZ, 
				TrackedFactory.class, PAINTER_CONFIG, null);
		generator = config.loadInstance(Generator.class, GENERATOR_CLAZZ, 
				SparseGenerator.class, GENERATOR_CONFIG, null);
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		config.set(TOTAL_PAINT_TICK, totalPaintTick);
		config.set(REPAINT_TICK, repaintTick);
		
		config.saveInstance(painterFactory, PAINTER_CLAZZ, PAINTER_CONFIG);
		config.saveInstance(generator, GENERATOR_CLAZZ, GENERATOR_CONFIG);
	}
	
	@Override
	public void execute(Context context, Entity sender, String[] params) {
		new SealPaintTask(context, this, painterFactory.newPainter(sender), params).start();
	}
}
