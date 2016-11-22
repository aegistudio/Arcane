package net.aegistudio.arcane.effect;

import java.util.TreeMap;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.aegistudio.arcane.*;
import net.aegistudio.arcane.config.*;
import net.aegistudio.arcane.expr.*;

public class FeatherFall extends ConfigurableObject implements net.aegistudio.arcane.Effect {
	class ContextualObject extends VolatileObject implements Listener, Buff {
		private final TreeMap<Integer, Double> protecting = new TreeMap<Integer, Double>();
		private Sound feather;
		
		public ContextualObject(Context context) {
			context.getServer().getPluginManager().registerEvents(this, context.getPlugin());
			feather = CompatibleSound.ENDERDRAGON_WING.get(context.getServer());
		}

		@Override
		public String name() {
			return buffName;
		}

		@Override
		public void buff(Context element, Entity entity, String[] parameters) {
			this.protecting.put(entity.getEntityId(), velocity.getDouble(new Parameter(parameters)));
			if(effectBegin != null) entity.sendMessage(effectBegin);
		}

		@Override
		public void remove(Context element, Entity entity) {
			this.protecting.remove(entity.getEntityId());
			if(effectEnd != null) entity.sendMessage(effectEnd);
		}
		
		@EventHandler
		public void handleFallSpeed(PlayerMoveEvent event) {
			if(!protecting.containsKey(event.getPlayer().getEntityId())) return;
			double velocity = protecting.get(event.getPlayer().getEntityId());
			Location location = event.getPlayer().getLocation();
			
			if(event.getPlayer().getVelocity().getY() < -velocity) {
				event.getPlayer().setVelocity(event.getPlayer().getVelocity().setY(-velocity));
				location.getWorld().playSound(location, feather, 1.0f, 1.0f);
				location.getWorld().playEffect(location, Effect.valueOf("FLYING_GLYPH"), null);
				event.getPlayer().setFallDistance(0);
			}
		}
	}
	
	private final ContextualMap<ContextualObject> map = new ContextualMap<>(context -> new ContextualObject(context));
	
	@Override
	public void execute(Context context, Entity sender, String[] params) {
		context.getBuffManager().buff(context, sender, map.get(context), duration.getInt(new Parameter(params)), params);
	}

	public @Configurable(Configurable.Type.LOCALE) String effectBegin = "You feel you were as light as a swallow...";
	public @Configurable(Configurable.Type.LOCALE) String effectEnd = "You could feel the pull of gravity again...";
	
	public @Configurable(Configurable.Type.ALGEBRA) AlgebraExpression duration = new AlgebraExpression("200");
	public @Configurable(Configurable.Type.ALGEBRA) AlgebraExpression velocity = new AlgebraExpression("0.2");
	public @Configurable(Configurable.Type.STRING) String buffName = "Feather Fall";
}
