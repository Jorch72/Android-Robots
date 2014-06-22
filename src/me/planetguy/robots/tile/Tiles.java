package me.planetguy.robots.tile;

import me.planetguy.robots.R;
import android.content.res.Resources;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Tiles {

	private static BiMap<String, Tile> tiles=HashBiMap.create();

	private static Tile createTile(int icon, String name, Resources rsrc){
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
		ground=createTile(R.drawable.ground, "ground", rsrc);
		lava=createTile(R.drawable.lava, "lava", rsrc);
		ore=createTile(R.drawable.ore, "ore", rsrc);
		rock=createTile(R.drawable.rock, "rock", rsrc);
		win=createTileWin(R.drawable.lava, "win", rsrc);
		pitTrap=new TilePitTrap(R.drawable.pit, "pitTrap", rsrc);
		tiles.put(pitTrap.name,pitTrap);
		tiles.get("ground").setWalkable(true);
	}
	
	public static Tile ground,
	lava,
	ore,
	rock,
	win,
	pitTrap;

}
