package me.planetguy.robots.tile;

import me.planetguy.robots.dynamic.DynamicObject;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.ScaleAnimation;

public class TilePitTrap extends Tile {

	public TilePitTrap(int i, String name, Resources rsrc) {
		super(i,rsrc,name);
		this.setWalkable(true);
	}

	public void onObjectEnter(DynamicObject obj, View v){
		obj.destroy();
	}
	
}
