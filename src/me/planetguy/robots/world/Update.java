package me.planetguy.robots.world;

public class Update implements Comparable<Update>{

	private final int time,x,y;
	
	public Update(int x, int y,int globalTime){
		this.time=globalTime;
		this.x=x;
		this.y=y;
	}
	
	@Override
	public int compareTo(Update paramT) {
		return time-paramT.time;
	}
	
	public int getTime(){
		return time;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
}
