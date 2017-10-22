package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GrabberGrab extends InstantCommand {

	public GrabberGrab() {
		requires(Robot.geargrabber);
	}

	@Override
	protected void execute() {
		Robot.geargrabber.grab();
	}
}
