package org.usfirst.frc.team4453.visionlib;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Remake of the old datasource used in old vision.
 * @author conner
 *
 */
public class NetworkTablesDataSource implements IDataSource {

	private NetworkTable table;
	
	public NetworkTablesDataSource(NetworkTable tbl)
	{
		table = tbl;
	}
	@Override
	public boolean putData(Target data) {
		table.putNumber("centerX", data.centerX);
		table.putNumber("centerY", data.centerY);
		table.putNumber("height", data.height);
		table.putNumber("width", data.width);
		table.putNumber("area", data.height * data.width);
		
		return table.isConnected();
	}
	@Override
	public Target getData() {
		Target ret = new Target();
		ret.centerX = table.getNumber("centerX", -99);
		ret.centerY = table.getNumber("centerY", -99);
		ret.height = table.getNumber("height", -99);
		ret.width = table.getNumber("width", -99);
		return null;
	}

}
