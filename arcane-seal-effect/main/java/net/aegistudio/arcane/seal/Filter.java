package net.aegistudio.arcane.seal;

import java.awt.image.Raster;
import net.aegistudio.arcane.Module;

public interface Filter extends Module {
	public boolean pick(Raster image, int x, int y);
}
