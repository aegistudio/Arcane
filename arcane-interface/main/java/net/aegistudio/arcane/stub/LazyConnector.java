package net.aegistudio.arcane.stub;

import net.aegistudio.arcane.Engine;

public class LazyConnector extends DeftConnector {
	protected Engine connected;
	
	@Override
	protected Engine connectEngine() {
		if(connected == null) 
			connected = super.connectEngine();
		return connected;
	}
}
