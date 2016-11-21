package net.aegistudio.magick.effect;

import java.util.HashSet;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.aegistudio.arcane.*;
import net.aegistudio.arcane.config.*;
import net.aegistudio.arcane.expr.*;

/**
 * Make player immune to some debuffs.
 * @author aegistudio
 */

public class PotionResistance implements SpellEffect {
	class ContextualObject extends VolatileObject implements Buff, Listener, Runnable {
		private final Context context;
		public ContextualObject(Context context) {
			this.context = context;
			context.getServer().getPluginManager()
				.registerEvents(this, context.getPlugin());
		}
		
		private final HashSet<LivingEntity> resistance = new HashSet<LivingEntity>();
		
		@Override
		public String name() {
			return buffName;
		}

		@Override
		public void buff(Context element, Entity entity, String[] params) {
			if(!(entity instanceof LivingEntity)) return;
			LivingEntity living = ((LivingEntity)entity);
			living.removePotionEffect(potionEffect);
			if(beginEffect != null) entity.sendMessage(beginEffect);
			this.resistance.add((LivingEntity) entity);
		}

		@Override
		public void remove(Context element, Entity entity) {
			if(!(entity instanceof LivingEntity)) return;
			if(endEffect != null) entity.sendMessage(endEffect);
			this.resistance.remove(entity);
		}
		
		@Override
		public void run() {
			for(LivingEntity entity : resistance) 
				entity.removePotionEffect(potionEffect);
		}
		
		@EventHandler
		public void handlePotion(PotionSplashEvent event) {
			for(PotionEffect effect : event.getPotion().getEffects())
				if(effect.getType() == potionEffect)
					doProtect();
		}
		
		public void doProtect() {
			context.getServer().getScheduler().runTask(context.getPlugin(), this);
		}
		
		@EventHandler
		public void handleDamage(EntityDamageEvent event) {
			if(!this.resistance.contains(event.getEntity())) return;
			
			if(event.getCause() == DamageCause.WITHER && potionEffect == PotionEffectType.WITHER) {
				event.setCancelled(true);
				doProtect();
			}
			if(event.getCause() == DamageCause.POISON && potionEffect == PotionEffectType.POISON) {
				event.setCancelled(true);
				doProtect();
			}
			if(event.getCause() == DamageCause.MAGIC && potionEffect == PotionEffectType.HARM) 
				event.setCancelled(true);
		}
	}
	
	public @Configurable(Configurable.Type.STRING) String buffName = "Resistance";
	public @Configurable(Configurable.Type.LOCALE) String beginEffect;
	public @Configurable(Configurable.Type.LOCALE) String endEffect;
	public @Configurable(Configurable.Type.ALGEBRA) AlgebraExpression duration = new AlgebraExpression("50");
	
	public static final String POTIONEFFECT_TYPE = "potion";
	public PotionEffectType potionEffect;
	
	public PotionResistance() {}
	public PotionResistance(PotionEffectType effect) {
		potionEffect = effect;
		beginEffect = "You're now immune to " + effect.getName().toLowerCase() + "!";
		endEffect = "You're vulnerable to " + effect.getName().toLowerCase() + " again...";
	}

	private final ContextualMap<ContextualObject> map = new ContextualMap<>(context -> new ContextualObject(context));
	
	@Override
	public void spell(Context context, Entity sender, String[] params) {
		context.getBuffManager().buff(context, sender, map.get(context), 
				duration.getInt(new Parameter(params)), params);
	}

	@Override
	public void load(ConfigurationSection spellConfig) throws Exception {
		spellConfig.loadConfigurable(this);
		if(spellConfig.contains(POTIONEFFECT_TYPE))
			this.potionEffect = PotionEffectType.getByName(spellConfig.getString(POTIONEFFECT_TYPE));
	}

	@Override
	public void save(ConfigurationSection spellConfig) throws Exception {
		spellConfig.saveConfigurable(this);
		spellConfig.set(POTIONEFFECT_TYPE, potionEffect.getName());
	}
}
