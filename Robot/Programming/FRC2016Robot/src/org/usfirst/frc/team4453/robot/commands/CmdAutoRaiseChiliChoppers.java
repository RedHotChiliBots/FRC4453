package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CmdAutoRaiseChiliChoppers extends Command {

	private double angle;
	
    public CmdAutoRaiseChiliChoppers() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooter);
        angle   = SmartDashboard.getNumber("ccUpAngle", 0.0);	// degrees  was 20    
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("AutoRaiseChiliChoppers");
    	Robot.shooter.tiltSetAngle(angle);
       	System.out.println("ccUpAngle: "+angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println("Testing Shooter onTarget()");
        //return Robot.shooter.onTarget();
        return (Math.abs(Robot.shooter.getSetpoint() - Robot.shooter.tiltGetDist()) < 0.25);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
