package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ResetToterMotor extends Command {

    public ResetToterMotor() {
 		SmartDashboard.putString("ResetToterMotor", "constructor");
        // Use requires() here to declare subsystem dependencies
        requires(Robot.toter);

        setInterruptible(false);	// can not be interrupted
    	setRunWhenDisabled(true);
    	willRunWhenDisabled();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
 		SmartDashboard.putString("ResetToterMotor", "initialize");
    	System.out.println("ResetToterMotor");
    	
    	setTimeout(5.0);
    	Robot.toter.disableToterPID();	// disable toter PID controller
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
 		SmartDashboard.putString("ResetToterMotor", "execute");
    	Robot.toter.reverseToter();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.toter.toterGetLimit() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
 		SmartDashboard.putString("ResetToterMotor", "end");
    	Robot.toter.stopToter();
    	Timer.delay(1.0);	// allow motor to stop
    	Robot.toter.resetToterEncoder();
    	Robot.toter.enableToterPID();	// enable toter PID controller
    	Robot.toter.initToterPos();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
