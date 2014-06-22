package me.planetguy.robots.init;

import me.planetguy.robots.R;
import me.planetguy.robots.misc.Options;
import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ActivityConfig extends Activity{
	
	static final int maxTps=20;
	
	static final int maxCycles=50;
	
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.activity_opts);
		SeekBar bar=(SeekBar)findViewById(R.id.gameSpeedSeekBar);
		bar.setMax(maxTps);
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int currentVal, boolean arg2) {
				Options.millisPerTick=1/(currentVal+1);
			}

			public void onStartTrackingTouch(SeekBar arg0) {}
			public void onStopTrackingTouch(SeekBar arg0) {}
			
		});
		
		bar=(SeekBar)findViewById(R.id.robotSpeedSeekBar);
		bar.setMax(maxCycles);
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int currentVal, boolean arg2) {
				Options.millisPerTick=maxCycles-currentVal;
			}

			public void onStartTrackingTouch(SeekBar arg0) {}
			public void onStopTrackingTouch(SeekBar arg0) {}
			
		});
	}
	
}
