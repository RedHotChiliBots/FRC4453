package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	AutoNoOp - Does nothing for two seconds then stops.
 *	Used to cause the robot to stay still during autonomous mode.
 */
public class AutoNoOp extends Command {

    public AutoNoOp() {
        // Is primary command for chassis. No other command can "require" the chassis.
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(2.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.stop();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
