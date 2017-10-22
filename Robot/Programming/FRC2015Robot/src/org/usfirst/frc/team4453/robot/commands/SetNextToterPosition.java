package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetNextToterPosition extends Command {
	
    public SetNextToterPosition() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.toter);
    }

    // Called just before this Command runs the first time
    protected void initialize() { 	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.toter.toterSetSetpoint(Robot.toter.nextToterPosition());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//        return Robot.toter.onTarget();
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
