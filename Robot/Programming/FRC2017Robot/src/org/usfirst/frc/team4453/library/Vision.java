package org.usfirst.frc.team4453.library;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {

	private static NetworkTable table;

	private static int connTest = 0;
	
	private static long lastSynced = 0;
	
	// conversion constants
	private static final double MPI = 2.54 / 100.0; // Meters per inch

	// Camera constants
	private static final double FOV = 62.2; // FOV in degrees (Axis = 37.4; HD3000 = 68.5 (according to docs, we measured 45.329); Raspberry Pi Camera module v2.1 = 62.2 )

	// physical target size
	private static final double BOILERw = 15 * MPI; // Boiler width in meters
	private static final double GEARw = 8.25 * MPI; // Distance between centers of gear strips in meters

//	private static final double BOILERh = 97 * MPI; // Boiler height from floor (OVERALL height, not height of strips)

	private static final double noValue = -99.; // Resolution Y in pixels

	private static final double GEARCAM_OFFSET = 57.07 * MPI;
	private static final double GEARCAM_ANGLE = 5.0;
	
	/**
	 * Called every so often to update Vision code, resets parameters for now.
	 */
	public static void update() {
		if (table == null) {
			System.out.println("Connecting to /Vision...");
			table = NetworkTable.getTable("/Vision");
			lastSynced = System.currentTimeMillis();
		}
		else {
			table.putNumber("param:gearWidth", GEARw);
			table.putNumber("param:boilerWidth", BOILERw);
			table.putNumber("param:FOV", FOV);

			table.putNumber("debug:connTestToRobotReturn", table.getNumber("debug:connTestToRobot", -1));
			table.putNumber("debug:connTestToVision", connTest);
			NetworkTable.flush();
			if(connTest == table.getNumber("debug:connTestToVisionReturn", -1))
			{
				lastSynced = System.currentTimeMillis();
				connTest++;
				if(connTest > 100)
				{
					connTest = 0;
				}
			}
		}
	}

	public static String getStatus()
	{
		if(System.currentTimeMillis() - lastSynced > 5000)
		{
			return "BADCONN";
		}
		else if(System.currentTimeMillis() - lastSynced > 10000)
		{
			return "NOCONN";
		}
		else if(!table.getString("param:getCamera", "").equals(table.getString("param:setCamera", "")))
		{
			return "BADCAMERA";
		}
		else
		{
			return "OK";
		}
	}
	
	public static String getStatusRemote()
	{
		if(System.currentTimeMillis() - lastSynced > 5000)
		{
			return "BADCONN";
		}
		else if(System.currentTimeMillis() - lastSynced > 10000)
		{
			return "NOCONN";
		}
		else
		{
			return table.getString("RPIStatus", "N/A");
		}
	}
	
	/**
	 * Get the pixel position of the gear peg.
	 * 
	 * @return Position of target: {X, Y}, or {-99, -99} if not found.
	 */
	public static double[] getGearPos() {
		double[] tgtPos = {
				table.getNumber("gear:X", noValue), table.getNumber("gear:Y", noValue)
		};
		return tgtPos;
	}

	/**
	 * Get the pixel position of the boiler.
	 * 
	 * @return Position of target: {X, Y}, or {-99, -99} if not found.
	 */
	public static double[] getBoilerPos() {
		double[] tgtPos = {
				table.getNumber("boiler:X", noValue), table.getNumber("boiler:Y", noValue)
		};
		return tgtPos;
	}

	/**
	 * Returns if the boiler is visible.
	 * 
	 * @return true if found, false otherwise.
	 */
	public static boolean boilerVisible() {
		return table.getBoolean("boiler:Found", false);
	}

	/**
	 * Returns if the gear peg is visible.
	 * 
	 * @return true if found, false otherwise.
	 */
	public static boolean gearVisible() {
		return table.getBoolean("gear:Found", false);
	}

	/**
	 * Gets the distance (hypotenuse) to the gear peg.
	 * 
	 * @return Distance, in meters, to the target.
	 */
	public static double getGearDistUnadjusted() {
		return table.getNumber("gear:Distance", noValue);
	}
	
	public static double getGearDistAdjusted()
	{
		double X = getGearDistUnadjusted() * Math.sin(Math.toRadians(getGearAngleOffsetUnadjusted() + GEARCAM_ANGLE));
		double Y = getGearDistUnadjusted() * Math.cos(Math.toRadians(getGearAngleOffsetUnadjusted() + GEARCAM_ANGLE));
		
		double Xa = X - GEARCAM_OFFSET;
		
		
		return Math.sqrt(Math.pow(Xa, 2) + Math.pow(Y, 2));
	}

	/**
	 * Gets the distance (hypotenuse) to the boiler.
	 * 
	 * @return Distance, in meters, to the target.
	 */
	public static double getBoilerDist() {
		return table.getNumber("boiler:Distance", noValue);
	}

	/**
	 * Gets the angle of the gear peg from the center of the view.
	 * 
	 * @return Angle, in degrees, of the offset.;
	 */
	public static double getGearAngleOffsetUnadjusted() {
		return table.getNumber("gear:angleOffset", noValue);
	}
	
	public static double getGearAngleOffsetAdjusted() {
		double X = getGearDistUnadjusted() * Math.sin(Math.toRadians(getGearAngleOffsetUnadjusted() + GEARCAM_ANGLE));
		double Y = getGearDistUnadjusted() * Math.cos(Math.toRadians(getGearAngleOffsetUnadjusted() + GEARCAM_ANGLE));
		
		double Xa = X - GEARCAM_OFFSET;
		
		return Math.toDegrees(Math.atan2(Xa, Y));
	}

	/**
	 * Gets the angle of the boiler from the center of the view.
	 * 
	 * @return Angle, in degrees, of the offset.;
	 */
	public static double getBoilerAngleOffset() {
		return table.getNumber("boiler:angleOffset", noValue);
	}

	public static double getMPI() {
		return MPI;
	}

	public static double getFOV() {
		return FOV;
	}

	/**
	 * Sets the current camera to be used. Should ideally be called before every
	 * use of vision.
	 * 
	 * @param name
	 *            The name of the camera (currently "boiler" or "gear")
	 * @return True, if the switch succeeded.
	 */
	public static boolean setCamera(String name, double timeout) {
		table.putString("param:setCamera", name);
		NetworkTable.flush();
		long startTime = System.nanoTime();
		while (!name.equals(table.getString("param:getCamera", ""))) {
			if (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime) > timeout) {
				return false;
			}
		}
		return true;
	}
	
	public static String getCurrentCamera()
	{
		return table.getString("param:getCamera", "N/A");
	}
}
