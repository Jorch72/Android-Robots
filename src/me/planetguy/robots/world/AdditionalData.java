package me.planetguy.robots.world;

import java.io.Serializable;

public final class AdditionalData {
	
	public Serializable[] data;
	
	public AdditionalData(int size){
		data=new Serializable[size];
	}
	
	public void put(int idx, Serializable data){
		this.data[idx]=data;
	}
	
	public Serializable get(int idx){
		return data[idx];
	}
	
	public int size(){
		return data.length;
	}
}
