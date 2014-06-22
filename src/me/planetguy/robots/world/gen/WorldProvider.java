package me.planetguy.robots.world.gen;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import me.planetguy.robots.world.World;

public abstract class WorldProvider {
	
	public int image;
	
	public WorldProvider(int icon){
		this.image=icon;
	}
	
	public abstract String getName();
	public abstract String getDescription();
	
	public abstract World generate(Context context, TilePainter wgu);
	
	public int getImage(){
		return image;
	}

}
