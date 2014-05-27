package me.planetguy.advancedvm;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptAPI {
	
	public String value() default "__NULL__";

}
