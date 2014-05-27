package me.planetguy.advancedvm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;


/**
 * An IBox backed by a reflection-based field.
 */
public class FieldBox implements IBox{

	private static final long serialVersionUID = 3474887475989678178L;
	
	private transient Object target;
	private transient Field field;
	private Serializable so;
	private String objID;
	private String fieldName;
	private final boolean canWrite;
	
	public FieldBox(Object target, Field f, boolean canWrite){
		this.target=target;
		this.field=f;
		f.setAccessible(true);
		fieldName=field.getName();
		this.canWrite=canWrite;
	}
	
	@Override
	public void set(Object o) {
		if(!canWrite)
			return;
		try {
			field.set(target, o);
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object get() {
		try {
			return field.get(target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void writeObject(ObjectOutputStream oos)
			throws IOException {
		if(target instanceof Serializable&&!(target instanceof ImperativeScript)){
			so=(Serializable) target;
		}else{
			objID=Box.name(target);
		}
		
		oos.defaultWriteObject();
	}
	
	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		if(so==null){
			target=Box.fish(objID);
		}else{
			target=so;
		}
		try {
			field=target.getClass().getField(this.fieldName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return "<"+this.get()+">";
	}

}
