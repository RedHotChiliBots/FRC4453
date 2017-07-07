package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GrabberToggleGrab extends InstantCommand {

	public GrabberToggleGrab() {
		requires(Robot.geargrabber);
	}

	@Override
	protected void execute() {
		Robot.geargrabber.toggleGrab();
	}
}

