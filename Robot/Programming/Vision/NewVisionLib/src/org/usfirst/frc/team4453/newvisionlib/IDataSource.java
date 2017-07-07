package org.usfirst.frc.team4453.newvisionlib;

/**
 * Interface for a data source.
 * @author conner
 *
 */
public interface IDataSource {
	
	/**
	 * Gets a target by id.
	 * @param id ID of target.
	 * @return The target.
	 */
	public abstract Target getData(int id);
	
	/**
	 * Updates a target.
	 * @param data The data to update with.
	 * @param id ID of target.
	 * @return true if success.
	 */
	public abstract boolean putData(Target data, int id);
	
	/**
	 * Adds a new target.
	 * @param target Target to add.
	 * @return ID of new target.
	 */
	public abstract int addTarget(Target target);
	
	/**
	 * Removes a target by ID.
	 * NOTE: May invalidate other ids.
	 * @param id ID of target.
	 * @return true if success.
	 */
	public abstract boolean removeTarget(int id);
	
	/**
	 * Gets number of targets.
	 * @return Number of targets.
	 */
	public abstract int numOfTargets();
	
	/**
	 * Updates all data on remote sources.
	 * @return true if success.
	 */
	public abstract boolean push();
	
	/**
	 * Updates local data from remote sources.
	 * NOTE: May destroy local data.
	 * @return true if success.
	 */
	public abstract boolean pull();
	
	/**
	 * Gets value of a setting.
	 * @param setting Setting to get value of.
	 * @return Value of setting.
	 */
	public abstract String getSetting(String setting);
	
	/**
	 * Sets value of a setting.
	 * @param setting Setting to set value of.
	 * @return true if success.
	 */
	public abstract boolean setSetting(String setting, String value);
}
