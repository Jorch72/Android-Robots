package me.planetguy.robots.tile;

import me.planetguy.robots.R;
import android.content.res.Resources;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class TileUtil {

	public static BiMap<String, Tile> tiles=HashBiMap.create();

	public static Tile createTile(int icon, String name, Resources rsrc){
		@SuppressWarnings("deprecation")
		Tile t=new Tile(icon, rsrc, name);
		tiles.put(name, t);
		return t;
	}
	
	public static Tile createTileWin(int icon, String name, Resources rsrc){
		Tile t=new TileWin(icon, rsrc, name);
		tiles.put(name, t);
		return t;
	}

	public static void createAllTiles(Resources rsrc){
		createTile(R.drawable.ground, "ground", rsrc);
		createTile(R.drawable.lava, "lava", rsrc);
		createTile(R.drawable.ore, "ore", rsrc);
		createTile(R.drawable.rock, "rock", rsrc);
		createTileWin(R.drawable.lava, "win", rsrc);
		tiles.get("ground").setWalkable(true);
	}

}
