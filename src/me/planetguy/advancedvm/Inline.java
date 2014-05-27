package me.planetguy.advancedvm;
import java.util.List;

/**
 * Interface for inline (compile-time replacement) functions in scripting.
 */
public interface Inline {
	
	/**
	 * The function name for the Inline to respond to. For example, if name() returns "for", the context will call replace 
	 * with any line whose first word is "for".
	 * 
	 * Note that registering an Inline with a different name than its own is possible.
	 */
	public String name();
	
	public List<String> replace(String[] line);

}
