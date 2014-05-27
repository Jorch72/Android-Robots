package me.planetguy.advancedvm;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * A Context represents the set of methods a script can have.
 *
 */
public class VMContext {

	/**
	 * The root context. It should have the standard library functions available to all contexts before any other contexts are created.
	 * 
	 * Most contexts should probably be constructed from it, directly or indirectly, since it contains the basic operations of the language.
	 */
	public static VMContext root;

	static{
		root=new VMContext();
		root.registerClass(StandardLibrary.class);
		root.setScriptClass(ImperativeScript.class);
		root.registerScriptTransformer(new TransformerInlineConstants());
	}

	private Map<String, Method> methodMap=new HashMap<String, Method>();

	private Map<String, Inline> inlines=new HashMap<String, Inline>();

	private ArrayList<ScriptTransformer> transformers=new ArrayList<ScriptTransformer>();

	private Map<String, IBox> literals=new HashMap<String, IBox>();

	private Class<? extends Script> scriptClass;

	public VMContext(){}

	public VMContext(VMContext c){ //Construct from other context, it copies over all mappings.
		this.methodMap=new HashMap<String, Method>(c.methodMap);
		this.inlines=new HashMap<String, Inline>(c.inlines);
		this.literals=new HashMap<String, IBox>(c.literals);
		this.transformers=new ArrayList<ScriptTransformer>(c.transformers);
		this.scriptClass=c.scriptClass;
	}

	public Script compile(String source, Scripted owner) throws Exception{
		Map<String, IBox> boxMap=new HashMap<String, IBox>(this.literals);
		for(String methodName:methodMap.keySet()){
			boxMap.put(methodName, new ImmutableBox(methodMap.get(methodName)));
		}
		ArrayList<IBox[]> lines=new ArrayList<IBox[]>();
		String[] linesArr=source.split("\n");
		for(ScriptTransformer t:transformers){
			linesArr=t.transform(linesArr);
		}
		LinkedList<String> script=new LinkedList<String>();
		script.addAll(Arrays.asList(linesArr));
		boxMap.put("", NullBox.instance);
		int functionCallNumber=0;
		while(!script.isEmpty()){

			
			String line=script.getFirst();
			script.removeFirst();

			System.out.println("Compiling "+line);

			String[] stmts=line.split(" ");
			IBox[] boxes=new IBox[stmts.length];

			if(stmts.length==1||stmts.length==0)
				continue;

			boxes[0]=box("script@"+functionCallNumber, boxMap);
			String statement=stmts[0];
			boxes[1]=boxUsedVar(stmts[1], boxMap);

			Inline inline=this.inlines.get(statement);
			if(inline!=null){
				List<String> newLines=inline.replace(stmts);
				for(int i=newLines.size()-1; i>=0; i--){
					script.addFirst(newLines.get(i));
				}
				continue;
			}
			Method m=methodMap.get(statement);
			if(m!=null){
				boxes[0].set(m);
				for(int j=2; j<stmts.length; j++){
					boxes[j]=boxUsedVar(stmts[j], boxMap);
				}
				functionCallNumber++;
				lines.add(boxes);
			}else if(statement.equals("lit")){
				boxes[1].set(parseLit(stmts));
			}else if(statement.equals("label")){
				boxes[1].set(functionCallNumber);
			}
		}
		if(owner!=null){
			Script scr=scriptClass.getConstructor(String.class, IBox[][].class, Map.class, Scripted.class)
					.newInstance(source, lines.toArray(new IBox[][]{{}}), boxMap, owner);
			return scr;
		}else{
			Script scr=scriptClass.getConstructor(String.class, IBox[][].class, Map.class)
					.newInstance(source, lines.toArray(new IBox[][]{{}}), boxMap);
			return scr;
		}
	}

	private IBox box(String varName, Map<String, IBox> boxRegistry ){
		if(boxRegistry.containsKey(varName)){
			return boxRegistry.get(varName);
		}else{
			IBox box=new Box();
			boxRegistry.put(varName, box);
			return box;
		}
	}
	
	private IBox boxUsedVar(String varName, Map<String, IBox> boxRegistry ){
		if(boxRegistry.containsKey(varName)){
			return boxRegistry.get(varName);
		}else{
			IBox box=new ScriptCreatedBox();
			boxRegistry.put(varName, box);
			return box;
		}
	}

	private Object parseLit(String[] stmts){
		if(stmts.length==3){
			return parseSingleLit(stmts[2]);
		}else{
			Object[] lits=new Object[stmts.length-2];
			for(int i=0; i<lits.length; i++){
				lits[i]=parseSingleLit(stmts[i+2]);
			}
			return lits;
		}
	}

	public static Object parseSingleLit(String lit){
		Object returnVal;
		try{
			returnVal=Double.parseDouble(lit);
		}catch(NumberFormatException e){
			if(lit.equals("true")){
				returnVal=Boolean.valueOf(true);
			}else if(lit.equals("false")){
				returnVal=Boolean.valueOf(false);
			}else{
				returnVal=lit;
			}
		}
		return returnVal;
	}

	/*==================
	 * Begin registry methods - these change the Context
	 * =================
	 */

	public void setScriptClass(Class<? extends Script> class1){
		this.scriptClass=class1;
	}

	public void registerMethod(String name, Method m){
		methodMap.put(name, m);
		Box.register(m, "method."+name);
	}

	public void registerClass(Class<?> c){
		registerClass(c, "");
	}

	public void registerClass(Class<?> c, String namespace){
		if(!namespace.equals("")){
			namespace=namespace+"."; //Add period to namespace if it isn't blank
		}
		for(Method m:c.getDeclaredMethods()){
			if(m.getAnnotation(ScriptAPI.class)!=null){
				if(Modifier.isStatic(m.getModifiers())){
					ScriptAPI sapi=m.getAnnotation(ScriptAPI.class);
					if(!sapi.value().equals("__NULL__")){//If has a special name, register it
						registerMethod(namespace+sapi.value(),m);
					}
					registerMethod(namespace+m.getName(), m); //Register under plain name too
				}
			}
		}
	}


	public void registerInline(Inline i){
		registerInline(i, i.name());
	}

	public void registerInline(Inline i, String detect){
		this.inlines.put(detect, i);
	}

	public void registerLiteral(String name, IBox b){
		this.literals.put(name, b);
	}

	public void registerLitObj(String name, Object o){
		registerLiteral(name, new Box(o));
	}

	public void registerFinalObj(String name, Object o){
		registerLiteral(name, new ImmutableBox(o));
	}

	/**
	 * Registers a FieldBox literal, which is backed by a reflection-based field.
	 */
	public void registerFieldBox(String name, Object o, Field f, boolean write){
		registerLiteral(name, new FieldBox(o, f, write));
	}

	public void registerScriptTransformer(ScriptTransformer trans){
		this.transformers.add(trans);
	}

}
