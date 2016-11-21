package net.aegistudio.arcane.spawn;

import org.bukkit.Location;

import net.aegistudio.arcane.config.Configurable;
import net.aegistudio.arcane.config.ConfigurableObject;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Spawnable;

public class Effect extends ConfigurableObject implements Spawnable {
	public @Configurable(value = Configurable.Type.ENUM, name = "effectType") org.bukkit.Effect effect;
	public @Configurable(value = Configurable.Type.CONSTANT, name = "effectData") int data; 
	
	public Effect() {}
	
	public Effect(org.bukkit.Effect effect) {
		this.effect = effect;
		this.data = 0;
	}

	@Override
	public void spawn(Context context, Location location, String[] arguments) {
		location.getWorld().playEffect(location, effect, data);
	}
}

