package net.aegistudio.arcane.seal;

import net.aegistudio.arcane.Context;

/**
 * A painter accepts 2-D matrix and reflects
 * it into the world.
 * 
 * @author aegistudio
 */

public interface Painter {
	public void paint(Context context, double x, double y, double z, String[] arguments);
	
	public void end(Context context);
}
