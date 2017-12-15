package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.library.Vision;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Aligns the robot with the gear target, following a curve until it is within 5 degrees.
 * @author Conner Ebbinghaus
 */

public class AutoGearAlign extends Command { // TODO: this
	
	private static final double[] gearAngles = {60, 0, -60};
	private static final double attackAngle = 45;
	
	
	private double targetAngle;
	
	public AutoGearAlign(int gearNum) {
		requires(Robot.chassis);
		targetAngle = gearAngles[gearNum];
	}

	@Override
	protected void initialize() {
		Vision.setCamera("gear", 5);
		Robot.chassis.enableChassisPID(0.5);
	}

	@Override
	protected void execute() {
		Robot.chassis.setSetpoint(targetAngle - Vision.getGearAngleOffsetAdjusted() + attackAngle);
	}

	@Override
	protected boolean isFinished() {
		return !Vision.gearVisible() || Vision.getGearAngleOffsetAdjusted() < 5 ;
	}

	@Override
	protected void end() {
		Robot.chassis.disableChassisPID();
	}

	@Override
	protected void interrupted() {
		Robot.chassis.disableChassisPID();
	}
}
