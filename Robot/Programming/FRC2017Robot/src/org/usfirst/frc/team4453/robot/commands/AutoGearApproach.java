package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.library.Tilt;
import org.usfirst.frc.team4453.library.Vision;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoGearApproach extends Command {

	private double minDist = 0;

	public AutoGearApproach(double dist) {
		requires(Robot.chassis);
		minDist = dist;
	}

	@Override
	protected void initialize() {
		Vision.setCamera("gear", 2);
		Robot.chassis.enableChassisPID(0.25);
		System.out.println("AutoGearApproach init.");
	}

	@Override
	protected void execute() {
		Robot.chassis.chassisSetSetpoint(Vision.getGearAngleOffsetAdjusted() + Robot.chassis.chassisGetYaw() + 2);
		System.out.println("AutoGearApproach exec.");
	}

	@Override
	protected boolean isFinished() {
		return !Vision.gearVisible() || Vision.getGearDistAdjusted() / Tilt.getMPI() < minDist;
	}

	@Override
	protected void end() {
		Robot.chassis.disableChassisPID();
		Robot.chassis.stop();
		System.out.println("AutoGearApproach fini.");
	}

	@Override
	protected void interrupted() {
		Robot.chassis.disableChassisPID();
		Robot.chassis.stop();
	}
}
