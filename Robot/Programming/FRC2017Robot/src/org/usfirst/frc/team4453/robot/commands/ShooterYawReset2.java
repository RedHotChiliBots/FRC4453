package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterYawReset2 extends Command {

    public ShooterYawReset2() {
//        requires(Robot.shooter);
    }

    protected void initialize() {
    	System.out.println("YawReset2: Initialize");
    	Robot.shooter.yawEnableSoftLimit(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("YawReset2: Execute");
    	Robot.shooter.yawSetAngle(65.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.shooter.yawIsReady(2.0);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("YawReset2: End");
    	Robot.shooter.yawStop();
    	Robot.shooter.yawResetEncoder();
    	Robot.shooter.yawSetAngle(0);
    	Robot.shooter.yawEnableSoftLimit(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.yawStop();
    }
}
