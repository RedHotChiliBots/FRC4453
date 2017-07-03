package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivingWithJoysticks extends Command {

    public DrivingWithJoysticks() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
     	double yCmd = Robot.oi.getLeftYDriveStick();
        double xCmd = Robot.oi.getLeftXDriveStick();
        double zCmd = Robot.oi.getThrottleDrive();
     	
     	 Robot.chassis.arcadeDrive(yCmd*zCmd, xCmd*zCmd);
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
