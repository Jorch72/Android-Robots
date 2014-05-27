package me.planetguy.robots;

import me.planetguy.robots.renderworld.ActivityWorld;
import me.planetguy.robots.tile.TileUtil;
import me.planetguy.robots.world.World;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class TitleScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TileUtil.createAllTiles(getResources());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onStart(){
		super.onStart();
	}
	
	public void onClickStartGame(View v){
		Robots.world=World.makeMazeWorld(this.getApplicationContext());
		Intent intent=new Intent(this.getApplicationContext(), ActivityWorld.class);
		startActivity(intent);
	}
}
