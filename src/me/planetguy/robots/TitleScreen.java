package me.planetguy.robots;

import me.planetguy.robots.renderworld.ActivityWorld;
import me.planetguy.robots.tile.Tiles;
import me.planetguy.robots.world.World;
import me.planetguy.robots.world.gen.ActivityWgenChooser;
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
		Tiles.createAllTiles(getResources());
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
		Intent intent=new Intent(this.getApplicationContext(), ActivityWgenChooser.class);
		startActivity(intent);
	}
}
