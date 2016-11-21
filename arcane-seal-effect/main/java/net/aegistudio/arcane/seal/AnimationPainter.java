package net.aegistudio.arcane.seal;

import net.aegistudio.arcane.Context;

public class AnimationPainter implements Painter {
	protected final Painter[] painter;
	protected final Integer[] tick;
	
	protected int index;
	protected int frame;
	
	public AnimationPainter(Painter[] painter, Integer[] tick) {
		this.painter = painter;
		this.tick = tick;
		
		this.index = 0;
		this.frame = 0;
	}
	
	@Override
	public void paint(Context context, double x, double y, double z, String[] arguments) {
		if(index < painter.length)
			painter[index].paint(context, x, y, z, arguments);
	}

	@Override
	public void end(Context context) {
		if(index < tick.length) {
			painter[index].end(context);
			frame ++;
			
			if(tick[index] >= 0 && frame > tick[index]) {
				index ++;
				frame = 0;
			}
		}
	}
}
