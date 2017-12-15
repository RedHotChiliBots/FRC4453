package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.library.Vision;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoShoot extends Command {

	private double seconds;

	public AutoShoot() {
		seconds = 15;
	}

	public AutoShoot(double time) {
		seconds = time;
	}

	@Override
	protected void initialize() {
		setTimeout(seconds);
	}

	@Override
	protected void execute() {
		Robot.shooter.shooterFire(Robot.shooter.getAimSpeed(Vision.getBoilerDist()));
	}

	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override
	protected void end() {
		Robot.shooter.shooterStop();
	}

	@Override
	protected void interrupted() {
	}
}
