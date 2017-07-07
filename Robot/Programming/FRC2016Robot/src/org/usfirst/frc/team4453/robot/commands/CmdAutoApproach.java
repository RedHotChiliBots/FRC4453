package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	AutoDriveTime - drives robot forward at set speed for set amount of time
 *	Speed and Time can be initialized in Robot Preferences and changed on the dashboard
 */
public class CmdAutoApproach extends Command {

	private double speed;
	private double pitch;
	private double seconds;

    public CmdAutoApproach() {
        // Is primary command for chassis. No other command can "require" the chassis.
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("AutoApproach");

       	speed   = SmartDashboard.getNumber("appVel", 0.25);	// speed [0-1]
       	pitch   = SmartDashboard.getNumber("defPitch", 5.0);	// degrees
       	seconds   = SmartDashboard.getNumber("defDelay", 1.5);	// seconds
       	System.out.println("defDelay: " + seconds);
       	System.out.println("appVel: " + speed);
       	System.out.println("defPitch: " + pitch);
       	
    	setTimeout(seconds);

//    	Robot.chassis.setPidVel(speed);
//      	Robot.chassis.setSetpoint(Robot.ahrs.getHeading());
//    	Robot.chassis.enable();
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.forward(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	double p = Robot.ahrs.getPitch();
    	//System.out.println("CurrentPitch: "+p);
    	return isTimedOut();
//    	return isTimedOut() && (p >= pitch);
//    	return (p >= pitch);	// || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
//    	Robot.chassis.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
