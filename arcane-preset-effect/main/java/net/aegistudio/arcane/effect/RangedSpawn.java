package net.aegistudio.arcane.effect;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.*;
import net.aegistudio.arcane.config.*;

public class RangedSpawn implements SpellEffect {
	public @Configurable(Configurable.Type.CONSTANT) int tier = 1;
	public @Configurable(Configurable.Type.CONSTANT) int cluster = 1;
	public @Configurable(value = Configurable.Type.CONSTANT, name = "maxRange") double range = 5.0;
	public @Configurable(value = Configurable.Type.CONSTANT, name = "minRange") double minRange = 1.0;
	public @Configurable(Configurable.Type.CONSTANT) long lag = 0;
	public @Configurable(Configurable.Type.CONSTANT) long delay = 40;
	
	public static final String ENTITY_CLASS = "entityClass"; 
	public static final String ENTITY_CONFIG = "entityConfig";
	public Spawnable entity;
	
	@Override
	public void spell(Context context, Entity sender, String[] params) {
		new RangedSpawnRunnable(context, this, sender.getLocation(), params).start();
	}
	
	@Override
	public void load(ConfigurationSection spellConfig) throws Exception {
		spellConfig.loadConfigurable(this);
		this.entity = spellConfig.loadInstance(Spawnable.class, 
				ENTITY_CLASS, null, ENTITY_CONFIG, null);
	}

	@Override
	public void save(ConfigurationSection spellConfig) throws Exception {
		spellConfig.saveConfigurable(this);
		spellConfig.saveInstance(entity, ENTITY_CLASS, ENTITY_CONFIG);
	}
}
