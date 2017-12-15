package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class AutoTurnTime extends TimedCommand {

	private double speed;
	public AutoTurnTime(double time, double spd) {
		super(time);
		requires(Robot.chassis);
		speed = spd;
	}

	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		Robot.chassis.arcadeDrive(0, speed);
	}

	@Override
	protected void end() {
		Robot.chassis.stop();
	}

	@Override
	protected void interrupted() {
		Robot.chassis.stop();
	}
}
