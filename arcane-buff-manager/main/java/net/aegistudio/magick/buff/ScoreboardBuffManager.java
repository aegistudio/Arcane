package net.aegistudio.magick.buff;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Buff;
import net.aegistudio.arcane.BuffManager;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.Configurable;
import net.aegistudio.arcane.config.ConfigurableObject;

public class ScoreboardBuffManager extends ConfigurableObject implements BuffManager {
	final HashMap<Entity, ScoreboardBuffRecord> entityBuffs = new HashMap<>();
	
	@Configurable(value = Configurable.Type.CONSTANT) long updateTick = 2;
	@Configurable(value = Configurable.Type.STRING) String scoreboardTitle = ChatColor.YELLOW + "Buff";
	
	private HashMap<Context, ScoreboardBuffUpdater> updaterMap = new HashMap<>();
	
	@Override
	public void buff(Context context, Entity entity, Buff buff, long duration, String[] parameters) {
		ScoreboardBuffUpdater updater = updaterMap.get(context);
		if(updater == null) updaterMap.put(context, 
				updater = new ScoreboardBuffUpdater(this, context));
		
		ScoreboardBuffRecord record = entityBuffs.get(entity);
		if(record == null) entityBuffs.put(entity, 
				record = new ScoreboardBuffRecord(context, context.getServer()
						.getScoreboardManager().getNewScoreboard(), scoreboardTitle, updateTick));
		
		record.set(buff, duration);
		buff.buff(context, entity, parameters);
		updater.start();
	}

	@Override
	public void unbuff(Context context, Entity entity, Buff buff) {
		BuffRecord buffRecord = entityBuffs.get(entity);
		if(buffRecord == null) return;
		if(buffRecord.hasBuff(buff)) {
			buffRecord.remove(buff);
			buff.remove(context, entity);
		}
	}
}
