package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterYawReset1 extends Command {

    public ShooterYawReset1() {
//        requires(Robot.shooter);
    }

    protected void initialize() {
    	System.out.println("YawReset1: Initialize");
    	Robot.shooter.yawEnableSoftLimit(false);
   	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("YawReset1: Execute");
    	Robot.shooter.yawMove(-0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.shooter.yawHitLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("YawReset1: End");
    	Robot.shooter.yawStop();
    	Robot.shooter.yawResetEncoder();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.yawStop();
    }
}
