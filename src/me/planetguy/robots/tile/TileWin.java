package me.planetguy.robots.tile;

import me.planetguy.robots.dynamic.DynamicObject;
import me.planetguy.robots.robot.Robot;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;

public class TileWin extends Tile{

	@SuppressWarnings("deprecation")
	public TileWin(int icon, Resources rsrc, String name) {
		super(icon, rsrc, name);
		this.setWalkable(true);
	}
	
	public void onObjectEnter(DynamicObject obj, View v){
		if(obj instanceof Robot){
			Robot r=(Robot)obj;
			Intent intent=new Intent(r.worldGui, ActivityWin.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			v.getContext().startActivity(intent);
		}
	}
	
}
