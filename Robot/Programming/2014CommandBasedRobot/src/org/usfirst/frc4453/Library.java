package org.usfirst.frc4453;

import com.sun.squawk.util.MathUtils;


/**
 * The Library class is a collection of utility functions used throughout the robot code.
 */
public class Library {

	/*
	 * Tilt Robot constants
	 */
	private static final double TILT_TRI_SIDE_A = 12+(19/32);	// from pivot to base mount
	private static final double TILT_TRI_SIDE_B = 12+(19/32);	// from pivot to slide mount
	
	/*
	 * Tilt Equation constants
	 */
	private static final double TILT_TRI_FACTOR_1 = MathUtils.pow(TILT_TRI_SIDE_A,2) + MathUtils.pow(TILT_TRI_SIDE_B,2);
	private static final double TILT_TRI_FACTOR_2 = 2 * (TILT_TRI_SIDE_A * TILT_TRI_SIDE_B);

    //make the constructor private, so no one can instantiate this class
    private Library() {
    }

    /**
     * Convert a tilt angle to the length of the tilt lead screw.
     * 
     * @param angle double - tilt angle (degrees)
     * @return double - tilt lead screw length (inches)
     */
	public static double calcSide(double angle) {
		return (Math.sqrt(TILT_TRI_FACTOR_1 - (TILT_TRI_FACTOR_2 * Math.cos(Math.toRadians(angle)))));
	}
	
	/**
	 * Convert the tilt lead screw distance to tilt angle
	 * 
	 * @param dist double - tilt lead screw distance (inches)
	 * @return double - tilt angle (degrees)
	 */
	public static double calcAngle(double dist) {
		return (Math.toDegrees(MathUtils.acos((TILT_TRI_FACTOR_1 - MathUtils.pow(dist,2)) / TILT_TRI_FACTOR_2)));
	}
	
	public static double servo2Angle(double pos) {
		return (pos * 90.0) - 45.0;
	}
	
	public static double angle2Servo(double deg) {
		return (deg + 45.0) / 90.0;
	}
	
}
