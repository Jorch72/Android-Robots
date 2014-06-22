package me.planetguy.robots.world.gen;

import java.util.ArrayList;
import java.util.Arrays;

import me.planetguy.robots.R;
import me.planetguy.robots.Robots;
import me.planetguy.robots.renderworld.ActivityWorld;
import me.planetguy.robots.world.World;
import me.planetguy.robots.world.gen.generators.WgenSimpleMaze;
import me.planetguy.robots.world.gen.generators.WgenTest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityWgenChooser extends Activity {
	
	public ArrayList<WorldProvider> providers=new ArrayList<WorldProvider>();
	
	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
		providers.addAll(Arrays.asList(new WorldProvider[]{
				new WgenTest(),
				new WgenSimpleMaze()
		}));
		
		setContentView(R.layout.activity_levels);
		ListView listView = (ListView) findViewById(R.id.levelsList);
		listView.setAdapter(new WgenAdapter(getApplicationContext(), providers));
		
		OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,	long id) {
				Robots.world=providers.get(pos).generate(v.getContext(), new TilePainter());
				Intent intent=new Intent(v.getContext(), ActivityWorld.class);
				startActivity(intent);
			}
		};
		listView.setOnItemClickListener(mMessageClickedHandler); 
	}

}
