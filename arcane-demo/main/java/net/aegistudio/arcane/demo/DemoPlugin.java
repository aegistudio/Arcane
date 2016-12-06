package net.aegistudio.arcane.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.EngineModule;
import net.aegistudio.arcane.capable.Decorative;
import net.aegistudio.arcane.capable.Descriptive;
import net.aegistudio.arcane.config.Configurable;
import net.aegistudio.arcane.config.ConfigurationSection;
import net.aegistudio.arcane.map.ClassAbbreviation;

public class DemoPlugin extends JavaPlugin {
	EngineModule engine;
	Context connection;
	
	Decorative decorative;
	
	@Override
	public void onEnable() {
		try {
			// Initialize configuration.
			saveDefaultConfig();
			reloadConfig();
			ConfigurationSection rootConfig = new ConfigurationSection(getDataFolder(), 
					new ClassAbbreviation(), getConfig());
		
			// Load your engine module (stub) by configuration specified.
			// The two nulls are default classes if entry absence. Seems we don't require it here.
			engine = rootConfig.loadInstance(EngineModule.class, "engineClass", null, "engineConfig", null);
			
			// Connect to arcane engine service.
			connection = engine.connect(getServer(), this);
			
			// Register an effect filter flag.
			decorative = engine.capable(Decorative.class);
			if(decorative != null) 
				decorative.register(connection, "enabled", Configurable.Type.CONSTANT, (effect) -> true);
		}
		catch(Exception e) {
			setEnabled(false);
			getLogger().log(Level.SEVERE, "Error loading demo plugin.", e);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
		switch(command.getName()) {
			case "demoexec":
				// I tend to add a command to execute effects from the arcane engine.
				// It was just like what the api consumer will be responded while calling execute method.
				
				if(!(sender instanceof Entity)) return false;
				if(arguments.length == 0) return false;
				if(!checkEnabled(toEffectName(arguments))) return false;
				
				engine.execute(connection, (Entity)sender, 
						toEffectName(arguments), toEffectArgs(arguments));
				return true;
			case "demodescribe":
				// I tend to add a command to describe effects from the arcane engine.
				// If the engine do not support descriptive function, I will tell you that is not supported.
				// If the effect is missing or has no description, you will get 'No description.
				// Or you will get the very description from the engine.
				
				if(arguments.length == 0) return false;
				if(!checkEnabled(toEffectName(arguments))) return false;
				
				Descriptive descriptive = engine.capable(Descriptive.class);
				if(descriptive == null) sender.sendMessage(
						ChatColor.RED + "The engine does not support descriptive.");
				else {
					String description = descriptive.describe(connection, 
						toEffectName(arguments), toEffectArgs(arguments));
					
					if(description == null) sender.sendMessage(
						ChatColor.RED + "No description.");
					else sender.sendMessage(description);
				}
				
				return true;
			default:
				return false;
		}
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] arguments) {
		// I tend to provide a tab-complete for effects available.
		// First I get possible effects by retrieving effects from the arcane engine.
		// Then I filter them down and finally result in effect prefixed with name provided by user.
		
		if(!Arrays.asList("demoexec", "demodescribe").contains(command.getName())) return null;
		if(arguments.length > 1) return null;
		String name = toEffectName(arguments);
		ArrayList<String> result = new ArrayList<>();
		engine.all().forEach(effect -> { 
			if(!effect.startsWith(name)) return;
			if(!checkEnabled(effect)) return;
			
			result.add(effect);
		});
		return result;
	}
	
	private boolean checkEnabled(String effect) {
		if(decorative == null) return true;
		Boolean enabled = 
			decorative.get(connection, "enabled", effect, Boolean.class);
		return enabled != null? enabled : true;
	}
	
	private String toEffectName(String[] arguments) {
		return arguments.length == 1? arguments[0] : "";
	}
	
	private String[] toEffectArgs(String[] arguments) {
		String[] effectArguments = new String[arguments.length - 1];
		System.arraycopy(arguments, 1, effectArguments, 0, effectArguments.length);
		return effectArguments;
	}
}
