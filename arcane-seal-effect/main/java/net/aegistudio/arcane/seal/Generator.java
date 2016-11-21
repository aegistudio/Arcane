package net.aegistudio.arcane.seal;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.Module;

/**
 * A generator is used to generate 2-D matrix
 * signal.
 * 
 * @author aegistudio
 */

public interface Generator extends Module {
	public void generate(Context context, Painter painter, String[] arguments);
}
