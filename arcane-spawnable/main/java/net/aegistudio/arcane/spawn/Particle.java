package net.aegistudio.arcane.spawn;

import java.lang.reflect.Method;

import org.bukkit.Location;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.config.Configurable;
import net.aegistudio.arcane.config.ConfigurableObject;
import net.aegistudio.arcane.config.ConfigurationSection;

public class Particle extends ConfigurableObject implements Spawnable {
	public @Configurable(value = Configurable.Type.CONSTANT, name = "particleType") String effect;
	public @Configurable(value = Configurable.Type.CONSTANT, name = "particleCount") int count; 
	
	public Object particle;
	public Method method;
	
	public Particle() {}
	
	public Particle(String effect) {
		this.effect = effect;
		this.count = 0;
	}
	
	public void load(ConfigurationSection config) throws Exception {
		super.load(config);
		Class<?> particleClass = Class.forName("org.bukkit.Particle");
		this.particle = particleClass.getMethod("valueOf", String.class).invoke(null, effect);
		method = Class.forName("org.bukkit.World").getMethod(effect, particleClass, 
				double.class, double.class, double.class, int.class);
	}
	
	public void save(ConfigurationSection config) throws Exception {
		super.save(config);
	}
	
	@Override
	public void spawn(Context arg0, Location arg1, String[] arg2) {
		try {
			if(method == null) return;
			if(particle == null) return;
			method.invoke(arg1.getWorld(), 
					arg1.getX(), arg1.getY(), arg1.getZ(), count);
		}
		catch(Exception e) {
			
		}
	}
}