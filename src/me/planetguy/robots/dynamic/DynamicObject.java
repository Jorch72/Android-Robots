package me.planetguy.robots.dynamic;

import java.util.ArrayList;

import me.planetguy.robots.world.World;
import android.graphics.Bitmap;
import android.view.View;

public class DynamicObject {
	
	public static ArrayList<DynamicObject> objectRegistry=new ArrayList<DynamicObject>();
	
	public static Bitmap defaultBitmap;
	
	public int x,y;
	
	public int heading;
	public World world;

	private int dynObjectID;
	
	public DynamicObject(World world) {
		this.world=world;
		this.heading=0;
		objectRegistry.add(this);
		dynObjectID=objectRegistry.indexOf(this);
	}
	
	public int facingX(int heading){
		if(heading==1){
			return -1;
		}else if(heading == 3){
			return 1;
		}else{
			return 0;
		}
	}
	
	public int facingY(int heading){
		if(heading==0){
			return 1;
		}else if(heading == 2){
			return -1;
		}else{
			return 0;
		}
	}
	
	public boolean onClicked(View v){
		return false;
	}

	public void moveTo(int x, int y, View v){
		this.x=x;
		this.y=y;
		world.tiles[x][y].onObjectEnter(this, v);
	}
	
	public void turnLeft(){
		heading=(heading+1)%4;
	}
	
	public void turnRight(){
		heading=(heading+3)%4;
	}
	
	public Bitmap getIcon(){
		return defaultBitmap;
		
	}
	
	public int getDynamicObjectID() {
		return dynObjectID;
	}

	public void onUpdate(View v) {
		
	}
	
	public String save(){
		return "x="+x+" y="+y+" h="+heading+" i="+dynObjectID+";";
	}
	
	public void load(String s){
		String[] objects=s.split(";");
		String[] properties=objects[0].split(" ");
		for(String prop:properties){
			String data=prop.substring(2);
			char dataStored=prop.charAt(0);
			if(dataStored=='x'){
				x=Integer.parseInt(data);
			}else if(dataStored=='y'){
				y=Integer.parseInt(data);
			}else if(dataStored=='h'){
				heading=Integer.parseInt(data);
			}else if(dataStored=='i'){
				dynObjectID=Integer.parseInt(data);
			}
		}
	}
	
	public void destroy(){
		objectRegistry.remove(this);
	}
}
