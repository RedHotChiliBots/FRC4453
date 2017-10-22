package org.usfirst.frc.team4453.robot.commands;

/**
 *
 */

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class notUsed_TiltSetAngle extends Command {

	private double button;
	private double angle;

	public notUsed_TiltSetAngle(int button) {
		this.button = button;
	}

	@Override
	protected void initialize() {
		System.out.println("TiltSetAngle");

		requires(Robot.shooter);

		if (button == 1) {
			angle = SmartDashboard.getNumber("tiltAngle1", 30.0);
		}
		else if (button == 2) {
			angle = SmartDashboard.getNumber("tiltAngle2", 45.0);
		}
	}

	@Override
	protected void execute() {
		Robot.shooter.tiltSetAngle(angle);
	}

	@Override
	protected boolean isFinished() {
		return true; // Robot.shooter.onTarget();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
