package me.planetguy.robots.world;

import java.util.ArrayList;
import java.util.List;

import me.planetguy.robots.Robots;
import me.planetguy.robots.dynamic.DynamicObject;
import me.planetguy.robots.misc.Options;
import me.planetguy.robots.misc.Side;
import me.planetguy.robots.robot.Robot;
import me.planetguy.robots.tile.Tile;
import me.planetguy.robots.tile.TileUtil;
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
	
	public static World makeTestWorld(Context context){
		World w=new World(10,10);
		Tile lava=TileUtil.tiles.get("lava");
		Tile stone=TileUtil.tiles.get("ore");
		for(int x=0; x<10; x++){
			for(int y=0; y<10; y++){
				double r=Math.random();
				if(r>0.8){
					w.tiles[x][y]=lava;
				}else if(r<0.2){
					w.tiles[x][y]=stone;
				}else{
					w.tiles[x][y]=TileUtil.tiles.get("ground");
				}
			}
		}
		Robot theRobot=new Robot(w, Side.RED,context);
		w.robots.add(theRobot);
		theRobot.x=2;
		theRobot.y=2;
		return w;
	}
	
	public static World makeMazeWorld(Context con){
		World w=new World(10,11);
		for(int x=0; x<10; x++){
			for(int y=0; y<11; y++){
				w.tiles[x][y]=TileUtil.tiles.get("ore");
			}
		}
		for(int col=1; col<11; col+=2){
			for(int row=1; row<9; row++){
				w.tiles[row][col]=TileUtil.tiles.get("ground");
			}
		}
		for(int i=2; i<10; i+=2){
			w.tiles[1+(int)(Math.random()*8)][i]=TileUtil.tiles.get("ground");
		}
		Robot theRobot=new Robot(w, Side.RED,con);
		w.robots.add(theRobot);
		theRobot.x=1;
		theRobot.y=1;
		w.tiles[8][9]=TileUtil.tiles.get("win");
		return w;
	}
	
	public World(int xSize, int ySize){
		tiles=new Tile[xSize][ySize];
		Tile ground=TileUtil.tiles.get("ground");
		for(int x=0; x<xSize; x++){
			for(int y=0; y<ySize; y++){
				tiles[x][y]=ground;
			}
		}
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
			String posOld="("+r.x+","+r.y+")";
			r.onUpdate(v);
			Log.d("Robots", "Moved from "+posOld+" to ("+r.x+","+r.y+")");
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
