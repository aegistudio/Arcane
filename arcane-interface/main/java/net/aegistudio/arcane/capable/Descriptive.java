package net.aegistudio.arcane.capable;

import net.aegistudio.arcane.Context;

/**
 * Allow describing information of an effect.
 * 
 * @author aegistudio
 */

public interface Descriptive extends Capability {
	/**
	 * Describe an effect in the effect database.
	 * 
	 * @param context the context provided.
	 * @param effect the effect name. (if not exists or not supported, null will be returned.)
	 * @param parameters the parameters for effect execution.
	 * @return
	 */
	public String describe(Context context, String effect, String[] parameters);
}
