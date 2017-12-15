package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BubblerStart extends Command {

	public BubblerStart() {
		requires(Robot.bubbler);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.bubbler.bubbler();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.bubbler.stop();
	}

	@Override
	protected void interrupted() {
		Robot.bubbler.stop();
	}
}
