package me.planetguy.robots.world;

import java.util.PriorityQueue;

public class ScheduleQueue {
	
	private PriorityQueue<Update> updates=new PriorityQueue<Update>();
	
	public void schedule(Update u){
		updates.add(u);
	}
	
	public boolean hasUpdates(int globalTime){
		return !updates.isEmpty()&&updates.peek().getTime()<=globalTime;
	}
	
	public Update getNextUpdate(){
		return updates.poll();
	}
	
	public Update pullNextUpdate(){
		return updates.remove();
	}

}
