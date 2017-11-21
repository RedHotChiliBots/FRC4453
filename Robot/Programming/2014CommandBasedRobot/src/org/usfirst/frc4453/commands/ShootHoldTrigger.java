package org.usfirst.frc4453.commands;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Hold shooter trigger
 * @author Developer
 */

public class ShootHoldTrigger extends CommandBase {
	
    /**
     * 
     */
	public ShootHoldTrigger() {
		requires(launchShoot);
	}
	
	//
	protected void initialize() {
		System.out.println("ShootHoldTrigger");
	}

	//
	protected void execute() {
		launchShoot.holdShooterTrigger();
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
