package net.aegistudio.arcane.buff;

import java.util.Map.Entry;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.aegistudio.arcane.Buff;
import net.aegistudio.arcane.Context;

public class ScoreboardBuffRecord extends BuffRecord {
	private final Context context;
	private final Scoreboard scoreboard;
	private final Objective objective;
	private final long division;
	public ScoreboardBuffRecord(Context context, Scoreboard scoreboard, String scoreboardTitle, long division) {
		this.context = context;
		this.scoreboard = scoreboard;
		this.objective = this.scoreboard
				.registerNewObjective(scoreboardTitle, Criterias.DEATHS);
		this.objective.setDisplayName(scoreboardTitle);
		this.division = division;
	}
	
	public void show(Player player) {
		if(this.hasBuff()) {
			player.setScoreboard(scoreboard);
			this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			for(Entry<Buff, Long> buffEntry : super.buffRecord.entrySet()) {
				objective.getScore(buffEntry.getKey().name())
					.setScore((int) (buffEntry.getValue() / division));
			}
		}
		else if(player.getScoreboard() == this.scoreboard) 
			this.scoreboard.clearSlot(DisplaySlot.SIDEBAR);
	}
	
	public void tick(Entity entity) {
		for(Buff buff : super.buffRecord.keySet().toArray(new Buff[0])) {
			super.increment(buff, (int) -division);
			if(!super.hasBuff(buff)){
				buff.remove(context, entity);
				this.scoreboard.resetScores(buff.name());
			}
		}
	}
}
