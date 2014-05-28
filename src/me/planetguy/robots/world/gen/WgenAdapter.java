package me.planetguy.robots.world.gen;

import java.util.ArrayList;

import me.planetguy.robots.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WgenAdapter extends ArrayAdapter<WorldProvider>{

	private ArrayList<WorldProvider> objects;
	
	public WgenAdapter(Context context, ArrayList<WorldProvider> objects) {
		super(context, R.layout.worldgen_option, objects);
		this.objects = objects;
	}
	
	@Override
	public View getView(int idx, View v, ViewGroup parent){
		if(v==null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=inflater.inflate(R.layout.worldgen_option, null);
		}
		if(objects.get(idx)!=null){
			TextView title=(TextView) v.findViewById(R.id.worldGenTitle);
			TextView desc=(TextView) v.findViewById(R.id.worldGenDetails);
			ImageView icon=(ImageView) v.findViewById(R.id.worldGenIcon);
			
			title.setText(objects.get(idx).getName());
			title.setTextColor(0xff000000);
			desc.setText(objects.get(idx).getDescription());
			desc.setTextColor(0xff000000);
			icon.setImageResource(objects.get(idx).getImage());
		}
		return v;
	}

}
