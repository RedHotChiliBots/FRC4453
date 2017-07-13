package org.usfirst.frc.team4453.library.vision;

import java.util.HashMap;

/**
 * This class represents the data that is passed from step to step.
 * It stores Objects with associated names.
 * @author conner
 *
 */
public class Data {
	/**
	 * The Data
	 */
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	/**
	 * Puts the object in with the associated name.
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value)
	{
		data.put(key, value);
	}
	
	/**
	 * Gets an object by name.
	 * @param key The name.
	 * @return The object, or null if it does not exist.
	 */
	public Object get(String key)
	{
		return data.get(key);
	}
	
	/**
	 * Renames an object.
	 * @param key The old name.
	 * @param newkey The new name.
	 */
	public void remap(String key, String newkey)
	{
		data.put(newkey, data.get(key));
		data.remove(key);
	}
}
