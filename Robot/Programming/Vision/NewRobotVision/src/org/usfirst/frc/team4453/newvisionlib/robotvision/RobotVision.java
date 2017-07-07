package org.usfirst.frc.team4453.newvisionlib.robotvision;

import java.util.HashMap;

import org.usfirst.frc.team4453.newvisionlib.IDataSource;
import org.usfirst.frc.team4453.newvisionlib.Target;

/**
 * Small class to interface between robot code and vision code.
 * @author conner
 *
 */
public class RobotVision {
	private IDataSource dataSource;
	private HashMap< String, Integer > nameToId = new HashMap <String, Integer>();
	
	/**
	 * Adds a target.
	 * @param tg The target to add.
	 * @param name Name of target.
	 */
	public void addTarget(Target tg, String name)
	{
		int id = dataSource.addTarget(tg);
		dataSource.push();
		nameToId.put(name, id);
		return;
	}
	
	/**
	 * Removes a target.
	 * @param name Name of target.
	 */
	public void removeTarget(String name)
	{
		int id = nameToId.get(name);
		dataSource.removeTarget(id);
	}
	
	/**
	 * Gets a target.
	 * @param name Name of target.
	 * @return The target.
	 */
	public Target getTarget(String name)
	{
		int id = nameToId.get(name);
		Target data = dataSource.getData(id);
		return data;
	}
	
	/**
	 * Gets a target, failing if it has no data.
	 * @param name Name of target.
	 * @return The target, or null if failure.
	 */
	public Target getTargetData(String name)
	{
		int id = nameToId.get(name);
		dataSource.pull();
		Target data = dataSource.getData(id);
		return data;
	}
	
	/**
	 * Gets status of vision software.
	 * @return String representing the status,
	 */
	public String getVisionStatus()
	{
		return getSetting("STATUS");
	}
	
	/**
	 * Notifies the vision software to start running.
	 */
	public void setReady()
	{
		setSetting("robotStatus", "READY");
	}
	
	/**
	 * Sets a setting.
	 * @param setting Setting to set.
	 * @param value Value to set.
	 */
	public void setSetting(String setting, String value)
	{
		dataSource.setSetting(setting, value);
		dataSource.push();
	}
	
	/**
	 * Gets a setting.
	 * @param setting Setting to get.
	 * @return Value of setting.
	 */
	public String getSetting(String setting)
	{
		dataSource.pull();
		return dataSource.getSetting(setting);
	}
	
	/**
	 * Sets the data source to be used.
	 * @param ds The data source.
	 */
	public void setDataSource(IDataSource ds)
	{
		dataSource = ds;
	}
	
}
