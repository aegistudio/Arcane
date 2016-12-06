package net.aegistudio.arcane.buff;

import java.util.TreeMap;

import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Buff;
import net.aegistudio.arcane.BuffManager;
import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.VolatileObject;

public class MutedBuffManager extends VolatileObject implements BuffManager {
	private final TreeMap<Integer, BuffRecord> entityBuffs = new TreeMap<Integer, BuffRecord>();
	
	class BuffRemoval implements Runnable {
		private final Context context;
		private final BuffRecord record;
		private final Buff buff;
		private final Entity entity;
		public BuffRemoval(Context context, Entity entity, BuffRecord record, Buff buff) {
			this.context = context;
			this.record = record;
			this.buff = buff;
			this.entity = entity;
		}
		@Override
		public void run() {
			if(this.record.hasBuff(buff)) {
				this.record.increment(buff, -1);
				if(!this.record.hasBuff(buff)) 
					buff.remove(context, entity);
			}
			
			if(!this.record.hasBuff())
				entityBuffs.remove(entity.getEntityId());
		}
	}
	
	@Override
	public void buff(Context context, Entity entity, Buff buff, long duration, String[] parameters) {
		BuffRecord buffRecord = entityBuffs.get(entity.getEntityId());
		if(buffRecord == null) entityBuffs.put(entity.getEntityId(), 
				buffRecord = new BuffRecord());
		
		buffRecord.increment(buff, 1);
		buff.buff(context, entity, parameters);
		context.getServer().getScheduler().runTaskLater(context.getPlugin(), 
				new BuffRemoval(context, entity, buffRecord, buff), duration);
	}
	
	@Override
	public void unbuff(Context context, Entity entity, Buff buff) {
		BuffRecord buffRecord = entityBuffs.get(entity.getEntityId());
		if(buffRecord == null) return;
		if(buffRecord.hasBuff(buff)) {
			buffRecord.remove(buff);
			buff.remove(context, entity);
		}
	}
}
