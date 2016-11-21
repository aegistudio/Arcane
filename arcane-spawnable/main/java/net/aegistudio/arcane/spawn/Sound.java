package net.aegistudio.arcane.spawn;

import org.bukkit.Location;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Spawnable;
import net.aegistudio.arcane.config.Configurable;
import net.aegistudio.arcane.config.ConfigurableObject;

public class Sound extends ConfigurableObject implements Spawnable {

	public @Configurable(Configurable.Type.ENUM) org.bukkit.Sound sound;
	public @Configurable(Configurable.Type.CONSTANT) int tier = 1;
	public @Configurable(Configurable.Type.CONSTANT) int max = 1;
	
	public Sound() {}
	public Sound(org.bukkit.Sound sound) {
		this.sound = sound;
	}
	
	public float volume() {
		return 0.4f + 0.6f * tier / max;
	}
	
	public static final float[] TONAL_PITCH_LOOKUP = new float[] {1.f, 9.0f/8, 5.0f/4, 4.0f/3, 3.0f/2, 5.0f/3, 90f/48, 2.f};
	public float pitch() {
		return TONAL_PITCH_LOOKUP[Math.min(Math.round(8.0f * tier / max), 7)];
	}

	@Override
	public void spawn(Context arg0, Location location, String[] arg2) {
		location.getWorld().playSound(location, sound, volume(), pitch());
	}
}
