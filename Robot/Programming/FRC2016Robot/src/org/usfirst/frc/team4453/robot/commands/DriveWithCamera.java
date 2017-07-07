package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.library.Vision;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveWithCamera extends Command {

    public DriveWithCamera() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("DriveWithCamera");
    	Robot.ahrs.zeroYaw();
    	Robot.chassis.setPidVel(0);
    	Robot.chassis.enableChassisPID();
    	Robot.chassis.setAutoTurn(true);
    }

    // Called repeatedly when this Command is scheduled to run
    
    // TODO: Figure out the 3d coordinant system, so we don't have to use the 2d coordinants, and so we can be camera independant.
    protected void execute() {
    	double targetXPos = Vision.getTargetImgPosition("TheTarget").X;
    	double targetAngleOffset;
    	if(targetXPos == -99.0)
    	{
    		targetAngleOffset = 0;
    	}
    	else
    	{
    		double targetXOffset = Vision.getTargetImgPosition("TheTarget").X - (Vision.getFOVx()/2);
    		// Lots of trig, gets us the number of degrees we need to turn.
    		targetAngleOffset = Math.toDegrees(Math.atan(targetXOffset / ((Vision.getFOVx()/2) / Math.tan(Math.toRadians(Vision.getFOV()/2)))));
    	}
    	//Update the setpoint (does this work?);
    	double setpointAngle = Robot.ahrs.getYaw() + targetAngleOffset;
/*    	
    	if(setpointAngle > 180.0)
    	{
    		setpointAngle -= 360.0;
    	}
    	if(setpointAngle < -180.0)
    	{
    		setpointAngle += 360.0;
    	}
*/
    	SmartDashboard.putNumber("DriveWithCamera Output ", targetAngleOffset);
    	Robot.chassis.chassisSetSetpoint(setpointAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.chassis.getAutoTurn();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.disableChassisPID();
    	Robot.chassis.setAutoTurn(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
