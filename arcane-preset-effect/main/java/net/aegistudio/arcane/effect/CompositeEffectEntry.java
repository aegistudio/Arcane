package net.aegistudio.arcane.effect;

import net.aegistudio.arcane.Effect;
import net.aegistudio.arcane.expr.AlgebraExpression;

public class CompositeEffectEntry {
	public Effect effect;
	
	public AlgebraExpression probability; { 
		try {
			probability = new AlgebraExpression("1.0");
		}
		catch(Exception e) {
			
		}
	}
}
