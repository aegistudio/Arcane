package net.aegistudio.arcane.seal;

import net.aegistudio.arcane.Context;

public class FilteredPainter implements Painter {
	protected final Painter next;
	protected int scanInterval;
	protected int tick;
	protected int counter;
	
	public FilteredPainter(Painter next, int scanInterval) {
		this.next = next;
		this.scanInterval = scanInterval;
		this.tick = 0;
		this.counter = 0;
	}
	
	@Override
	public void paint(Context context, double x, double y, double z, String[] arguments) {
		if(counter % scanInterval == tick)
			next.paint(context, x, y, z, arguments);
		counter ++;
	}

	@Override
	public void end(Context context) {
		tick = (tick + 1) % scanInterval;
		counter = 0;
		this.next.end(context);
	}
}
