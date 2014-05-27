package me.planetguy.advancedvm;


/**
 * Box that doesn't do anything. The empty string "" is by default one of these.
 */

public enum NullBox implements IBox {

	instance;
	
	@Override
	public void set(Object o) {
		
	}

	@Override
	public Object get() {
		return null;
	}
	
	public String toString(){
		return "NullBox";
	}
	
	

}
