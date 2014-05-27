package me.planetguy.robots;

import me.planetguy.robots.ide.IDEActivity;
import me.planetguy.robots.world.World;

import com.google.common.eventbus.EventBus;

public class Robots {
	
	public static EventBus EVENT_BUS=new EventBus();
	
	public static World world;

	public static int iconSize;
	
	public static IDEActivity currentIDE;
	
}
