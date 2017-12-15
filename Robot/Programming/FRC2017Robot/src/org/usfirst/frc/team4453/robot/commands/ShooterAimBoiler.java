package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.library.Vision;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterAimBoiler extends Command {

	boolean doTestVisible;

	public ShooterAimBoiler(boolean giveUpIfNotFound) {
		requires(Robot.chassis);
		requires(Robot.shooter);
		doTestVisible = giveUpIfNotFound;
	}

	@Override
	protected void initialize() {
		Vision.setCamera("boiler", 5);
		Robot.chassis.enableChassisPID(0);
	}

	@Override
	protected void execute() {
		if (Vision.boilerVisible()) {
			Robot.shooter.tiltSetAngle(Robot.shooter.getAimAngle(Vision.getBoilerDist()));
			Robot.shooter.yawSetAngle(Vision.getBoilerAngleOffset());
			if (Robot.shooter.yawHitLimit()) {
				Robot.chassis.chassisSetSetpoint(Robot.chassis.chassisGetYaw() + Vision.getBoilerAngleOffset());
			}
			Robot.shooter.shooterSpinup(Robot.shooter.getAimSpeed(Vision.getBoilerDist()));
		}
	}

	@Override
	protected boolean isFinished() {
		return Robot.shooter.shooterIsReady(20) && Robot.shooter.tiltIsReady(1) && Robot.shooter.yawIsReady(1) && Math.abs(Vision.getBoilerAngleOffset()) < 5;
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
