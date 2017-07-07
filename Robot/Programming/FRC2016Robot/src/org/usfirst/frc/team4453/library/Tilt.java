package org.usfirst.frc.team4453.library;

import org.usfirst.frc.team4453.newvisionlib.Point3d;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tilt {
	private static final double G = 9.80665;		// acceleration due to gravity in meters per second 
	
	/*
	 * The following constants were determined from the CAD model and trials
	 */
	//private static final double VEL = 8.5972881;			// Get this from shooting trials, should be in m/s
	//private static double VEL = 9.5;
	private static double VEL = 10.5;
	/*
	 * Tilt Robot constants
	 */
	private static final double TILT_TRI_SIDE_A = 6.7268120235;	// from pivot to base mount
	private static final double TILT_TRI_SIDE_B = 5.25;			// from pivot to screw mount
	private static final double TILT_TRI_SIDE_C = 2.48175566;	// lead screw length at zero distance
	private static final double TILT_TRI_ANGLE = 48.01278750;	// angle at zero distance
	
	/*
	 * Tilt Equation constants
	 */
	private static final double TILT_TRI_FACTOR_1 = Math.pow(TILT_TRI_SIDE_A,2) + Math.pow(TILT_TRI_SIDE_B,2);
	private static final double TILT_TRI_FACTOR_2 = 2 * (TILT_TRI_SIDE_A * TILT_TRI_SIDE_B);
	
	private Tilt() {
    }
	
	public static double calcSide(double angle) {
		return (Math.sqrt(TILT_TRI_FACTOR_1 
				- (TILT_TRI_FACTOR_2 * Math.cos(Math.toRadians(angle+TILT_TRI_ANGLE))))
				- TILT_TRI_SIDE_C);
	}
	
	/**
	 * Convert the tilt lead screw distance to tilt angle
	 * 
	 * @param dist double - tilt lead screw distance (inches)
	 * @return double - tilt angle (degrees)
	 */
	public static double calcAngle(double dist) {
		return (Math.toDegrees(Math.acos((TILT_TRI_FACTOR_1 
				- Math.pow(dist+TILT_TRI_SIDE_C,2)) / TILT_TRI_FACTOR_2))
				- TILT_TRI_ANGLE);
	}	
	
	public static double calcShootAngle(boolean test) {
		//All values must be in meters for physics calculations 
		//VEL = SmartDashboard.getNumber("Shoot Vel");
		Point3d tgtPos = Vision.getTargetPosition("TheTarget");
		double distanceToGoal = Math.abs(tgtPos.Z);	// Distance from robot to target ON THE GROUND, get this from vision software
		double heightOfTarget = -tgtPos.Y + Vision.getCAMh(); // Height of the target minus the height of the robot
		// Removed compensation for angle, as we think it is cyclic.
		
		if (!test) {
			SmartDashboard.putNumber("Dist to Goal", distanceToGoal) ;
		}
		
		if (distanceToGoal < 2.0) {
			return 0.0;
		}
		
		//Calculations for the necessary shooting angle:
		double robotAngle;

		double inroot = Math.pow(VEL,4)-G*(G*Math.pow(distanceToGoal,2)+2*heightOfTarget*Math.pow(VEL,2));	
		
		if(!test)
		{
			SmartDashboard.putNumber("Tilt InRoot", inroot);
		}
		if(inroot < 0)
		{
			if(!test)
			{
				SmartDashboard.putBoolean("Can Make Shot", false);
			}
			return 0.0;
		}
		
		double shootingAngle1 = Math.toDegrees(Math.atan((Math.pow(VEL,2) - Math.sqrt(inroot))/(G * distanceToGoal)));		// Calculates the shooting angle in radians and then converts into degrees
		double shootingAngle2 = Math.toDegrees(Math.atan((Math.pow(VEL,2) + Math.sqrt(inroot))/(G * distanceToGoal)));
		double shootingAngle = Math.min(shootingAngle1, shootingAngle2);
		
		if (test) {
			robotAngle = 0.0;
		} else {
			robotAngle = Robot.ahrs.getPitch(); 		//Get pitch from the AHRS
		}
		
		//displays important values on the dashboard
		if (!test) {
			SmartDashboard.putNumber("Shoot Deg", shootingAngle);
			SmartDashboard.putNumber("Robot Deg", robotAngle);
			SmartDashboard.putBoolean("Can Make Shot", true);
		}

		return shootingAngle - robotAngle;
	}
	
	
}
