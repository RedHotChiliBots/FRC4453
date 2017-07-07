package org.usfirst.frc4453.commands;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.usfirst.frc4453.Library;
import org.usfirst.frc4453.RobotMap;
import org.usfirst.frc4453.subsystems.LaunchTilt;

/**
 * Set tilt angle for shooter.
 *
 * @author Developer
 */

public class TiltSetAngle extends CommandBase {
    
    private double tiltCmd;
    private double m_tilt;
    private boolean m_dyn = false;
    private boolean m_src = false;
 	
    /**
     * Use joystick to set tilt angle.
     */
	public TiltSetAngle() {
		requires(launchTilt);
		m_dyn = true;
	}
	
	/**
	 * Use joystick or robot preferences to set tilt angle.
	 * 
	 * @param src boolean - true, preferences; false, joystick
	 */
	public TiltSetAngle(boolean src) {
		requires(launchTilt);
		m_src = src;
	}

	/**
	 * Use constant value to set tilt angle.
	 * 
	 * @param angle double - tilt angle (0.0 to 60.0 degrees)
	 */
	public TiltSetAngle(double angle) {
		requires(launchTilt);
		m_tilt = angle;

		//System.out.println("SetCameraServo constructor set one m_hPos = "+m_hPos+" m_vPos = "+m_vPos);
	}
	
	//
	protected void initialize() {
		System.out.println("TiltSetAngle");
		if (m_src) {
			tiltCmd = CommandBase.prefs.getDouble("ShootTilt",0.0);	
		}
	}

	//
	protected void execute() {
		if (m_dyn) {
			//System.out.println("from joystick");
			tiltCmd = Library.calcAngle(launchTilt.tiltGetSetPoint());

			m_tilt = CommandBase.oi.getLaunchTiltStick();	// range -1 to +1

			tiltCmd += (m_tilt * RobotMap.LAUNCH_TILT_MAX_RATE);
			if (tiltCmd > LaunchTilt.MAX_ANGLE) {
				tiltCmd = LaunchTilt.MAX_ANGLE;
			} else if (tiltCmd < LaunchTilt.MIN_ANGLE) {
				tiltCmd = LaunchTilt.MIN_ANGLE;
			}
		}

		//System.out.println("SetCameraServo execute exit horzCmd = "+horzCmd+" vertCmd = "+vertCmd);
		launchTilt.setAngle(tiltCmd);
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
