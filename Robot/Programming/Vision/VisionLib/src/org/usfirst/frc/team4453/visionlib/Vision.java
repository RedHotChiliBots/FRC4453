package org.usfirst.frc.team4453.visionlib;

public class Vision {

	// conversion constants
	private static final double MPI = 2.54/100.0;	// Meters per inch

	// Settings
	VisionSettings visionSettings;
		
	// Vision variables
	private Target data;
	private double ratio;
	private double angle;
	private double tgtDist;
	private double[] axialTgtDist = new double[2];
//	Set<String> keys;

	public Vision(VisionSettings settings) {
		visionSettings = settings;
		data = new Target();
	}
	
	public void update() {

        Target newDat = visionSettings.dataSource.getData();
        
        if(newDat.valid)
        {
        	data = newDat;
        }
        
		calcRatio();
		calcAngle();
		calcAxialTgtDist();
		calcTgtDist();
	}
	
	/**
	 * calcAxialTgtDist - based on the pixel size of the selected target 
	 * and the known size of the target, this routine calculates
	 * the distance to the target on the horizontal (X) and vertical (y)
	 * planes.
	 *  
	 * @return array of X,Y distance in meters
	 */
	public double[] calcAxialTgtDist() {
		double tgtX = visionSettings.TGTx;	// * Math.cos(heading);
		double tgtY = visionSettings.TGTy;	// * Math.cos(pitch);
		
		double tgtWidth = getTgtWidth();
		double tgtHeight = getTgtHeight();
		
		double distX = (tgtWidth == -99.0 ? 0.0 : (tgtX * visionSettings.FOVx) / (tgtWidth * visionSettings.tanFOV()));
		double distY = (tgtHeight == -99.0 ? 0.0 : (tgtY * visionSettings.FOVy) / (tgtHeight * visionSettings.tanFOV()));
		axialTgtDist[0] = distX;
		axialTgtDist[1] = distY;
		
		//		distance = (getTgtWidth()/2.0) / Math.tan(Math.toRadians(getAngle()/2.0));
		return axialTgtDist;		
	}
	
	/**
	 * calcTgtDist - using the X,Y distance from caltAxialTgtDist
	 * this routine calculates the point to point distance from
	 * the robot to the target.  This is the hypotenuse.
	 * 
	 * @return point to point distance in meters
	 */
	public double calcTgtDist() {
		double[] d = calcAxialTgtDist();
		tgtDist = Math.sqrt(Math.pow(d[0],2) + Math.pow(visionSettings.TGTh,2));
		return tgtDist;
	}

	public double calcAngle() {
		angle = (visionSettings.FOV * getTgtWidth()) / visionSettings.FOVx;
		return angle;
	}
	
	public double calcRatio() {
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
	public double getTgtHeight() {
		return data.height;
	}
	
	/**
	 * getTgtyWidth - returns the target pixel size 
	 * from the camera
	 * 
	 * @return - returns the target width in pixels
	 */
	public double getTgtWidth() {
		return data.width;
	}
	
	/**
	 * getTgtPos - returns the target position in x,y
	 * coordinates from the camera
	 * 
	 * @return - returns the target x,y position in pixels
	 */
	public double[] getTgtPos() {
		double[] tgtPos = {data.centerX, data.centerY};
		return tgtPos;
	}

//	public double[] getAimPos() {
//		double[] tgtPos = getTgtPos();
//		double x = (tgtPos[0] - (visionSettings.FOVx/2)) / (visionSettings.FOVx/2);
//		double y = -(tgtPos[1] - (visionSettings.FOVy/2)) / (visionSettings.FOVy/2);
//		double[] aimPos = {x,y};
//		return aimPos;
//	}

	public double getTgtArea() {
		return data.height * data.width;
	}
	
	public double getRatio() {
		return ratio;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double[] getAxialTgtDist() {
		return axialTgtDist;
	}
	
	public double getTgtDist() {
		return tgtDist;
	}
	
	public static double getMPI() {
		return MPI;
	}

	public double getFOV() {
		return visionSettings.FOV;
	}

	public double getFOVx() {
		return visionSettings.FOVx;
	}

	public double getFOVy() {
		return visionSettings.FOVy;
	}

	public double getTGTx() {
		return visionSettings.TGTx;
	}

	public double getTGTy() {
		return visionSettings.TGTy;
	}

	public double getTanFOV() {
		return visionSettings.tanFOV();
	}


	public double getTGTh() {
		return visionSettings.TGTh;
	}
}
