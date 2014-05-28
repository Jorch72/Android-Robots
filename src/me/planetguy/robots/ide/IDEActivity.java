package me.planetguy.robots.ide;

import me.planetguy.robots.R;
import me.planetguy.robots.Robots;
import me.planetguy.robots.dynamic.DynamicObject;
import me.planetguy.robots.renderworld.ActivityWorld;
import me.planetguy.robots.robot.Robot;
import me.planetguy.robots.world.gen.ActivityWgenChooser;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class IDEActivity extends Activity{
	
	public Robot robot;
	
	public static int robotID;
	
	public static final String 
	code="Code",
	log="Log",
	memory="Memory",
	docs="Manual";
	
	
	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		Intent intent=getIntent();
		setContentView(R.layout.activity_ide);
		Log.d("Robots","DO reg="+DynamicObject.objectRegistry+", bundle="+state);
		robotID=intent.getIntExtra("id", -1);
		robot=(Robot) DynamicObject.objectRegistry.get(robotID);
		Robots.currentIDE=this;
		EditText window=(EditText) findViewById(R.id.ideWindow);
		window.setMovementMethod(new ScrollingMovementMethod());
		selectCode(null);
	}
	
	public void onBackPressed(){
		offerUploadScript();
		Intent intent=new Intent(this, ActivityWorld.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(intent);
	}
	
	private void uploadScript(){
		EditText scriptWindow=(EditText) findViewById(R.id.ideWindow);
		try {
			robot.setScript(scriptWindow.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void offerUploadScript(){
		String title=((TextView)findViewById(R.id.titleWindow)).getText().toString();
		if(title.equals(code)){
			uploadScript();
		}
	}
	
	public void selectCode(View v){
		offerUploadScript();
		chooseButton(R.id.buttonCode);
		loadWindow(code,robot.getScript().source);
		setKeyboardVisibility(true);
		findViewById(R.id.ideWindow).requestFocus();
	}
	
	public void selectLog(View v){
		offerUploadScript();
		chooseButton(R.id.buttonLog);
		loadWindow(log,robot.log);
		setKeyboardVisibility(false);
	}
	
	public void selectMemory(View v){
		offerUploadScript();
		chooseButton(R.id.buttonMemory);
		loadWindow(memory,robot.getScript().dumpSymbolTable());
		setKeyboardVisibility(false);
	}
	
	public void selectDocs(View v){
		offerUploadScript();
		chooseButton(R.id.buttonDocs);
		loadWindow(docs,getString(R.string.manual));
		setKeyboardVisibility(false);
	}
	
	public void exitToWgen(View v){
		Intent intent=new Intent(this.getApplicationContext(), ActivityWgenChooser.class);
		startActivity(intent);
	}
	
	public void onStop(){
		super.onStop();
		offerUploadScript();
	}
	
	public void loadWindow(String title, String box){
		EditText window=(EditText) findViewById(R.id.ideWindow);
		window.setText(box);
		TextView titleBar=(TextView)findViewById(R.id.titleWindow);
		titleBar.setText(title);
	}
	
	public void chooseButton(int buttonId){
		for(int i:new int[]{R.id.buttonCode, R.id.buttonMemory, R.id.buttonLog, R.id.buttonDocs}){
			ToggleButton button=(ToggleButton)(findViewById(i));
			button.setChecked(false);
		}
		((ToggleButton)findViewById(buttonId)).setChecked(true);
	}
	
	public void setKeyboardVisibility(boolean b){
		if(b)
			((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
			.showSoftInput(findViewById(R.id.ideWindow), InputMethodManager.SHOW_IMPLICIT);
		else
			((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
			.hideSoftInputFromWindow(findViewById(R.id.ideWindow).getWindowToken(), 0);
	}
}
