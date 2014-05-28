package me.planetguy.robots.renderworld;

import me.planetguy.robots.R;
import me.planetguy.robots.Robots;
import me.planetguy.robots.dynamic.DynamicObject;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

public class ActivityWorld extends Activity {

	ViewWorld vw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vw=new ViewWorld(this);
		setContentView(vw);
		vw.invalidate();
		SharedPreferences prefs=getSharedPreferences("dynObjectData", 0);
		int i=0;
		while(prefs.contains("robot_"+i)){
			DynamicObject obj=new DynamicObject(Robots.world);
			obj.load(prefs.getString("robot_"+i, ""));
			i++;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onStop(){
		super.onStop();
		SharedPreferences prefs=getSharedPreferences("dynObjectData", 0);
		SharedPreferences.Editor ed=prefs.edit();
		int i=0;
		for(DynamicObject r:Robots.world.robots){
			ed.putString("robot_"+i, r.save());
			i++;
		}
		ed.commit();
	}


}
