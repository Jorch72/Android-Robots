package me.planetguy.advancedvm;

import java.util.Map;


public class LimitedScript extends ImperativeScript implements Limited {
	
	public int steps=0;

	private static final long serialVersionUID = -1614013099277951512L;

	public LimitedScript(String src, IBox[][] script, Map<String, IBox> c) {
		super(src, script, c);
	}
	
	public LimitedScript(String src, IBox[][] script, Map<String, IBox> c, Scripted owner) {
		super(src, script, c, owner);
	}

	@Override
	public void onRunExtraComputation(int extra) {
		steps-=extra;
	}

	@Override
	public void doAllAllowedSteps() {
		while(steps>0){
			try {
				step();
				steps--;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void allowSteps(int steps) {
		this.steps+=steps;
	}

}
