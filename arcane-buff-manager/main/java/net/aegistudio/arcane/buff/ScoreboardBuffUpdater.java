package net.aegistudio.arcane.buff;

import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import net.aegistudio.arcane.Context;

public class ScoreboardBuffUpdater implements Runnable {
	private final Context context;
	private final ScoreboardBuffManager manager;
	public ScoreboardBuffUpdater(ScoreboardBuffManager manager, Context context) {
		this.manager = manager;
		this.context = context;
	}
	
	private BukkitTask task;
	
	@Override
	public void run() {
		this.tick();
		this.show();
		this.remove();
		if(manager.entityBuffs.isEmpty()) {
			task.cancel();
			task = null;
		}
	}

	public void start() {
		if(task != null) return;
		task = context.getServer().getScheduler()
			.runTaskTimer(context.getPlugin(), this, 
					manager.updateTick, manager.updateTick);
		this.show();
	}
	
	protected void show() {
		for(Entry<Entity, ScoreboardBuffRecord> buffs 
				: manager.entityBuffs.entrySet()) 
			if(buffs.getKey() instanceof Player) {
				Player player = (Player) buffs.getKey();
				buffs.getValue().show(player);
			}
	}
	
	protected void tick() {
		for(Entry<Entity, ScoreboardBuffRecord> buffs 
				: manager.entityBuffs.entrySet()) 
				buffs.getValue().tick(buffs.getKey());
	}
	
	protected void remove() {
		Iterator<Entry<Entity, ScoreboardBuffRecord>> iterator = 
				manager.entityBuffs.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Entity, ScoreboardBuffRecord> next = iterator.next();
			if(!next.getValue().hasBuff()) 
				iterator.remove();
		}
	}
}
