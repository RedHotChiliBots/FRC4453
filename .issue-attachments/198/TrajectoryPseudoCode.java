package org.usfirst.frc.team4453.robot;

import java.io.Console;

public class TrajectoryPseudoCode {

	public static void main(String[] args) {
		
		
		// necessary constants and variables to calculate shooting angle 
		//All values must be in meters for physics calculations 
		double distanceFromGoal  ; 
		distanceFromGoal = valueFromVisionSoftware ; // Distance from robot to target ON THE GROUND, get this from vision software
		
		double  heightOfShooter = ? ; 
		double heightOfTarget = 8.1 ft - heightOfShooter // Height of the target minus the height of the robot
		double velocityOfShooter = ?		// Get this from shooting trials, should be in m/s
		
		
		double distanceFromGoalInMeters = distanceFromGoal //converted to meters 	
		double heightOfTargetInMeters = heightOfTarget // converted to meters
		double gravity = 9.80665 		// acceleration due to gravity in meters per second 
		
		//Calculations for the necessary shooting angle:
		double descriminant = ((velocityOfShooter^4)-gravity*(gravity*(distanceFromGoalInMeters^2)+2*heightOfTargetInMeters*(velocityOfShooter^2)))^(1/2) 		
		double shootingAngleRadians = arctan(((velocityOfShooter^2) - descriminant)/(gravity * distanceFromGoalInMeters))		// Calculates the shooting angle in radians
		double shootingAngleDegrees = shootingAngleRadians * (180/pi) 		// Converts shooting angle into degrees 
		
		double currentAngle = angleOfShooterFromEncoder 		//Get this from the shooter encoder
		
	
		//displays important values on the dashboard
		Dashboard.Display(velocityOfShooter)
		Dashboard.Display(distanceFromGoalInMeters) 
		Dashboard.Display(shootingAngleDegrees)
		Dashboard.Display(currentAngle)
		
		method ShootWithCalculations() {
			while(currentAngle >= shootingAngleDegrees){
				leadScrew.Lower 
			}
			while(currentAngle <= shootingAngleDegrees){
				leadScrew.Raise
			}
			if (currentAngle == shootingAngleDegrees){
				shooter.shoot
			}
		}
	}

}
