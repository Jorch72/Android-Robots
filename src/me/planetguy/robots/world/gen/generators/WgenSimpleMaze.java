package me.planetguy.robots.world.gen.generators;

import me.planetguy.robots.R;
import me.planetguy.robots.misc.Side;
import me.planetguy.robots.robot.Robot;
import me.planetguy.robots.tile.Tiles;
import me.planetguy.robots.world.World;
import me.planetguy.robots.world.gen.TilePainter;
import me.planetguy.robots.world.gen.WorldProvider;
import android.content.Context;
import android.content.res.Resources;

public class WgenSimpleMaze extends WorldProvider{

	public WgenSimpleMaze() {
		super(R.drawable.lava);
	}

	@Override
	public String getName() {
		return "Simple maze";
	}

	@Override
	public String getDescription() {
		return "A very simple maze";
	}

	@Override
	public World generate(Context con, TilePainter paint){
		paint.makeWorld(10, 11);
		paint.setWorkingTile(Tiles.ore);
		paint.drawBorder();
		paint.fillOut(Tiles.ore);
		paint.setWorkingTile(Tiles.ground);
		for(int col=1; col<11; col+=2){
			paint.drawHorizontalLine(1, 9, col);
		}
		for(int i=2; i<10; i+=2){
			paint.paint(1+(int)(Math.random()*8),i, Tiles.ground);
		}
		paint.paint(8,9,Tiles.win);
		paint.putRobot(con, 1, 1);
		return paint.validate();
	}

}
