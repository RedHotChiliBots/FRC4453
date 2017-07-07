/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.commands;


/**
 *
 * @author Developer
 */

public class ShootWithJoystick extends CommandBase {
	
	public ShootWithJoystick() {
		requires(launchShoot);
	}
	
	protected void initialize() {
		System.out.println("ShootWithJoystick");
	}

	protected void execute() {
		//System.out.println("DriveWithJoystick - execute");

		double cmd = CommandBase.oi.getLaunchDistStick();

		launchShoot.shootWithJoystick(cmd);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
