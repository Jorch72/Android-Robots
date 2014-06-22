package me.planetguy.robots.world;

import java.util.ArrayList;
import java.util.List;

import me.planetguy.robots.Robots;
import me.planetguy.robots.dynamic.DynamicObject;
import me.planetguy.robots.misc.Options;
import me.planetguy.robots.misc.Side;
import me.planetguy.robots.robot.Robot;
import me.planetguy.robots.tile.Tile;
import me.planetguy.robots.tile.Tiles;
import android.content.Context;
import android.util.Log;
import android.view.View;

public class World {
	
	public Tile[][] tiles;
	
	public boolean[][] visible;
	
	long lastTick=System.currentTimeMillis();
	
	public List<DynamicObject> robots=new ArrayList<DynamicObject>();
	
	private ScheduleQueue scheduledUpdates=new ScheduleQueue();
	
	public int globalTime=0;
	
	public World(int xSize, int ySize){
		tiles=new Tile[xSize][ySize];
		visible=new boolean[xSize][ySize];
	}
	
	public void addRobot(Robot r){
		
	}
	
	public void tryTick(View v){
		long millis=System.currentTimeMillis();
		long timeSinceTick=millis-lastTick;
		if(timeSinceTick>Options.millisPerTick){
			tick(v);
			lastTick=millis;
		}
	}
	
	private void tick(View v){
		while(scheduledUpdates.hasUpdates(globalTime)){
			Update u=scheduledUpdates.getNextUpdate();
			tiles[u.getX()][u.getY()].onUpdate(u.getX(), u.getY());
		}
		for(DynamicObject r:robots){
			r.onUpdate(v);
		}
		globalTime++;
	}
	
	public void scheduleUpdate(int x, int y, int ticks){
		scheduledUpdates.schedule(new Update(x,y,ticks));
	}
	
	public boolean handleClick(View v,int x, int y){
		for(DynamicObject dynobj:robots){
			if(dynobj.x==x&&dynobj.y==y){
				return dynobj.onClicked(v);
			}
		}
		return tiles[x][y].onClicked(v, x, y);
	}
	
	public Tile getTile(int x, int y){
		if(x<tiles.length&&x>=0&&
				y<tiles[0].length&&y>=0	){
			return tiles[x][y];
		}else{
			return null;
		}
				
	}
	
}
