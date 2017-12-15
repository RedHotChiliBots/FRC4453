package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoNoOp extends Command {

	public AutoNoOp() {
		requires(Robot.chassis);
	}

	@Override
	protected void initialize() {
		setTimeout(2.0);
	}

	@Override
	protected void execute() {
		Robot.chassis.stop();
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
