package me.planetguy.robots.robot;

import android.util.Log;
import me.planetguy.advancedvm.ScriptAPI;
import me.planetguy.advancedvm.Scripted;
import me.planetguy.advancedvm.VMContext;
import me.planetguy.robots.tile.Tile;

public class RobotScriptAPI {

	public static void initActions(VMContext script) {
		for(BlockingState state:BlockingState.values()){
			script.registerFinalObj(state.name(), state);
		}
		script.registerClass(RobotScriptAPI.class, "robot");
	}
	
	@ScriptAPI
	public static void move(Scripted r, BlockingState state){
		Log.d("Robots", "Set blocking state to "+state);
		((Robot)r).waitingOn=state;
	}
	
}
