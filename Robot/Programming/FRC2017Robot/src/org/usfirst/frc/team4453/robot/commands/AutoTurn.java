package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoTurn extends Command {

	private double setpoint;
	private double moveAngle;
	public AutoTurn(double angle) {
		requires(Robot.chassis);
		moveAngle = angle;
	}

	@Override
	protected void initialize() {
		setpoint = Robot.chassis.chassisGetYaw() + moveAngle;
		System.out.print("Turning " + moveAngle + " degrees to a heading of " + setpoint + "... ");
		Robot.chassis.enableChassisPID(0);
		Robot.chassis.chassisSetSetpoint(setpoint);
	}

	@Override
	protected void execute() {
		Robot.chassis.chassisSetSetpoint(setpoint);
	}

	@Override
	protected boolean isFinished() {
		return Robot.chassis.isOnTarget(1);
	}

	@Override
	protected void end() {
		System.out.println("done.");
		Robot.chassis.disableChassisPID();
	}

	@Override
	protected void interrupted() {
		Robot.chassis.disableChassisPID();
	}
}
