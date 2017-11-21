package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TiltSetAngle extends Command {

	private double button;
	private double angle;
	
    public TiltSetAngle(int button) {
        // Is primary command for chassis. No other command can "require" the chassis.
        this.button = button;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("TiltSetAngle");

    	requires(Robot.shooter);
    	
    	if (button == 1) {
    		angle = SmartDashboard.getNumber("tiltAngle1", 30.0);
    	} else if (button == 2) {
          	angle   = SmartDashboard.getNumber("tiltAngle2", 45.0);   		
    	}
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooter.tiltSetAngle(angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return true;	//Robot.shooter.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
