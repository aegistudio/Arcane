package net.aegistudio.arcane.config;

import java.io.File;
import java.lang.reflect.Field;
import java.util.function.Consumer;

import net.aegistudio.arcane.Module;
import net.aegistudio.arcane.expr.AlgebraExpression;
import net.aegistudio.arcane.map.ClassAbbreviation;

public class ConfigurationSection extends DelegateSection {
	protected final ClassAbbreviation abbreviation;
	protected final File rootFolder;
	
	public ConfigurationSection(File rootFolder, ClassAbbreviation abbreviation, 
			org.bukkit.configuration.ConfigurationSection section) {
		super(section);
		this.abbreviation = abbreviation;
		this.rootFolder = rootFolder;
	}
	
	protected ConfigurationSection inherit(org.bukkit.configuration.ConfigurationSection section) {
		return new ConfigurationSection(rootFolder, abbreviation, section);
	}
	
	public ConfigurationSection createSection(String configEntry) {
		return inherit(section.createSection(configEntry));
	}
	
	public ConfigurationSection getConfigurationSection(String configEntry) {
		return inherit(section.getConfigurationSection(configEntry));
	}

	public File getDataFolder() {
		return this.rootFolder;
	}
	
	@SuppressWarnings("unchecked")	
	public <T extends Module> T loadInstance(Class<T> target, String classEntry, 
			Class<? extends T> defaultClazz, String configEntry, Consumer<T> abscence) throws Exception {
		
		String classValue = section.getString(classEntry);
		if(classValue == null) section.set(classEntry, classValue = 
				abbreviation.lookForName(defaultClazz));
		
		Class<T> moduleClazz = (Class<T>) 
				abbreviation.lookForClass(classValue);
		T instance = moduleClazz.newInstance();
		
		this.loadConfig(instance, configEntry, abscence);
		return instance;
	}
	
	public <T extends Module> void saveConfig(T instance, String configEntry) throws Exception {
		if(!section.contains(configEntry)) section.createSection(configEntry);
		instance.save(getConfigurationSection(configEntry));
	}
	
	public <T extends Module> void saveInstance(T instance, String classEntry, String configEntry) throws Exception {
		section.set(classEntry, abbreviation.lookForName(instance.getClass()));
		this.saveConfig(instance, configEntry);
	}
	
	public <T extends Module> T loadConfig(T instance, String configEntry, Consumer<T> abscence) throws Exception {
		
		ConfigurationSection config;
		if(section.contains(configEntry) && section.isConfigurationSection(configEntry)) {
			config = getConfigurationSection(configEntry);
			instance.load(config);
		}
		else {
			config = createSection(configEntry);
			if(abscence != null)
				abscence.accept(instance);
			instance.save(config);
		}
		return instance;
	}
	
	private void configurable(Object instance, boolean load) throws Exception {
		Field[] fields = instance.getClass().getFields();
		for(Field field : fields) {
			Configurable config = field.getAnnotation(Configurable.class);
			if(config != null) {
				String fieldName = config.name().length() > 0? config.name() : field.getName();
				if(load) config.value().load(fieldName, field, instance, section);
				else config.value().save(fieldName, field, instance, section);
			}
		}
	}
	
	public void loadConfigurable(Object instance) throws Exception {
		configurable(instance, true);
	}
	
	public void saveConfigurable(Object instance) throws Exception {
		configurable(instance, false);
	}
	
	public AlgebraExpression getExpression(String name) {
		return new AlgebraExpression(section.getString(name));
	}
}
