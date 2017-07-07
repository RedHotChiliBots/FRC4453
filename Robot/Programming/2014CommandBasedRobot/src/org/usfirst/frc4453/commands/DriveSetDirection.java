package org.usfirst.frc4453.commands;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Command (chassis)
 * <p>
 * Sets the drive direction.  Used to flip joystick orientation
 * for collecting a ball versus shoot a ball.
 *
 * @author Developer
 */

public class DriveSetDirection extends CommandBase {
    
    private double driveDirection;
    private boolean toggle = false;
 	
    /**
     * Toggle the current drive direction.
     */
	public DriveSetDirection() {
		requires(chassis);
		toggle = true;
	}

	/**
	 * Set the drive direction using the parameter.
	 * 
	 * @param dir double - forward +1.0; reverse -1.0
	 */
	public DriveSetDirection(double dir) {
		requires(chassis);
		driveDirection = dir;
	}
	
	//
	protected void initialize() {
		if (toggle) {
			driveDirection = -1.0*chassis.getDriveDirection();
		}
		
		System.out.println("DriveSetDirection - Dir: "+driveDirection);
	}

	/**
	 * Perform command
	 */
	protected void execute() {
		chassis.setDriveDirection(driveDirection);
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
