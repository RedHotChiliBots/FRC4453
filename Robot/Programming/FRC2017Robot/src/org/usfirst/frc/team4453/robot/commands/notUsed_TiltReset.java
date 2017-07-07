package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class notUsed_TiltReset extends InstantCommand {

	public notUsed_TiltReset() {
		requires(Robot.shooter);
		setInterruptible(false);
	}

	@Override
	protected void initialize() {
		System.out.println("TiltReset");
	}

	@Override
	protected void execute() {
		Robot.shooter.tiltLower();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		Robot.shooter.tiltStop();
	}
}
