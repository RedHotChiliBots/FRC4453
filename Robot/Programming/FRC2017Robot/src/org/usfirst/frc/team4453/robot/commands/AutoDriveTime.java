package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveTime extends Command {

	private double seconds;
	private double drivespeed;

	public AutoDriveTime(double drivetime, double speed) {
		requires(Robot.chassis);
		seconds = drivetime;
		drivespeed = speed;
	}

	@Override
	protected void initialize() {
		setTimeout(seconds);
	}

	@Override
	protected void execute() {
		Robot.chassis.arcadeDrive(drivespeed, 0.0);
		// The robot will drive until "setTimeout" runs out of time
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		Robot.chassis.stop();
	}

	@Override
	protected void interrupted() {
	}
}
