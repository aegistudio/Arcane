package net.aegistudio.arcane.capability;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import net.aegistudio.arcane.config.Configurable;
import net.aegistudio.arcane.config.ConfigurationSection;

public class SingleValueEntry<T> implements DecorativeEntry {
	public static class MarshalledEntry<U> {
		public U value;
	}
	
	final Configurable.Type valueType;
	final String name;
	final Map<String, MarshalledEntry<T>> valueMap = new TreeMap<>();
	final Function<String, T> backup;
	final Field valueField;
	
	public SingleValueEntry(String name, Configurable.Type valueType, Function<String, T> backup) {
		
		this.name = name;
		this.valueType = valueType;
		this.backup = backup;
		try {
			this.valueField = MarshalledEntry.class.getDeclaredField("value");
		}
		catch(Exception e) {
			throw new AssertionError("Malformed class file for SingleValueEntry!");
		}
	}

	@Override
	public void loadSingleEffect(String key, ConfigurationSection context) throws Exception {
		MarshalledEntry<T> entry = new MarshalledEntry<T>();
		if(context.contains(name)) 
			valueType.load(name, valueField, entry, context);
		else if(backup != null) 
			entry.value = backup.apply(key);
		
		valueMap.put(key, entry);
	}

	@Override
	public void saveSingleEffect(String key, ConfigurationSection context) throws Exception {
		MarshalledEntry<T> entry = this.valueMap.get(key);
		if(entry != null) 
			valueType.save(name, valueField, entry, context);
	}

	@Override
	public T getValue(String effectName) {
		return valueMap.get(effectName).value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(String effectName, Object value) {
		valueMap.get(effectName).value = (T) value;
	}
}
