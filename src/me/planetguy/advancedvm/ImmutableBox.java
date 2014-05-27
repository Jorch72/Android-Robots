package me.planetguy.advancedvm;

public class ImmutableBox extends Box{

	private static final long serialVersionUID = -2784513845533561808L;

	public ImmutableBox(Object o){
		super(o);
	}
	
	public void set(Object o){
		throw new RuntimeException("Tried to set an ImmutableBox! Oh noes!");
	}
}
