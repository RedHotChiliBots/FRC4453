package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class notUsed_TiltInitialize extends Command {

	public notUsed_TiltInitialize() {
		requires(Robot.shooter);
		setInterruptible(false); // can not be interrupted
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.shooter.tiltSetAngle(47.0);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}


	@Override
	protected void interrupted() {
	}
}
