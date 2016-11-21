package net.aegistudio.arcane.seal;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import net.aegistudio.arcane.Context;

public class YawOrientPainter extends TransformPainter {
	
	protected final Entity entity;
	public YawOrientPainter(Painter next, Entity entity) {
		super(next, new double[][] {
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}}
		);
		this.entity = entity;
		this.calculateTranslation(entity.getLocation());
	}

	@Override
	public void end(Context context) {
		this.calculateTranslation(entity.getLocation());
		this.next.end(context);
	}
	
	public static final double FACTOR = Math.PI / 180;
	
	public void calculateTranslation(Location orient) {
		super.setZero();
		
		double cosRotActual = Math.cos(orient.getYaw() * FACTOR);
		double sinRotActual = Math.sin(orient.getYaw() * FACTOR);
		actual[0][0] =  cosRotActual;
		actual[0][1] = -sinRotActual;
		actual[1][0] =  sinRotActual;
		actual[1][1] =  cosRotActual;
		actual[2][2] = 1;
		actual[3][3] = 1;
	}
}
