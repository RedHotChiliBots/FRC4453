package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetToterPosition extends Command {

	private double setPos;
	
    public SetToterPosition(double pos) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.toter);
        setPos = pos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.toter.setSetpoint(setPos);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
