package net.aegistudio.magick.effect;

import net.aegistudio.arcane.SpellEffect;
import net.aegistudio.arcane.expr.AlgebraExpression;

public class CompositeEffectEntry {
	public SpellEffect effect;
	
	public AlgebraExpression probability; { 
		try {
			probability = new AlgebraExpression("1.0");
		}
		catch(Exception e) {
			
		}
	}
}
