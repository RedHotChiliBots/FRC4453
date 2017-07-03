package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterFire extends Command {

    public ShooterFire() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.shooter.getIsReady()) {
    		Robot.shooter.solenoidFire();
           	setTimeout(0.5);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.shooter.solenoidReset();
		Robot.shooter.setIsReady(false);
		Robot.shooter.shooterStop();
		Robot.chassis.setAutoTurn(false);
		Robot.shooter.setAutoTilt(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		Robot.shooter.solenoidReset();
		Robot.shooter.setIsReady(false);
		Robot.shooter.shooterStop();
    }
}
