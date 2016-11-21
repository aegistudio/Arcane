package net.aegistudio.arcane.seal;

import net.aegistudio.arcane.Context;

public class SealPaintTask implements Runnable {
	private final Context context;
	private final SealEffect seal;
	private int ticks;
	private final Painter painter;
	private final String[] arguments;
	public SealPaintTask(Context context, SealEffect seal, Painter painter, String[] arguments) {
		this.context = context;
		this.seal = seal;
		this.ticks = seal.totalPaintTick;
		this.painter = painter;
		this.arguments = arguments;
	}
	
	int task = -1;
	public void start() {
		if(task == -1) {
			task = context.getServer().getScheduler()
					.scheduleSyncRepeatingTask(context.getPlugin(), this, 0, seal.repaintTick);
		}
	}
	
	@Override
	public void run() {
		if(ticks > 0) {
			this.seal.generator.generate(context, painter, arguments);
			this.painter.end(context);
			ticks --;
		}
		else {
			context.getServer().getScheduler().cancelTask(task);
			task = -1;
		}
	}
}
