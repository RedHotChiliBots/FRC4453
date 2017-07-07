package org.usfirst.frc.team4453.library;

import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team4453.newvisionlib.Color;
import org.usfirst.frc.team4453.newvisionlib.ColorTargetPoint;
import org.usfirst.frc.team4453.newvisionlib.NetworkTablesDataSource;
import org.usfirst.frc.team4453.newvisionlib.Point2d;
import org.usfirst.frc.team4453.newvisionlib.Point3d;
import org.usfirst.frc.team4453.newvisionlib.Target;
import org.usfirst.frc.team4453.newvisionlib.robotvision.RobotVision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {

	// conversion constants
	private static final double MPI = 2.54/100.0;	// Meters per inch

	// Camera (Microsoft HD 3000) constants
	private static final double FOV  = 59.0;	// FOV in degrees (Axis = 37.4; HD3000 = 68.5 (according to docs, we measured 45.329) )
	private static final double FOVx = 640.0;	// FOV X in pixels
	private static final double FOVy = 480.0;	// FOV Y in pixels

	// physical target size
	private static final double TGTx = 29.0;	// Target width in meters
	private static final double TGTy = 19.0;	// Target height in meters

	//private static final double CAMh = 13.5*MPI;	// camera lens height from floor
	private static final double CAMh = 45.75;
	//private static final double TWRh = 91.5;	// target center height from floor

	// equation constants
	private static final double tanFOV = 2*Math.tan(Math.toRadians(FOV/2.0));
	//private static final double TGTh = TWRh - CAMh;  //target height above camera

	private static RobotVision rVision = new RobotVision();
	private static Color colorTolerance = new Color(10, 10, 10);
	
	private static Color redColor = new Color(0, 0, 0);
	private static Color blueColor = new Color(0, 0, 0);
	private static Color greenColor = new Color(0, 0, 0);
	private static Color orangeColor = new Color(0, 0, 0);
	
	private static boolean isInited = false;
	
	public static boolean isInited() {
		return isInited;
	}

	public Vision() {
	}
	
	public static boolean init()
	{
		rVision.setDataSource(new NetworkTablesDataSource(NetworkTable.getTable("/Vision")));
		Target theTarget = Target.makeCOLOR_DOT();
		
		theTarget.addPoint(ColorTargetPoint.makeForCOLOR_DOT("RedDot", redColor, colorTolerance, new Point3d(0, 0, 0)));
		theTarget.addPoint(ColorTargetPoint.makeForCOLOR_DOT("BlueDot", blueColor, colorTolerance, new Point3d(TGTx, 0, 0)));
		theTarget.addPoint(ColorTargetPoint.makeForCOLOR_DOT("GreenDot", greenColor, colorTolerance, new Point3d(0, TGTy, 0)));
		theTarget.addPoint(ColorTargetPoint.makeForCOLOR_DOT("OrangeDot", orangeColor, colorTolerance, new Point3d(TGTx, TGTy, 0)));
		rVision.addTarget(theTarget, "TheTarget");
		rVision.setSetting("calibPatternSizeX", "4.0");
		rVision.setSetting("calibPatternSizeY", "4.0");
		rVision.setSetting("calibSquareSize", "0.75");
		rVision.setSetting("cameraUrl", "http://localhost:1180/?action=stream&dummy=param.mjpg");
		rVision.setReady();
		
		long startTime = System.nanoTime();
		while(!rVision.getVisionStatus().equals("RUNNING"))
		{
			if(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime) > 20)
			{
				isInited = false;
				return false;
			}
		}
		isInited = true;
		return true;
	}
	
	private static Target getTarget(String tgt, boolean withData)
	{
		if(withData)
		{
			return rVision.getTargetData(tgt);
		}
		return rVision.getTarget(tgt);
	}
	
	/**
	 * getTargetPosition - finds the 3d center of a target, by averaging the points together.
	 * @param tgt The target.
	 * @return The center.
	 */
	public static Point3d getTargetPosition(Target tgt)
	{
		if(isInited() == false)
		{
			return new Point3d(-99, -99, -99);
		}
		Point3d center = new Point3d(0, 0, 0);
		for(int i = 0; i < tgt.colorPoints.size(); i++)
		{
			center.X += tgt.colorPoints.get(i).tgtCoord.X;
			center.Y += tgt.colorPoints.get(i).tgtCoord.Y;
			center.Z += tgt.colorPoints.get(i).tgtCoord.Z;
		}
		center.X /= tgt.colorPoints.size();
		center.Y /= tgt.colorPoints.size();
		center.Z /= tgt.colorPoints.size();
		return center;
	}
	
	public static Point3d getTargetPosition(String target)
	{
		return getTargetPosition(getTarget(target, true));
	}
	
	
	public static Point2d getTargetImgPosition(Target tgt)
	{
		if(isInited() == false)
		{
			return new Point2d(-99, -99);
		}
		Point2d center = new Point2d(0, 0);
		for(int i = 0; i < tgt.colorPoints.size(); i++)
		{
			center.X += tgt.colorPoints.get(i).imgCoord.X;
			center.Y += tgt.colorPoints.get(i).imgCoord.Y;
		}
		center.X /= tgt.colorPoints.size();
		center.Y /= tgt.colorPoints.size();
		return center;
	}
	
	public static Point2d getTargetImgPosition(String target)
	{
		return getTargetImgPosition(getTarget(target, true));
	}
	/**
	 * calcTgtDist - Finds total distance to target.
	 *  
	 * @return array of X,Y distance in meters
	 */
	public static double calcTgtDist(Target tgt) {
		if(isInited() == false)
		{
			return -99;
		}
		Point3d center = getTargetPosition(tgt);
		return Math.sqrt(Math.pow(center.X, 2) + Math.pow(center.Y, 2) + Math.pow(center.Z, 2));		
	}
	
	public static double calcTgtDist(String tgt) {
		return calcTgtDist(getTarget(tgt, true));
	}
	
	public static String getStatus()
	{
		return rVision.getVisionStatus();
	}

	public static double getMPI() {
		return MPI;
	}

	public static double getFOV() {
		return FOV;
	}

	public static double getFOVx() {
		return FOVx;
	}

	public static double getFOVy() {
		return FOVy;
	}

	public static double getTGTx() {
		return TGTx;
	}

	public static double getTGTy() {
		return TGTy;
	}

	public static double getCAMh() {
		return CAMh;
	}

	public static double getTanFOV() {
		return tanFOV;
	}

	/**
	public static double getTGTh() {
		return TGTh;
	}
	**/
}
