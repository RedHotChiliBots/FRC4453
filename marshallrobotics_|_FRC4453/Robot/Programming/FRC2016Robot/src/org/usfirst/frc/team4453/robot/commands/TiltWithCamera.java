package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.library.Tilt;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TiltWithCamera extends Command {

    public TiltWithCamera() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.setAutoTilt(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double shootAngle = Tilt.calcShootAngle(false);
    	SmartDashboard.putNumber("TiltWithCamera Angle", shootAngle);
    	Robot.shooter.tiltSetAngle(shootAngle);
        SmartDashboard.putNumber("Shooter Setpoint", Robot.shooter.tiltGetAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.shooter.getAutoTilt();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
