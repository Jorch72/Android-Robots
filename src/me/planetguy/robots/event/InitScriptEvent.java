package me.planetguy.robots.event;

import me.planetguy.advancedvm.VMContext;

public abstract class InitScriptEvent {
	
	private final VMContext sc;
	
	public InitScriptEvent(VMContext script){
		sc=script;
	}

	public VMContext getContext() {
		return sc;
	}

}
