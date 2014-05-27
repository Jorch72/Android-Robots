package me.planetguy.advancedvm;

public interface Limited {
	
	public void onRunExtraComputation(int extra);
	
	public void doAllAllowedSteps();
	
	public void allowSteps(int steps);

}
