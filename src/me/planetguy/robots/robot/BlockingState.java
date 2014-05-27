package me.planetguy.robots.robot;

import android.util.Log;
import android.view.View;

public enum BlockingState {
	
	none,
	toolUse,
	forward,
	left,
	right,
	reverse, 
	gonorth,
	gosouth,
	goeast,
	gowest;
	
	public void execute(Robot r, View v){
		switch(this){
		case forward:
			r.moveTo(r.x+r.facingX(r.heading), r.y+r.facingY(r.heading), v);
			break;
		case left:
			r.turnLeft();
			break;
		case right:
			r.turnRight();
			break;
		case reverse:
			r.moveTo(r.x-r.facingX(r.heading), r.y-r.facingY(r.heading), v);
			break;
		case none:
			break;
		case toolUse:
			break;
		case gonorth:
			r.moveTo(r.x, r.y-1, v);
			break;
		case gosouth:
			r.moveTo(r.x, r.y+1, v);
			break;
		case goeast:
			r.moveTo(r.x+1, r.y, v);
			break;
		case gowest:
			r.moveTo(r.x-1, r.y, v);
			break;
		default:
			break;
		}
	}

}
