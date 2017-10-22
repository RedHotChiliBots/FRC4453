/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.commands;

/**
 * Command (chassis)
 * <p>
 * Tank drive using the joystick output to determine robot speed.
 *
 * @author Developer
 */

public class DriveWithJoystick extends CommandBase {
	
	/**
	 * Use joysticks to determine left and right motor speeds.
	 */
	public DriveWithJoystick() {
		requires(chassis);
	}
	
	protected void initialize() {
		System.out.println("DriveWithJoystick");
	}

	protected void execute() {

		double leftCmd = CommandBase.oi.getLeftDriveStick();
		double rightCmd = CommandBase.oi.getRightDriveStick();

		chassis.driveWithJoystick(leftCmd,rightCmd);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

    // Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
