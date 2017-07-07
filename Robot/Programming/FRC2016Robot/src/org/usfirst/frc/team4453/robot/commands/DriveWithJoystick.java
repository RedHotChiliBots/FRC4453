
package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4453.robot.Robot;

/**
 * DriveWithjoystick - gets commands from the joysticks defined in OI 
 * to send to the chassis method to drive the robot.
 * 
 * Requires the chassis. Only command that can be running for the chassis.
 * 
 */
public class DriveWithJoystick extends Command {

    public DriveWithJoystick() {
        // Is primary command for chassis. No other command can "require" the chassis.
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("DriveWithJoystick");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
     	double yCmd = Robot.oi.getLeftYDriveStick();
        double xCmd = Robot.oi.getLeftXDriveStick();
        double zCmd = Robot.oi.getThrottleDrive();
     	//double leftCmd = YVal + XVal;
     	//double rightCmd = YVal - XVal ;
     		
        //Robot.chassis.tankDrive(yCmd, xCmd);
        Robot.chassis.arcadeDrive(yCmd*zCmd, xCmd*zCmd);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
