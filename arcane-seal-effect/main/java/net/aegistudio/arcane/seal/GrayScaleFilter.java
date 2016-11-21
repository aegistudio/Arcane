package net.aegistudio.arcane.seal;

import java.awt.image.Raster;
import net.aegistudio.arcane.config.ConfigurationSection;

public class GrayScaleFilter implements Filter {
	boolean white = false; public static final String WHITE = "white";
	int threshold = 127; public static final String THRESHOLD = "threshold";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		if(config.contains(WHITE)) white = config.getBoolean(WHITE);
		if(config.contains(THRESHOLD)) threshold = config.getInt(THRESHOLD);
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		config.set(WHITE, white);
		config.set(THRESHOLD, threshold);
	}

	@Override
	public boolean pick(Raster image, int x, int y) {
		int value = image.getSample(x, y, 0);
		if(white) return value >= threshold;
		else return value <= threshold;
	}
}
