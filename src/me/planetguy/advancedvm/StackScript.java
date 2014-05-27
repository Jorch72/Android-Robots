package me.planetguy.advancedvm;

import android.annotation.TargetApi;
import android.os.Build;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class StackScript extends Script implements Scripted{

	private ArrayDeque<IBox> prog=new ArrayDeque<IBox>();

	private Stream output;

	public StackScript(String src, IBox[][] script, Map<String, IBox> boxMap, Scripted owner) {
		super(src, script, boxMap, owner);
		for(IBox[] boxes:script){
			for(IBox box:boxes){
				prog.add(box);
			}
		}
		output=(Stream) boxMap.get("out");
	}

	@Override
	public void print(Object o) {

	}

	@Override
	public boolean canStep() {
		return !prog.isEmpty();
	}

	@Override
	public void step() throws Exception {
		System.out.println(prog);
		Object top=prog.pop().get();
		if(top instanceof Method){
			int args=((Method)top).getParameterTypes().length;
			Object[] argObjs=new Object[args];
			for(int i=0; i<argObjs.length; i++){
				while(argObjs[i] == null)
					argObjs[i]=prog.pop().get();
			}
			System.out.println(Arrays.toString(argObjs));
			IBox o=new ImmutableBox(((Method) top)
					.invoke(null, argObjs));
			if(o!=null)
				prog.addLast(o);
		}else{
			output.write(top);
		}
	}

	public void setOutput(Stream str){
		this.output=str;
	}

}
