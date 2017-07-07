/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.commands;

/**
 *
 * @author Developer
 */

public class TiltWithJoystick extends CommandBase {
	
	public TiltWithJoystick() {
		requires(launchTilt);
	}
	
	protected void initialize() {
		System.out.println("TiltWithJoystick");
	}

	protected void execute() {
		//System.out.println("DriveWithJoystick - execute");

		double cmd = CommandBase.oi.getLaunchTiltStick();

		launchTilt.tiltWithJoystick(cmd);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
