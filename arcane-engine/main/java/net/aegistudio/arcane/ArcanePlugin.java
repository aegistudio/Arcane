package net.aegistudio.arcane;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import net.aegistudio.arcane.config.ConfigurationSection;
import net.aegistudio.arcane.map.ClassAbbreviation;
import net.aegistudio.arcane.map.PropertyClassMapper;
import net.aegistudio.arcane.map.Traverser;
import net.aegistudio.arcane.map.TraverserComposite;

/**
 * The plugin class for arcane project. Serving
 * as context provider, engine provider, etc.
 * 
 * @author aegistudio
 */

public class ArcanePlugin extends JavaPlugin {
	public ClassAbbreviation abbreviation = new ClassAbbreviation();
	public Traverser traverser = new TraverserComposite();

	public void mapClass(ClassAbbreviation abbreviation, ClassLoader classloader) throws Exception {
		PropertyClassMapper mapper = new PropertyClassMapper(classloader, abbreviation);
		URL enumerate = classloader.getResource("map");
		if(enumerate == null) return;
		traverser.mapClass(enumerate, mapper);
	}
	
	File effectPath, resourcePath, extensionPath, templatePath;
	String fileExtension;
	
	BuffManager buffManager;
	ArcaneEngine engine;
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		try {
			// Pre-setup
			this.saveDefaultConfig();
			this.reloadConfig();
			
			// Load my class map.			
			mapClass(abbreviation, getClassLoader());
			
			// Load default path configuration.
			Configuration rootConfig = this.getConfig();
			org.bukkit.configuration.ConfigurationSection effectEngineConfig = 
					rootConfig.getConfigurationSection("effectEngineConfig");
			effectPath = new File(getDataFolder(), effectEngineConfig.getString("effectPath"));
			fileExtension = effectEngineConfig.getString("fileExtension");
			resourcePath = new File(getDataFolder(), effectEngineConfig.getString("resourcePath"));
			extensionPath = new File(getDataFolder(), effectEngineConfig.getString("extensionPath"));
			templatePath = new File(getDataFolder(), effectEngineConfig.getString("templatePath"));
			
			// Printing arcane debugging info.
			getServer().getLogger().info("Using following configuration in Arcane Engine: ");
			getServer().getLogger().info("+ Effects(*." + fileExtension + "): " + effectPath.getAbsolutePath());
			getServer().getLogger().info("+ Extensions: " + extensionPath.getAbsolutePath());
			getServer().getLogger().info("+ Resources: " + resourcePath.getAbsolutePath());
			getServer().getLogger().info("+ Templates: " + templatePath.getAbsolutePath());
			
			// Load more from extension path.
			if(extensionPath.exists() && extensionPath.isDirectory()) 
				for(File file : extensionPath.listFiles()) 
					mapClass(abbreviation, new URLClassLoader(new URL[]{file.toURL()}));
			
			// Load buff manager configuration.
			ConfigurationSection buffConfig = new ConfigurationSection(resourcePath, abbreviation, rootConfig);
			buffManager = buffConfig.loadInstance(BuffManager.class, "buffEngineClass", 
					(Class<BuffManager>)null, "buffEngineConfig", null);
			
			// Create arcane engine
			engine = new ArcaneEngine(getLogger(), new ConfigurationSection(effectPath, abbreviation, null), 
					new ArcanePluginContext(getServer(), this, buffManager));
			
			// Load effect configurations.
			File[] effectFiles = effectPath.listFiles(name -> name.getName().endsWith("." + fileExtension));
			for(File effectFile : effectFiles) {
				String effectName = effectFile.getName();
				effectName = effectName.substring(0, effectName.length() - fileExtension.length() - 1);
				engine.accept(effectName, effectFile);
			}
			
			// Register arcane engine service.
			getServer().getServicesManager().register(Engine.class, this.engine, 
					this, ServicePriority.High);
		}
		catch(Exception e) {
			getServer().getLogger().log(Level.SEVERE, 
					"Error loading arcane engine.", e);
			setEnabled(false);
		}
	}
	
	public void onDisable() {
		engine.save();
	}
}
