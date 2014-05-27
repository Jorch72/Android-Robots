package me.planetguy.advancedvm;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;


public abstract class Script implements Scripted, Serializable{
	
	private static Random r=new Random();
	
	static final long serialVersionUID = -8554584858663984873L;

	long scriptID=r.nextLong();

	IBox[][] script;

	Map<String, IBox> boxMap;

	public final String source;

	private Scripted owningObject;

	public Stream debugStream=new Stream(){
		@Override
		public void write(Object o) {
			System.out.println(o);
		}
	};
	
	public Script(String source, IBox[][] script, Map<String, IBox> c, Scripted owningObject){
		this.script=script;
		this.boxMap=c;
		Box.register(this, "script<"+scriptID+">");
		this.source=source;
		this.owningObject=owningObject;
	}
	
	public Script(String src, IBox[][] script2, Map<String, IBox> c) {
		this(src,script2,c, null);
		this.owningObject=this;
	}

	public Script getScript(){
		return this;
	}
	
	public abstract boolean canStep();

	public abstract void step() throws Exception;
	
	public IBox getNamedBox(String name){
		return boxMap.get(name);
	}
	
	public String dumpSymbolTable(){
		String result="";
		for(String s:boxMap.keySet()){
			if(!(boxMap.get(s).get() instanceof Method)&& !(boxMap.get(s).get() instanceof Enum))
				result+=s+":   "+boxMap.get(s)+"\n";
		}
		return result;
	}

	public Scripted getOwningObject(){
		return owningObject;
	}
	
}
