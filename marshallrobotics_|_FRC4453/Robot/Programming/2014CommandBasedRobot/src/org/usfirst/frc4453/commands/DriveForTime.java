/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 * Command
 * Drive robot at a specified speed for a specified time
 * 
 * @author Developer
 */

public class DriveForTime extends CommandBase {
	boolean m_dyn = false;
	double m_time;
	double m_lSpeed;
	double m_rSpeed;
	double m_aSpeed;
	
	/**
	 * Drive robot for a specified amount of time (sec) provided 
	 * via parameters
	 * 
	 * @param time double - drive time in seconds
	 * @param lSpeed double - left wheel speed (-1.0 to 1.0)
	 * @param rSpeed double - right wheel speed (-1.0 to 1.0)
	 */
	public DriveForTime(double time, double lSpeed, double rSpeed) {
		requires(chassis);
		m_time = time;
		m_lSpeed = lSpeed;
		m_rSpeed = rSpeed;
	}
	
	/**
	 * Drive robot for a specified amount of time using values
	 * from robot preferences
	 */
	public DriveForTime(boolean src) {
		requires(chassis);
		m_dyn = src;
	}
	
	protected void initialize() {
		if (m_dyn) {
			m_time = CommandBase.prefs.getDouble("AutoTime", 1.5);
//			m_lSpeed = CommandBase.prefs.getDouble("LeftSpeed", 0.75);
//			m_rSpeed = CommandBase.prefs.getDouble("RightSpeed", 0.75);
			m_aSpeed = CommandBase.prefs.getDouble("AutoSpeed", 0.75);
		}
		
		CommandBase.sensors.gyroReset();

		System.out.println("DriveForTime - Time: "+m_time+" Speed: "+m_aSpeed);
	}

	protected void execute() {
		//System.out.println("DriveWithJoystick - execute");

//		chassis.driveWithJoystick(m_lSpeed,m_rSpeed);
		chassis.driveStraight(-m_aSpeed, CommandBase.sensors.gyroGetAngle());
		
		Timer.delay(m_time);

//		chassis.driveWithJoystick(0.0,0.0);
		chassis.driveStop();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

    // Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
