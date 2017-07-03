package org.usfirst.frc4453.commands;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.usfirst.frc4453.subsystems.LaunchShoot;
import org.usfirst.frc4453.subsystems.LaunchTilt;

/**
 * Command (launchShoot & launchTilt)
 * <p>
 * Reset shooter and tilt positions
 *
 * @author Developer
 */

public class ResetLaunchDistTilt extends CommandBase {
 	
    /**
     * Reset shooter and tilt positions
	 */
	public ResetLaunchDistTilt() {
		requires(launchShoot);
		requires(launchTilt);
	}
	
	//
	protected void initialize() {
		System.out.println("ResetLaunchDistTilt");
	}

	//
	protected void execute() {

		launchShoot.launchSetPoint(LaunchShoot.RESET_STRETCH);
		launchTilt.setDist(LaunchTilt.RESET_TILT);
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
