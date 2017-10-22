package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TiltReset extends Command {

    public TiltReset() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooter);
		setInterruptible(false);	// can not be interrupted
//    	setRunWhenDisabled(true);
//    	willRunWhenDisabled();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("TiltReset");
//    	setTimeout(10.0);
    	Robot.shooter.disable();

		Robot.shooter.tiltLower();
}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(Robot.shooter.tiltGetLowerLimit()) {
//        	Robot.shooter.tiltStop();
//    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.shooter.tiltGetLowerLimit();	// || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.tiltStop();
    	Timer.delay(0.25);	// allow motor to stop
    	Robot.shooter.tiltResetEncoder();
    	Robot.shooter.tiltSetDist(0.0);
    	Robot.shooter.getPIDController().enable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
