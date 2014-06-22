package me.planetguy.robots.tile;


import me.planetguy.robots.Robots;
import me.planetguy.robots.dynamic.DynamicObject;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

/**
 * A tile that exists in the world. Should be created as singletons.
 * @author planetguy
 *
 */
public class Tile {
	
	public Bitmap bitmapIcon;
	
	public final String name;
	
	private boolean walkable;
	
	/**
	 * Creates a tile using a resource (R.drawable.<icon name>). 
	 * @deprecated use {@link Tiles.createDefaultTile} to create custom tiles, or add them manually
	 */
	@Deprecated
	protected Tile(int icon, Resources rsrc, String name){
		this(BitmapFactory.decodeResource(rsrc, icon), name);
	}
	
	/**
	 * Creates a tile using a bitmap.
	 * @deprecated use {@link Tiles.createDefaultTile} to create custom tiles, or add them manually
	 */
	@Deprecated
	protected Tile(Bitmap bm, String name){
		this.bitmapIcon=bm;
		Robots.iconSize=bm.getHeight();
		this.name=name;
	}
	
	/**
	 * 
	 * @param view the canvas we are drawing on.
	 * @return whether anything has been done.
	 */
	public boolean onClicked(View view, int x, int y){
		Log.d("Robots", "Click at "+x+","+y);
		return false;
	}
	
	public boolean hasAdditionalData(){
		return false;
	}

	public void onUpdate(int x, int y){
		
	}
	
	public void onMove(int x1, int y1, int x2, int y2){
		onPut(x2,y2);
	}
	
	public void onPut(int x, int y){
		
	}
	
	public Bitmap getIcon(int x, int y){
		return this.bitmapIcon;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
	
	public void onObjectEnter(DynamicObject obj, View v){
		
	}

}
