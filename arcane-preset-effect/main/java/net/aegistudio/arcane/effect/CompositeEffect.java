package net.aegistudio.arcane.effect;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Effect;
import net.aegistudio.arcane.config.ConfigurationSection;
import net.aegistudio.arcane.expr.AlgebraExpression;

public abstract class CompositeEffect implements Effect {
	protected Map<String, CompositeEffectEntry> subEffects = new TreeMap<String, CompositeEffectEntry>();
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		for(String entry : config.getKeys(false)) 
			if(entry.endsWith("Class")) {
				String subEffect = entry.substring(0, entry.length() - "Class".length());
				Effect effectInstance = config.loadInstance(Effect.class, 
						entry, null, subEffect.concat("Config"), null);
				CompositeEffectEntry compositeEntry = new CompositeEffectEntry();
				compositeEntry.effect = effectInstance;
				if(config.contains(subEffect.concat("Probability")))
					compositeEntry.probability = new AlgebraExpression(config.getString(subEffect.concat("Probability")));
				
				this.subEffects.put(subEffect, compositeEntry);
			}
			else continue;
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		for(Entry<String, CompositeEffectEntry> subEffect : subEffects.entrySet()) {
			config.saveInstance(subEffect.getValue().effect, 
					subEffect.getKey().concat("Class"), subEffect.getKey().concat("Config"));
			
			if(subEffect.getValue().probability.isConstant()) {
				if(subEffect.getValue().probability.getDouble(null) < 1.0)
					config.set(subEffect.getKey().concat("Probability"), 
							subEffect.getValue().probability.getExpression());
			}
			else config.set(subEffect.getKey().concat("Probability"), 
					subEffect.getValue().probability.getExpression());
		}
	}

	@Override
	public abstract void execute(Context context, Entity sender, String[] params);
}
