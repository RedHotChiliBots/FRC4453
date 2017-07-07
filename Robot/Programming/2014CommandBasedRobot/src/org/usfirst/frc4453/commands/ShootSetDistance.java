package org.usfirst.frc4453.commands;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.usfirst.frc4453.RobotMap;
import org.usfirst.frc4453.subsystems.LaunchShoot;

/**
 * Set the stretch distance of the shooter spring.
 *
 * @author Developer
 */

public class ShootSetDistance extends CommandBase {
    
    private double distCmd;
    private double m_cmd;
    private boolean m_dyn = false;
    private boolean m_src = false;
 	
    /**
     * Use joystick values set the position of the shooter.
     */
	public ShootSetDistance() {
		requires(launchShoot);
		m_dyn = true;
	}
	
	/**
	 * Use either joystick or robot preferences to set the position
	 * of the shooter.
	 * 
	 * @param src boolean - true, use preferences; false, use joystick
	 */
	public ShootSetDistance(boolean src) {
		requires(launchShoot);
		m_src = src;
	}

	/**
	 * Use constant value to set position of shooter
	 * 
	 * @param dist double - stretch distance (0.0 to 21.0 inches)
	 */
	public ShootSetDistance(double dist) {
		requires(launchShoot);
		distCmd = dist;
	}
	
	/**
	 * Get the current position of the servos
	 */
	protected void initialize() {
		System.out.println("ShootSetDistance");
		if (m_src) {
			distCmd = CommandBase.prefs.getDouble("ShootDistance",0.0);	
		}
	}

	//
	protected void execute() {
		/**
		 * Calculate a new incremental position and 
		 * limit to the range from 0 to 1.
		 */
		if (m_dyn) {
			//System.out.println("from joystick");
			distCmd = launchShoot.launchGetSetPoint();

			m_cmd = CommandBase.oi.getLaunchDistStick();	// range -1 to +1

			distCmd += (m_cmd * RobotMap.LAUNCH_DIST_MAX_RATE);
			if (distCmd > LaunchShoot.MAX_STRETCH) {
				distCmd = LaunchShoot.MAX_STRETCH ;
			} else if (distCmd < LaunchShoot.MIN_STRETCH) {
				distCmd = LaunchShoot.MIN_STRETCH;
			}
		}

		//System.out.println("SetCameraServo execute exit horzCmd = "+horzCmd+" vertCmd = "+vertCmd);
		launchShoot.launchSetPoint(distCmd);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (!m_dyn || m_src);
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
