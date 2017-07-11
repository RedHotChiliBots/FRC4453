package org.usfirst.frc.team4453.library.vision;

import java.io.Serializable;
import java.util.HashMap;

public class Data implements Serializable {
	private static final long serialVersionUID = 49841664517034587L;
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	public void put(String key, Object value)
	{
		data.put(key, value);
	}
	
	public Object get(String key)
	{
		return data.get(key);
	}
	
	public void remap(String key, String newkey)
	{
		data.put(newkey, data.get(key));
		data.remove(key);
	}
}
