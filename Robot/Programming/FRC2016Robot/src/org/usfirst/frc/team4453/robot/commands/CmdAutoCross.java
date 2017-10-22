package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CmdAutoCross extends Command {

	private double seconds;
	private double speed;

	
    public CmdAutoCross() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("AutoCross");

    	seconds = SmartDashboard.getNumber("crsTime", 2.6);	// seconds
       	speed   = SmartDashboard.getNumber("crsVel", 0.25);	// speed [0-1]
       	System.out.println("crsVel: " + speed);
       	System.out.println("crsTime: " + seconds);

    	setTimeout(this.seconds);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.forward(speed);
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
