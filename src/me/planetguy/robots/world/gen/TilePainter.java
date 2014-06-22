package me.planetguy.robots.world.gen;

import android.content.Context;
import me.planetguy.robots.misc.Side;
import me.planetguy.robots.robot.Robot;
import me.planetguy.robots.tile.Tile;
import me.planetguy.robots.tile.Tiles;
import me.planetguy.robots.world.World;

public class TilePainter {
	

	private Tile workingTile=Tiles.rock;
	private World workingWorld;
	
	public World makeWorld(int xLength, int yLength){
		workingWorld=new World(xLength,yLength);
		return workingWorld;
	}
	
	public void drawBorder(){
		int xLength=workingWorld.tiles.length;
		int yLength=workingWorld.tiles[0].length;
		drawVerticalLine(0,0,yLength);
		drawHorizontalLine(0,xLength,0);
		drawVerticalLine(xLength-1,0,yLength);
		drawHorizontalLine(0,xLength,yLength-1);
	}
	
	public World drawVerticalLine(int x, int y1, int y2){
		for(int y=y1; y<y2; y++){
			paint(x,y);
		}
		return workingWorld;
	}
	
	public World drawHorizontalLine(int x1, int x2, int y){
		for(int x=x1; x<x2; x++){
			paint(x,y);
		}
		return workingWorld;
	}
	
	public void paint(int x, int y){
		if(workingWorld.tiles.length>x&&x>=0
				&&workingWorld.tiles[x].length>y&&y>=0){
			workingWorld.tiles[x][y]=workingTile;
		}
	}
	
	public void paint(int x, int y, Tile t){
		workingTile=t;
		paint(x,y);
	}
	
	public void fillOut(Tile t){
		workingTile=t;
		for(int x=0; x<workingWorld.tiles.length; x++){
			for(int y=0; y<workingWorld.tiles[x].length; y++){
				if(workingWorld.tiles[x][y]==null)
					paint(x,y);
			}
		}
	}
	
	public World validate(){
		workingTile=Tiles.pitTrap;
		for(int x=0; x<workingWorld.tiles.length; x++){
			for(int y=0; y<workingWorld.tiles[x].length; y++){
				if(workingWorld.tiles[x][y]==null)
					paint(x,y);
			}
		}
		return workingWorld;
	}

	public World getWorkingWorld() {
		return workingWorld;
	}

	public void setWorkingWorld(World workingWorld) {
		this.workingWorld = workingWorld;
	}

	public Tile getWorkingTile() {
		return workingTile;
	}

	public void setWorkingTile(Tile workingTile) {
		this.workingTile = workingTile;
	}
	
	public void putRobot(Context con, int x, int y){
		Robot theRobot=new Robot(workingWorld, Side.RED,con);
		workingWorld.robots.add(theRobot);
		theRobot.x=1;
		theRobot.y=1;
	}

}
