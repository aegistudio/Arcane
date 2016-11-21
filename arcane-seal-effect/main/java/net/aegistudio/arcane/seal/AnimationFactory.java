package net.aegistudio.arcane.seal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.config.ConfigurationSection;

public class AnimationFactory implements PainterFactory {
	public List<String> order;
	public static final String ORDER = "order";	

	public Map<String, PainterFactory> factory;
	public Map<String, Integer> ticks;
	public static final String CLASS = "Class";
	public static final String CONFIG = "Config";
	public static final String TICKS = "Ticks";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		this.order = config.getStringList(ORDER);
		this.factory = new TreeMap<String, PainterFactory>();
		this.ticks = new TreeMap<String, Integer>();
		
		for(String current : order) {
			factory.put(current, config.loadInstance(PainterFactory.class, current.concat(CLASS), 
					null, current.concat(CONFIG), null));
			ticks.put(current, config.getInt(current.concat(TICKS)));
		}
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		config.set(ORDER, order);
		for(String current : order) {
			PainterFactory pFactory = factory.get(current);
			config.saveInstance(pFactory, current.concat(CLASS), current.concat(CONFIG));
			config.set(current.concat(TICKS), ticks.get(current));
		}
	}

	@Override
	public Painter newPainter(Entity entity) {
		ArrayList<Painter> factory = new ArrayList<Painter>();
		ArrayList<Integer> ticks = new ArrayList<Integer>();
		for(String current : order) {
			factory.add(this.factory.get(current).newPainter(entity));
			ticks.add(this.ticks.get(current));
		}
		return new AnimationPainter(factory.toArray(new Painter[0]), ticks.toArray(new Integer[0]));
	}
}
