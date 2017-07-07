package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveDistance extends Command {
	private double dist;

	public AutoDriveDistance(double dist) {
		this.dist = dist;
		requires(Robot.chassis);
	}

	@Override
	protected void initialize() {
		System.out.print("Driving " + dist + " inches... ");
		Robot.chassis.disableChassisPID();
		Robot.chassis.driveDistanceStart(dist);
	}

	@Override
	protected void execute() {
		Robot.chassis.driveDistanceUpdate(dist);
	}

	@Override
	protected boolean isFinished() {
		return Robot.chassis.isDistanceOnTarget(0.5);
	}

	@Override
	protected void end() {
		System.out.println("done.");
		Robot.chassis.stop();
	}

	@Override
	protected void interrupted() {
		Robot.chassis.stop();
	}
}
