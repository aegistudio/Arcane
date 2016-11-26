package net.aegistudio.arcane;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import net.aegistudio.arcane.config.ConfigurationSection;

public class ArcaneEffect implements Effect {
	private FileConfiguration fileConfig;
	private final File effectFile;
	
	private String before;
	private Effect wrapped;
	
	public ArcaneEffect(File effectFile) {
		this.effectFile = effectFile;
	}
	
	@Override
	public void load(ConfigurationSection arg0) throws Exception {
		fileConfig = YamlConfiguration.loadConfiguration(effectFile);
		before = fileConfig.saveToString();
		ConfigurationSection config = get(arg0);
		wrapped = config.loadInstance(Effect.class, "class", null, "config", null);
	}
	
	@Override
	public void save(ConfigurationSection arg0) throws Exception {	
		ConfigurationSection config = get(arg0);
		config.saveInstance(wrapped, "class", "config");
		
		String after = fileConfig.saveToString();
		if(before.equals(after)) return;
		fileConfig.save(effectFile);
		before = after;
	}

	public ConfigurationSection get(ConfigurationSection arg0) {
		return arg0.inherit(fileConfig);
	}
	
	@Override
	public void execute(Context arg0, Entity arg1, String[] arg2) {
		wrapped.execute(arg0, arg1, arg2);
	}
}