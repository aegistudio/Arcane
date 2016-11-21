package net.aegistudio.magick.effect;

import java.util.Map.Entry;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.expr.Parameter;

public class UnionEffect extends CompositeEffect {
	@Override
	public void spell(Context element, Entity sender, String[] params) {
		Parameter param = new Parameter(params);
		double probability = Math.random();
		for(Entry<String, CompositeEffectEntry> entry : super.subEffects.entrySet()) {
			if(entry.getValue().probability.getDouble(param) >= probability) 
				entry.getValue().effect.spell(element, sender, params);
		}
	}
}
