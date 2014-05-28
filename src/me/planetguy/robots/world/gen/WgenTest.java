package me.planetguy.robots.world.gen;

import me.planetguy.robots.R;
import me.planetguy.robots.misc.Side;
import me.planetguy.robots.robot.Robot;
import me.planetguy.robots.tile.Tile;
import me.planetguy.robots.tile.TileUtil;
import me.planetguy.robots.world.World;
import android.content.Context;
import android.content.res.Resources;

public class WgenTest extends WorldProvider{

	public WgenTest() {
		super(R.drawable.hq_red_shields);
	}

	@Override
	public String getName() {
		return "Test";
	}

	@Override
	public String getDescription() {
		return "Test generator 1";
	}

	@Override
	public World generate(Context context){
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
	
}
