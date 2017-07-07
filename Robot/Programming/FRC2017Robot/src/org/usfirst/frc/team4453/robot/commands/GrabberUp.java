package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GrabberUp extends InstantCommand {

	public GrabberUp() {
		requires(Robot.geargrabber);
	}

	@Override
	protected void execute() {
		Robot.geargrabber.tipUp();
	}
}
