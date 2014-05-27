package me.planetguy.robots.tile;

import android.content.res.Resources;
import me.planetguy.robots.misc.Side;

public class TileSided extends Tile {

	private Side side;
	
	@SuppressWarnings("deprecation")
	public TileSided(int icon, Resources rsrc, Side side, String name) {
		super(icon,rsrc, name);
		// TODO Auto-generated constructor stub
		this.side=side;
	}
	
	public Side getSide(){
		return side;
	}
	
	public void updateSide(Side side){
		this.side=side;
	}

}
