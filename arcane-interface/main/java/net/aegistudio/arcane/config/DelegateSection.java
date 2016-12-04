package net.aegistudio.arcane.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DelegateSection implements org.bukkit.configuration.ConfigurationSection {
	public ConfigurationSection section;
	public DelegateSection(ConfigurationSection section) {
		this.section = section;
	}
	
	@Override
	public void addDefault(String arg0, Object arg1) {
		section.addDefault(arg0, arg1);
	}

	@Override
	public boolean contains(String arg0) {
		return section.contains(arg0);
	}

	@Override
	public ConfigurationSection createSection(String arg0, Map<?, ?> arg1) {
		return section.createSection(arg0, arg1);
	}

	@Override
	public Object get(String arg0) {
		return section.get(arg0);
	}

	@Override
	public Object get(String arg0, Object arg1) {
		return section.get(arg0, arg1);
	}

	@Override
	public boolean getBoolean(String arg0) {
		return section.getBoolean(arg0);
	}

	@Override
	public boolean getBoolean(String arg0, boolean arg1) {
		return section.getBoolean(arg0, arg1);
	}

	@Override
	public List<Boolean> getBooleanList(String arg0) {
		return section.getBooleanList(arg0);
	}

	@Override
	public List<Byte> getByteList(String arg0) {
		return section.getByteList(arg0);
	}

	@Override
	public List<Character> getCharacterList(String arg0) {
		return section.getCharacterList(arg0);
	}

	@Override
	public Color getColor(String arg0) {
		return section.getColor(arg0);
	}

	@Override
	public Color getColor(String arg0, Color arg1) {
		return section.getColor(arg0, arg1);
	}

	@Override
	public String getCurrentPath() {
		return section.getCurrentPath();
	}

	@Override
	public ConfigurationSection getDefaultSection() {
		return section.getDefaultSection();
	}

	@Override
	public double getDouble(String arg0) {
		return section.getDouble(arg0);
	}

	@Override
	public double getDouble(String arg0, double arg1) {
		return section.getDouble(arg0);
	}

	@Override
	public List<Double> getDoubleList(String arg0) {
		return section.getDoubleList(arg0);
	}

	@Override
	public List<Float> getFloatList(String arg0) {
		return section.getFloatList(arg0);
	}

	@Override
	public int getInt(String arg0) {
		return section.getInt(arg0);
	}

	@Override
	public int getInt(String arg0, int arg1) {
		return section.getInt(arg0, arg1);
	}

	@Override
	public List<Integer> getIntegerList(String arg0) {
		return section.getIntegerList(arg0);
	}

	@Override
	public ItemStack getItemStack(String arg0) {
		return section.getItemStack(arg0);
	}

	@Override
	public ItemStack getItemStack(String arg0, ItemStack arg1) {
		return section.getItemStack(arg0, arg1);
	}

	@Override
	public Set<String> getKeys(boolean arg0) {
		return section.getKeys(arg0);
	}

	@Override
	public List<?> getList(String arg0) {
		return section.getList(arg0);
	}

	@Override
	public List<?> getList(String arg0, List<?> arg1) {
		return section.getList(arg0, arg1);
	}

	@Override
	public long getLong(String arg0) {
		return section.getLong(arg0);
	}

	@Override
	public long getLong(String arg0, long arg1) {
		return section.getLong(arg0, arg1);
	}

	@Override
	public List<Long> getLongList(String arg0) {
		return section.getLongList(arg0);
	}

	@Override
	public List<Map<?, ?>> getMapList(String arg0) {
		return section.getMapList(arg0);
	}

	@Override
	public String getName() {
		return section.getName();
	}

	@Override
	public OfflinePlayer getOfflinePlayer(String arg0) {
		return section.getOfflinePlayer(arg0);
	}

	@Override
	public OfflinePlayer getOfflinePlayer(String arg0, OfflinePlayer arg1) {
		return section.getOfflinePlayer(arg0, arg1);
	}

	@Override
	public ConfigurationSection getParent() {
		return section.getParent();
	}

	@Override
	public Configuration getRoot() {
		return section.getRoot();
	}

	@Override
	public List<Short> getShortList(String arg0) {
		return section.getShortList(arg0);
	}

	@Override
	public String getString(String arg0) {
		return section.getString(arg0);
	}

	@Override
	public String getString(String arg0, String arg1) {
		return section.getString(arg0, arg1);
	}

	@Override
	public List<String> getStringList(String arg0) {
		return section.getStringList(arg0);
	}

	@Override
	public Map<String, Object> getValues(boolean arg0) {
		return section.getValues(arg0);
	}

	@Override
	public Vector getVector(String arg0) {
		return section.getVector(arg0);
	}

	@Override
	public Vector getVector(String arg0, Vector arg1) {
		return section.getVector(arg0, arg1);
	}

	@Override
	public boolean isBoolean(String arg0) {
		return section.isBoolean(arg0);
	}

	@Override
	public boolean isColor(String arg0) {
		return section.isColor(arg0);
	}

	@Override
	public boolean isConfigurationSection(String arg0) {
		return section.isConfigurationSection(arg0);
	}

	@Override
	public boolean isDouble(String arg0) {
		return section.isDouble(arg0);
	}

	@Override
	public boolean isInt(String arg0) {
		return section.isInt(arg0);
	}

	@Override
	public boolean isItemStack(String arg0) {
		return section.isItemStack(arg0);
	}

	@Override
	public boolean isList(String arg0) {
		return section.isList(arg0);
	}

	@Override
	public boolean isLong(String arg0) {
		return section.isLong(arg0);
	}

	@Override
	public boolean isOfflinePlayer(String arg0) {
		return section.isOfflinePlayer(arg0);
	}

	@Override
	public boolean isSet(String arg0) {
		return section.isSet(arg0);
	}

	@Override
	public boolean isString(String arg0) {
		return section.isString(arg0);
	}

	@Override
	public boolean isVector(String arg0) {
		return section.isVector(arg0);
	}

	@Override
	public void set(String arg0, Object arg1) {
		section.set(arg0, arg1);
	}

	@Override
	public ConfigurationSection createSection(String arg0) {
		return section.createSection(arg0);
	}

	@Override
	public ConfigurationSection getConfigurationSection(String arg0) {
		return section.getConfigurationSection(arg0);
	}
}
