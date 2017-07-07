package org.usfirst.frc.team4453.library;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionOld {

	private static NetworkTable table;

	// conversion constants
	private static final double MPI = 2.54/100.0;	// Meters per inch

	// Camera (Microsoft HD 3000) constants
	private static final double FOV  = 59.0;	// FOV in degrees (Axis = 37.4; HD3000 = 68.5 (according to docs, we measured 45.329) )
	private static final double FOVx = 640.0;	// FOV X in pixels
	private static final double FOVy = 480.0;	// FOV Y in pixels

	// physical target size
	private static final double TGTx = 20.0*MPI;	// Target width in meters
	private static final double TGTy = 14.0*MPI;	// Target height in meters

	//private static final double CAMh = 13.5*MPI;	// camera lens height from floor
	private static final double CAMh = 45.75*MPI;
	private static final double TWRh = 91.5*MPI;	// target center height from floor

	// equation constants
	private static final double tanFOV = 2*Math.tan(Math.toRadians(FOV/2.0));
	private static final double TGTh = TWRh - CAMh;  //target height above camera
	
	private static final double noValue = -99.0;
	
	private static double[] defaultValue = new double[1];
	private static double area = noValue;
	private static double height = noValue;
	private static double width = noValue;
	private static double centerX = noValue;
	private static double centerY = noValue;
	private static double ratio;
	private static double angle;
	private static double twrDist;
	private static double[] tgtDist = new double[2];
//	Set<String> keys;

	


	public VisionOld() {
	}
	
	public static void update() {

        if (table == null) {
        	System.out.println("Connecting to GRIP/Stronghold");
        	table = NetworkTable.getTable("/GRIP/Stronghold");
        	defaultValue[0] = noValue;
        }
        
//		int areaslength = table.getNumberArray("area", defaultValue).length;
//		int heightslength = table.getNumberArray("height", defaultValue).length;
//		int widthslength = table.getNumberArray("width", defaultValue).length;
//		int centerXslength = table.getNumberArray("centerX", defaultValue).length;
//		int centerYslength = table.getNumberArray("centerY", defaultValue).length;
        
        
		area = table.getNumber("area", noValue);
		height = table.getNumber("height", noValue);
		width = table.getNumber("width", noValue);
		centerX = table.getNumber("centerX", noValue);
		centerY = table.getNumber("centerY", noValue);
		findBestTarget();
		calcRatio();
		calcAngle();
		calcTgtDist();
		calcTwrDist();
	}
	
	/**
	 * findBestTarget - Based on the width array, this
	 * routine uses the array size to identify the number 
	 * of targets found and then chooses the largest target.
	 * <p>
	 * The largest target, based on width, is assumed to be 
	 * the closest and most aligned to the robot.
	 * 
	 * @return index of best target
	 */
	public static int findBestTarget() {
		return 0;
	}
	
	/**
	 * calcTgtDist - based on the pixel size of the selected target 
	 * and the known size of the target, this routine calculates
	 * the distance to the target on the horizontal (X) and vertical (y)
	 * planes.
	 *  
	 * @return array of X,Y distance in meters
	 */
	public static double[] calcTgtDist() {
		double tgtX = TGTx;	// * Math.cos(heading);
		double tgtY = TGTy;	// * Math.cos(pitch);
		
		double tgtWidth = getTgtWidth();
		double tgtHeight = getTgtHeight();
		
		double distX = (tgtWidth == -99.0 ? 0.0 : (tgtX * FOVx) / (tgtWidth * tanFOV));
		double distY = (tgtHeight == -99.0 ? 0.0 : (tgtY * FOVy) / (tgtHeight * tanFOV));
		tgtDist[0] = distX;
		tgtDist[1] = distY;
		
		//		distance = (getTgtWidth()/2.0) / Math.tan(Math.toRadians(getAngle()/2.0));
		return tgtDist;		
	}
	
	/**
	 * calcTwrDist - using the X,Y distance from caltTgtDist
	 * this routine calculates the point to point distance from
	 * the robot to the target.  This is the hypotenuse.
	 * 
	 * @return point to point distance in meters
	 */
	public static double calcTwrDist() {
		double[] d = calcTgtDist();
		twrDist = Math.sqrt(Math.pow(d[0],2) + Math.pow(TGTh,2));
		return twrDist;
	}

	public static double calcAngle() {
		angle = (FOV * getTgtWidth()) / FOVx;
		return angle;
	}
	
	public static double calcRatio() {
		ratio = 0.0;
		try {
			ratio = getTgtHeight() / getTgtWidth();
		} catch (ArithmeticException e) {
			System.out.println("Division by zero.");
		}
		
		return ratio;
	}
	
	/**
	 * getTgtyHeight - returns the target pixel size
	 * from the camera
	 * 
	 * @return - returns the target height in pixels
	 */
	public static double getTgtHeight() {
		return height;
	}
	
	/**
	 * getTgtyWidth - returns the target pixel size 
	 * from the camera
	 * 
	 * @return - returns the target width in pixels
	 */
	public static double getTgtWidth() {
		return width;
	}
	
	/**
	 * getTgtPos - returns the target position in x,y
	 * coordinates from the camera
	 * 
	 * @return - returns the target x,y position in pixels
	 */
	public static double[] getTgtPos() {
		double x = centerX;
		double y = centerY;
		double[] tgtPos = {x,y};
		return tgtPos;
	}

	public static double[] getAimPos() {
		double[] tgtPos = getTgtPos();
		double x = (tgtPos[0] - (FOVx/2)) / (FOVx/2);
		double y = -(tgtPos[1] - (FOVy/2)) / (FOVy/2);
		double[] aimPos = {x,y};
		return aimPos;
	}

	public static double getTgtArea() {
		return area;
	}
	
	public static double getRatio() {
		return ratio;
	}
	
	public static double getAngle() {
		return angle;
	}
	
	public static double[] getTgtDist() {
		return tgtDist;
	}
	
	public static double getTwrDist() {
		return twrDist;
	}

	public static double getTWRh() {
		return TWRh;
	}
	
	public static void setHeight(double h) {
		height = h;
	}
	
	public static void setWidth(double w) {
		width = w;
	}
	
	public static void setCenterX(double x) {
		centerX = x;
	}
	
	public static void setCenterY(double y) {
		centerY = y;
	}
	
	public static void setArea(double a) {
		area = a;
	}
	
	public static void setRatio(double r) {
		ratio = r;
	}
	
	public static void setAngle(double a) {
		angle = a;
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


	public static double getTGTh() {
		return TGTh;
	}
}
