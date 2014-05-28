package me.planetguy.robots.world.gen;

import me.planetguy.robots.tile.Tile;
import me.planetguy.robots.tile.TileUtil;
import me.planetguy.robots.world.World;

public class WorldEditor {
	

	public Tile workingTile=TileUtil.tiles.get("rock");
	
	public World generateBorderedWorld(int xLength, int yLength){
		World w=new World(xLength,yLength);
		drawHorizontalLine(w,0,0,yLength);
		drawVerticalLine(w,0,0,xLength);
		drawHorizontalLine(w,xLength-1,0,yLength);
		drawVerticalLine(w,0,xLength-1,yLength);
		return w;
	}
	
	public World drawHorizontalLine(World w, int x, int y1, int y2){
		for(int i=y1; i<y2; i++){
			w.tiles[x][i]=workingTile;
		}
		return w;
	}
	
	public World drawVerticalLine(World w, int x1, int x2, int y){
		for(int i=x1; i<x2; i++){
			w.tiles[i][y]=workingTile;
		}
		return w;
	}
	
	public World validate(World w){
		for(int x=0; x<w.tiles.length; x++){
			for(int y=0; y<w.tiles[x].length; y++){
				if(w.tiles[x][y]==null)
					w.tiles[x][y]=workingTile;
			}
		}
		return w;
	}

}
