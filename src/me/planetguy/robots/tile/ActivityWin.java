package me.planetguy.robots.tile;

import java.util.ArrayList;

import me.planetguy.robots.R;
import me.planetguy.robots.Robots;
import me.planetguy.robots.dynamic.DynamicObject;
import me.planetguy.robots.renderworld.ActivityWorld;
import me.planetguy.robots.world.World;
import me.planetguy.robots.world.gen.ActivityWgenChooser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityWin extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_won);
	}
	
	public void onStart(){
		super.onStart();
		TextView tv=(TextView)findViewById(R.id.win_textbox);
		tv.setText(tv.getText()+" Time taken:: "+Robots.world.globalTime);
	}
	
	public void clearWorld(View v){
		DynamicObject.objectRegistry=new ArrayList<DynamicObject>();
		Intent intent=new Intent(this, ActivityWgenChooser.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(intent);
	}
	
}