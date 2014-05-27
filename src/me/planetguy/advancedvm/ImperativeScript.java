package me.planetguy.advancedvm;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import android.util.Log;



public class ImperativeScript extends Script implements Serializable, Scripted{

	boolean alive=true;
	
	int index=0;
	
	public ImperativeScript(String src, IBox[][] script, Map<String, IBox> c, Scripted owner) {
		super(src, script, c, owner);
	}

	public ImperativeScript(String src, IBox[][] script, Map<String, IBox> c) {
		super(src, script, c);
	}

	public void jump(int newIdx){
		this.index=newIdx;
	}

	public boolean canStep(){
		return index<script.length;
	}

	public void step() throws Exception{
		if(!alive)return;
		Log.d("Robots","Script executing");
		try{
			int oldIndex=index;
			script[index][1].set(((Method)script[index][0].get()).invoke(null, unboxEnd(script[index])));
			if(oldIndex==index)
				index++;
		}catch(Exception e){
			alive=false;
			System.err.println(this);
			e.printStackTrace();
			throw(e);
		}
	}

	private Object[] unboxEnd(IBox[] script){
		Log.d("Robots", Arrays.toString(script));
		Object[] objs=new Object[script.length-2+1];//args - func and return + script
		objs[0]=this.getOwningObject();
		for(int i=2; i<script.length; i++){
			objs[i-1]=script[i].get();
		}
		Log.d("Robots", Arrays.toString(objs));
		return objs;
	}

	public String toString(){
		String result="Script:\n";
		for(int i=0; i<script.length; i++){
			IBox[] boxes=script[i];
			result+=(i==index)? ">" : " ";
			result+="   { ";
			for(IBox box:boxes){
				result+=box+", ";
			}
			result+="} \n";
		}
		return result;
	}

	@Override
	public Script getScript() {
		return this;
	}

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		this.getNamedBox("this").set(this);
	}

	@Override
	public void print(Object o) {
		debugStream.write("S@"+this.index+":"+o);
	}
	
}
