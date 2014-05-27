package me.planetguy.advancedvm;
import java.io.Serializable;

public interface IBox extends Serializable{
	
	public void set(Object o);
	
	public Object get();

}