package net.aegistudio.arcane.seal;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.aegistudio.arcane.Context;
import net.aegistudio.arcane.config.ConfigurationSection;

public class SparseGenerator implements Generator {
	@Override
	public void generate(Context context, Painter painter, String[] arguments) {
		for(int i = 0; i < x.length; i ++)
			painter.paint(context, x[i], y[i], 0, arguments);
	}

	private Filter filter = new GrayScaleFilter();
	private Double[] x, y;
	public void parse(Raster raster) {
		int w = raster.getWidth();
		int h = raster.getHeight();
		ArrayList<Double> x = new ArrayList<Double>();
		ArrayList<Double> y = new ArrayList<Double>();
		
		for(int i = 0; i < w; i += resolution)
			for(int j = 0; j < h; j += resolution)
				if(filter.pick(raster, i, j)) {
					x.add(2.0 * i / w - 1.0);
					y.add(2.0 * j / h - 1.0);
				}
		
		this.x = x.toArray(new Double[0]);
		this.y = y.toArray(new Double[0]);
	}

	private int resolution = 1;
	public static final String RESOLUTION = "resolution";
	
	
	private File source;
	public static final String SOURCE_IMAGE = "source";
	public static final String FILTER_CLAZZ = "filterClass";
	public static final String FILTER_CONFIG = "filterConfig";
	
	@Override
	public void load(ConfigurationSection config) throws Exception {
		if(config.contains(RESOLUTION)) resolution = config.getInt(RESOLUTION);
		source = new File(config.getDataFolder(), config.getString(SOURCE_IMAGE));
		filter = config.loadInstance(Filter.class, FILTER_CLAZZ, GrayScaleFilter.class, FILTER_CONFIG, null);

		if(source.exists()) {
			BufferedImage image = ImageIO.read(source);
			this.parse(image.getRaster());
		}
	}

	@Override
	public void save(ConfigurationSection config) throws Exception {
		config.set(RESOLUTION, resolution);
		config.set(SOURCE_IMAGE, source != null? source.getName() : "<image-name>");
		config.saveInstance(filter, FILTER_CLAZZ, FILTER_CONFIG);
	}
}
