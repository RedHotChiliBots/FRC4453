package org.usfirst.frc.team4453.library;

//import org.usfirst.frc.team4453.robot.Robot;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Library class is a collection of utility functions used throughout the robot code.
 */

//Commented, new vision API breaks this.
//public class TiltOld {
//
//	private static final double MPI = 2.54/100.0;	// Meters per inch
//	private static final double G = 9.80665;		// acceleration due to gravity in meters per second 
//
//	/*
//	 * The following constants were determined from the CAD model and trials
//	 */
//	private static final double VEL    = 8.5972881;			// Get this from shooting trials, should be in m/s
//	private static final double PIVOTy = 5.20740728*MPI;	// Shooter pivot to camera (vert)
//	private static final double PIVOTx = 7.63707590*MPI;	// Shooter pivot to camera (horz)
//	private static final double PIVOTz = 8.97775344*MPI;	// Shooter pivot to floor
//	private static final double BALLx  = 9.37118570*MPI;	// Ball center from Pivot (horz)
//	private static final double BALLy  = 5.625*MPI;			// Ball center from Pivot (vert)
//	private static final double BALLh  = Math.sqrt(Math.pow(BALLx, 2)+Math.pow(BALLy, 2));	// Ball center from Pivot (hyp)
//	private static final double BALLangle = Math.toDegrees(Math.atan(BALLy/BALLx));			// Ball center angle from Pivot/Platform
//
//
//	/*
//	 * Tilt Robot constants
//	 */
//	private static final double TILT_TRI_SIDE_A = 6.7268120235;	// from pivot to base mount
//	private static final double TILT_TRI_SIDE_B = 5.25;			// from pivot to screw mount
//	private static final double TILT_TRI_SIDE_C = 2.48175566;	// lead screw length at zero distance
//	private static final double TILT_TRI_ANGLE = 48.01278750;	// angle at zero distance
//	
//	/*
//	 * Tilt Equation constants
//	 */
//	private static final double TILT_TRI_FACTOR_1 = Math.pow(TILT_TRI_SIDE_A,2) + Math.pow(TILT_TRI_SIDE_B,2);
//	private static final double TILT_TRI_FACTOR_2 = 2 * (TILT_TRI_SIDE_A * TILT_TRI_SIDE_B);
//
//	/*
//	 * Test variable
//	 */
//	private static double testTiltAngle;
//	
//    //make the constructor private, so no one can instantiate this class
//    private TiltOld() {
//    }
//
//    /**
//     * Convert a tilt angle to the length of the tilt lead screw.
//     * 
//     * @param angle double - tilt angle (degrees)
//     * @return double - tilt lead screw length (inches)
//     */
//	public static double calcSide(double angle) {
//		return (Math.sqrt(TILT_TRI_FACTOR_1 
//				- (TILT_TRI_FACTOR_2 * Math.cos(Math.toRadians(angle+TILT_TRI_ANGLE))))
//				- TILT_TRI_SIDE_C);
//	}
//	
//	/**
//	 * Convert the tilt lead screw distance to tilt angle
//	 * 
//	 * @param dist double - tilt lead screw distance (inches)
//	 * @return double - tilt angle (degrees)
//	 */
//	public static double calcAngle(double dist) {
//		return (Math.toDegrees(Math.acos((TILT_TRI_FACTOR_1 
//				- Math.pow(dist+TILT_TRI_SIDE_C,2)) / TILT_TRI_FACTOR_2))
//				- TILT_TRI_ANGLE);
//	}	
//	
//	/**
//	 * getShooterHeight
//	 * @return height in meters
//	 */
//	public static double getShooterHeight(boolean test) {
//		double tiltAngle;
//		double [] dist = new double[2];
//		
//		if (test) {
//			tiltAngle = testTiltAngle;
//		} else {
//			tiltAngle = Robot.shooter.tiltGetAngle();	// Get this from the shooter encoder
//			SmartDashboard.putNumber("Tilt Deg", tiltAngle);
//		}
//		
//		dist = Robot.vision.getAxialTgtDist();	
//		double getShooterHeight =  dist[1] + PIVOTy + BALLh*Math.sin(Math.toRadians(tiltAngle+BALLangle));
//		SmartDashboard.putNumber("getShooterHeight", getShooterHeight);
//		return getShooterHeight;		
//	}
//	
//	/* Why is this same piece of code repeated twice? */ 
//	public static double getShooterDist(boolean test) {
//		double tiltAngle;
//		double [] dist = new double[2];
//		
//		if (test) {
//			tiltAngle = testTiltAngle;
//		} else {
//			tiltAngle = Robot.shooter.tiltGetAngle();	//Get this from the shooter encoder
//			SmartDashboard.putNumber("Tilt Deg", tiltAngle);
//		}
//		
//		dist = Robot.vision.getAxialTgtDist();	
//		double getShooterDist = dist[0] + PIVOTx + BALLh*Math.cos(Math.toRadians(tiltAngle+BALLangle));
//		SmartDashboard.putNumber("getShooterDist", getShooterDist);
//		return getShooterDist;
//	}
//	
//	public static double calcShootAngle(boolean test) {
//		//All values must be in meters for physics calculations 
//
//		double distanceToGoal = getShooterDist(test);	// Distance from robot to target ON THE GROUND, get this from vision software
//		double heightOfTarget = getShooterHeight(test); // Height of the target minus the height of the robot
//		
//		if (distanceToGoal == 0.0 || heightOfTarget == 0.0) {
//			return 0.0;
//		}
//		
//		//Calculations for the necessary shooting angle:
//		double robotAngle;
//
//		double descriminant = Math.pow(Math.pow(VEL,4)-G*(G*Math.pow(distanceToGoal,2)+2*heightOfTarget*Math.pow(VEL,2)),(0.5));	
//		double shootingAngle = Math.toDegrees(Math.atan((Math.pow(VEL,2) - descriminant)/(G * distanceToGoal)));		// Calculates the shooting angle in radians and then converts into degrees
//		
//		if (test) {
//			robotAngle = 0.0;
//		} else {
//			robotAngle = Robot.ahrs.getPitch(); 		//Get pitch from the AHRS
//		}
//		
//		//displays important values on the dashboard
//		if (!test) {
//			SmartDashboard.putNumber("Shoot Vel", VEL);
//			SmartDashboard.putNumber("Dist to Goal", distanceToGoal) ;
//			SmartDashboard.putNumber("Shoot Deg", shootingAngle);
//			SmartDashboard.putNumber("Robot Deg", robotAngle);
//		}
//		
//		double calcShootAngle = shootingAngle - robotAngle;
//		SmartDashboard.putNumber("calcShootAngle", calcShootAngle);
//		return calcShootAngle;
//	}
//	
//	public static void setTestTiltAngle(double a) {
//		testTiltAngle = a;
//	}
//	
//	/*
//	 * Used for testing
//	 */
//	
//	public static double getMPI() {
//		return MPI;
//	}
//	
//	public static double getVEL() {
//		return VEL;
//	}
//	
//	public static double getG() {
//		return G;
//	}
//	
//	public static double getPIVOTx() {
//		return PIVOTx;
//	}
//	
//	public static double getPIVOTy() {
//		return PIVOTy;
//	}
//	
//	public static double getBALLx() {
//		return BALLx;
//	}
//	
//	public static double getBALLy() {
//		return BALLy;
//	}
//	
//	public static double getBALLh() {
//		return BALLh;
//	}
//	
//	public static double getPIVOTz() {
//		return PIVOTz;
//	}
//	
//	public static double getBALLangle() {
//		return BALLangle;
//	}
//}
