/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.commands;

/**
 * Command (chassis & sensors)
 * <p>
 * Drives to a specified distance from wall.
 *
 * @author Developer
 */

public class DriveToDistance extends CommandBase {
	boolean m_dyn;
	double m_dist;
	boolean m_distReached;
	double m_lSpeed;
	double m_rSpeed;
	double m_aSpeed;
	
	/**
	 * Drive to a specified distance from the wall.
	 * 
	 * @param dist double - distance from sonar
	 * @param lSpeed double - left motor speed (1.0 to -1.0)
	 * @param rSpeed double - right motor speed (1.0 to -1.0)
	 */
	public DriveToDistance(double dist, double lSpeed, double rSpeed) {
		requires(sensors);
		requires(chassis);

//		m_distReached = false;
		m_dist = dist;
		m_lSpeed = lSpeed;
		m_rSpeed = rSpeed;
	}

	public DriveToDistance(boolean src) {
		requires(sensors);
		requires(chassis);

		m_dyn = src;
//		m_distReached = false;
	}

	protected void initialize() {
		if (m_dyn) {
			m_dist = CommandBase.prefs.getDouble("AutoDistance", 10.0);
//			m_lSpeed = CommandBase.prefs.getDouble("LeftSpeed", 0.75);
//			m_rSpeed = CommandBase.prefs.getDouble("RightSpeed", 0.75);
			m_aSpeed = CommandBase.prefs.getDouble("AutoSpeed", 0.75);
		}

		System.out.println("DriveToDist - Dist: "+m_dist+" Speed: "+m_aSpeed);

		CommandBase.sensors.gyroReset();
	}

	protected void execute() {
		//System.out.println("DriveWithJoystick - execute");

//		if (sensors.sonarGetRange() <= m_dist) {
//			m_distReached = true;
//			chassis.driveStop();
//		} else {
//			chassis.driveWithJoystick(m_lSpeed,m_rSpeed);
			chassis.driveStraight(-m_aSpeed, CommandBase.sensors.gyroGetAngle());
//		}
	}

	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
//		return (m_distReached);
		return (sensors.sonarGetRange() <= m_dist);
	}

	// Called once after isFinished returns true
	protected void end() {
		chassis.driveStop();
	}

    // Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
