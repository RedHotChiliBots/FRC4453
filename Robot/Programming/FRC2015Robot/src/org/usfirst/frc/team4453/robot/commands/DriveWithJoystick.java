
package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4453.robot.Robot;

/**
 *
 */
public class DriveWithJoystick extends Command {

    public DriveWithJoystick() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//System.out.println("DriveWithJoystick");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double leftYCmd = Robot.oi.getLeftYDriveStick();
    	double leftXCmd = Robot.oi.getLeftXDriveStick();
    	double rightXCmd = Robot.oi.getRightXDriveStick(); 
    	
    	Robot.chassis.driveWithJoystick(leftXCmd, leftYCmd, rightXCmd);
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
