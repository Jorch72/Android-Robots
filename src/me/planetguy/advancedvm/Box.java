package me.planetguy.advancedvm;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



public class Box implements Serializable, IBox{
	
	private static final long serialVersionUID = -8410494493928878550L;

	private static Map<Object, String> names=new HashMap<Object, String>();
	private static Map<String, Object> invNames=new HashMap<String, Object>();
	
	public static void register(Object key, String name){
		names.put(key, name);
		invNames.put(name, key);
	}
	
	public static Object fish(String name){
		return invNames.get(name);
	}
	
	public static String name(Object o){
		return names.get(o);
	}
	
	
	
	private transient Object o;
	private String objID;
	
	private Serializable so;
	
	public Box(){}
	
	public Box(Object o){
		this.o=o;
	}
	
	public void set(Object o){
		this.o=o;
	}
	
	public Object get(){
		return o;
	}
	
	public String toString(){
		if(this.get() instanceof ImperativeScript) //Avoid infinite recursion by printing script ID
			return "Box["+Box.name(this.get())+"]";
		else
			return "Box["+this.get()+"]";
	}
	
	private void writeObject(ObjectOutputStream oos)
			throws IOException {
		if(o instanceof Serializable&&!(o instanceof ImperativeScript)){
			so=(Serializable) o;
		}else{
			objID=name(o);
		}
		oos.defaultWriteObject();
	}
	
	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		if(so==null){
			o=fish(objID);
		}else{
			o=so;
		}
	}
	
}
