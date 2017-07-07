package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterTiltReset extends Command {

    public ShooterTiltReset() {
//        requires(Robot.shooter);
    }

    protected void initialize() {
    	System.out.println("TiltReset: Initialize");
    	Robot.shooter.tiltEnableSoftLimit(false);
    }

    protected void execute() {
    	System.out.println("TiltReset: Execute");
    	Robot.shooter.tiltLower();
    }

    protected boolean isFinished() {
        return Robot.shooter.tiltGetLowerLimit();
    }

    protected void end() {
    	System.out.println("TiltReset: End");
    	Robot.shooter.tiltStop();
    	Robot.shooter.tiltResetEncoder();
    	Robot.shooter.tiltSetAngle(0);
    	Robot.shooter.tiltEnableSoftLimit(true);
}

    protected void interrupted() {
        Robot.shooter.tiltStop();
    }
}
