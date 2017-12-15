package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HopperAgitatorReset extends Command {

    public HopperAgitatorReset() {
     	requires(Robot.hopper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.hopper.agitatorEnableSoftLimits(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.hopper.agitatorMove(-1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.hopper.agitatorHitLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.hopper.agitatorStop();
    	Robot.hopper.agitatorResetEncoder();
    	Robot.hopper.agitatorRight();
    	Robot.hopper.agitatorEnableSoftLimits(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.hopper.agitatorStop();
    }
}
