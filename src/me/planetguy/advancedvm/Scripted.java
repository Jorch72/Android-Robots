package me.planetguy.advancedvm;

/**
 * Designates an object that has a Script. For example, a Script has a script (itself) and
 * a "computer" object in a game might have a script, and both these objects would indicate
 * this by implementing this interface.
 *
 */

public interface Scripted {
	
	public Script getScript();

	public void print(Object o);
	
}
