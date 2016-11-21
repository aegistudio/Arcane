package net.aegistudio.arcane.effect;

import java.util.TreeMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.aegistudio.arcane.*;
import net.aegistudio.arcane.config.*;
import net.aegistudio.arcane.expr.*;

public class VampireHand extends ConfigurableObject implements SpellEffect {
	class ContextualObject extends VolatileObject implements Buff, Listener {
		private TreeMap<Integer, Double> vampire = new TreeMap<Integer, Double>();
		
		public ContextualObject(Context context) {
			context.getServer().getPluginManager()
				.registerEvents(this, context.getPlugin());
		}
		
		@Override
		public void buff(Context element, Entity entity, String[] params) {
			vampire.put(entity.getEntityId(), adsorption.getDouble(new Parameter(params)));
			if(beginVampire != null) entity.sendMessage(beginVampire);
		}
		
		@Override
		public String name() {
			return buffName;
		}
		
		@EventHandler
		public void onEntityDamage(EntityDamageByEntityEvent event) {
			if(this.vampire.containsKey(event.getDamager().getEntityId())) {
				Entity damager = event.getDamager();
				if(!(damager instanceof LivingEntity)) return; 
				LivingEntity vampire = (LivingEntity) damager;
				double healCount = event.getDamage() * this.vampire.get(event.getDamager().getEntityId());
				vampire.setHealth(Math.min(vampire.getHealth() + healCount, vampire.getMaxHealth()));
			}
		}
		
		@Override
		public void remove(Context element, Entity entity) {
			vampire.remove(entity.getEntityId());
			if(endVampire != null) entity.sendMessage(endVampire);
		}
	}
	
	public @Configurable(Configurable.Type.ALGEBRA) AlgebraExpression duration = new AlgebraExpression("200");
	public @Configurable(Configurable.Type.ALGEBRA) AlgebraExpression adsorption = new AlgebraExpression(".5");
	
	public @Configurable(Configurable.Type.LOCALE) String beginVampire = "You've grown a pair of nails teeth...";
	public @Configurable(Configurable.Type.LOCALE) String endVampire = "You've regain your sanity...";
	public @Configurable(Configurable.Type.STRING) String buffName = "Vampire Hand";
	
	private ContextualMap<ContextualObject> map = new ContextualMap<>(context -> new ContextualObject(context));
	
	@Override
	public void spell(Context context, Entity sender, String[] params) {
		context.getBuffManager().buff(context, sender, map.get(context), 
				duration.getInt(new Parameter(params)), params);
	}
}
