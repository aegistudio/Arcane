package net.aegistudio.arcane;

import java.util.HashMap;
import java.util.function.Function;

public class ContextualMap<T> {
	private final HashMap<Context, T> map = new HashMap<>();
	private final Function<Context, T> abscence;
	public ContextualMap(Function<Context, T> abscence) {
		this.abscence = abscence;
	}
	
	public T get(Context context) {
		T t = map.get(context);
		if(t == null) map.put(context, t = abscence.apply(context));
		return t;
	}
}
