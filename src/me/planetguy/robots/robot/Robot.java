package me.planetguy.robots.robot;

import java.lang.reflect.Field;

import me.planetguy.advancedvm.ImperativeScript;
import me.planetguy.advancedvm.LimitedScript;
import me.planetguy.advancedvm.Script;
import me.planetguy.advancedvm.Scripted;
import me.planetguy.advancedvm.StandardLibrary;
import me.planetguy.advancedvm.Stream;
import me.planetguy.advancedvm.TransformerInlineConstants;
import me.planetguy.advancedvm.VMContext;
import me.planetguy.robots.R;
import me.planetguy.robots.Robots;
import me.planetguy.robots.dynamic.DynamicObject;
import me.planetguy.robots.ide.IDEActivity;
import me.planetguy.robots.misc.Options;
import me.planetguy.robots.misc.Side;
import me.planetguy.robots.tile.Tile;
import me.planetguy.robots.tile.TileUtil;
import me.planetguy.robots.world.World;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class Robot extends DynamicObject implements Scripted {

	public static VMContext contex;
	
	static{
		initContext(null);
		
	}
	
	public static void initContext(Robot r){
		contex=new VMContext();
		contex.registerClass(StandardLibrary.class);
		contex.setScriptClass(LimitedScript.class);
		contex.registerScriptTransformer(new TransformerInlineConstants());
		if(r!=null)
			try{
				Field posX=r.getClass().getField("x");
				Field posY=r.getClass().getField("y");
				Field north=r.getClass().getField("north");
				Field south=r.getClass().getField("south");
				Field east=r.getClass().getField("east");
				Field west=r.getClass().getField("west");
				contex.registerFieldBox("x", r, posX, false);
				contex.registerFieldBox("y", r, posY, false);
				contex.registerFieldBox("north", r, north, false);
				contex.registerFieldBox("south", r, south, false);
				contex.registerFieldBox("east", r, east, false);
				contex.registerFieldBox("west", r, west, false);
			}catch(Exception e){
				e.printStackTrace();
			}
		RobotScriptAPI.initActions(contex);
		Log.d("Robots", contex.toString());
	}
	
	public Context worldGui;
	
	public Side side;

	public String log="";

	public Script script;
	
	public BlockingState waitingOn=BlockingState.none;
	
	public int north, south, east, west;
	
	Bitmap icon;
	
	public Robot(World world, Side side, Context context) {
		super(world);
		this.worldGui=context;
		icon=BitmapFactory.decodeResource(context.getResources(), R.drawable.robot_red_north);
		try {
			setScript(
					"label top\n"+
					"randint x 1 4\n"+
					"* newLabel 2 x\n"+
					"+ newLabel 2 newLabel\n"+
					"jump _ newLabel\n"+
					"robot.move _ gosouth\n"+
					"jump _ top\n"+
					"robot.move _ gowest\n"+
					"jump _ top\n"+
					"robot.move _ goeast\n"+
					"jump _ top\n"+
					"robot.move _ gonorth\n"+
					"jump _ top"
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setScript(String source) throws Exception{
		script=contex.compile(source, this);
		script.debugStream=new Stream(){

			@Override
			public void write(Object o) {
				log(o.toString());
			}
			
		};
		((ImperativeScript)script).jump(0);
	}
	
	public void onUpdate(final View v){
		//Log.w("Robots",script.toString());
		try {
			for(int i=0; i<Options.ROBOT_TICKS_PER_UPDATE && waitingOn==BlockingState.none; i++){
				if(script.canStep())
					script.step();
			}
		} catch (Exception e) {
			log(e);
			e.printStackTrace();
			makeToast(v, "Broken script!");
		}
		waitingOn.execute(this, v);
		this.waitingOn=BlockingState.none;
		north=id(this.world.tiles[x][y-1]);
		east=id(this.world.tiles[x+1][y]);
		south=id(this.world.tiles[x][y+1]);
		west=id(this.world.tiles[x-1][y]);
	}
	
	public boolean onClicked(View v){
		Intent intent=new Intent(worldGui, IDEActivity.class);
		intent.putExtra("id",this.getDynamicObjectID());
		Log.d("Robots","Robot clicked!");
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		if(Robots.currentIDE!=null)
			Robots.currentIDE.robot=this;
		v.getContext().startActivity(intent);
		return true;
	}

	public void moveTo(int x, int y, View v){
		if(!world.tiles[x][y].isWalkable())
			return;
		Log.d("Robots","Moving to "+x+","+y);
		if(x<0||x>=world.tiles.length||y<0||y>=world.tiles[0].length)
			return;
		this.x=x;
		this.y=y;
		world.tiles[x][y].onObjectEnter(this, v);
	}
	
	public Bitmap getIcon(){
		return this.icon;
	}

	@Override
	public Script getScript() {
		return this.script;
	}

	@Override
	public void print(Object o) {
		Log.w("Robots",o.toString());
	}
	
	
	public String toString(){
	/*	String s="Robot {x="+x+", y="+y+", script=\n";
		s+=this.script.toString();
		s+="}";
		return s;*/
		return super.toString();
	}
	
	public int id(Tile t){
		if(t==TileUtil.tiles.get("ground"))
			return 0;
		else if(t==TileUtil.tiles.get("ore"))
			return 1;
		else
			return 2;
	}
	
	public String save(){
		return super.save()+script.source+";";
	}
	
	public void load(String s){
		super.load(s);
		String[] objects=s.split(";");
		String scriptSrc=objects[1];
		scriptSrc=scriptSrc.substring(0, scriptSrc.length()-1);
		try {
			setScript(scriptSrc);
		} catch (Exception e) {
			log(e);
		}
	}
	
	public void makeToast(final View v, final String message){
		v.post(new Runnable(){
			public void run(){
				Toast toast = Toast.makeText(v.getContext(), message, Toast.LENGTH_LONG);
				toast.show();
			}
		});
	}
	
	public void log(Exception e){
		log(e.getClass().getSimpleName()+": "+e.getMessage());
	}
	
	public void log(String s){
		log+=s+"\n";
	}
	
}
