package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.library.Vision;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoShooterControl extends Command {
		
	public AutoShooterControl() {
		requires(Robot.shooter);
	}

	@Override
	protected void initialize() {
		Vision.setCamera("boiler", 0);
	}

	@Override
	protected void execute() {
		SmartDashboard.putString("Control Mode", "Auto");
		if (Vision.boilerVisible()) {
			Robot.shooter.yawSetAngle(Robot.shooter.yawGetAngle()-Vision.getBoilerAngleOffset());
			Robot.shooter.tiltSetAngle(Robot.shooter.getAimAngle(Vision.getBoilerDist()) + Robot.ahrs.getRoll());
			if(Robot.oi.drive2Controller.getBumper(Hand.kLeft))
			{
				if(Vision.getBoilerAngleOffset() < 2)
				{
					Robot.oi.drive2Controller.setRumble(RumbleType.kLeftRumble, 0);
					Robot.oi.drive2Controller.setRumble(RumbleType.kRightRumble, 0);
					Robot.shooter.shooterFire(Robot.shooter.getAimSpeed(Vision.getBoilerDist()));
				}
				else
				{
					if(Vision.getBoilerAngleOffset() < 0)
					{
						Robot.oi.drive2Controller.setRumble(RumbleType.kLeftRumble, (-Vision.getBoilerAngleOffset()-2.0) / 10.0);
						Robot.oi.drive2Controller.setRumble(RumbleType.kRightRumble, 0);
					}
					else
					{
						Robot.oi.drive2Controller.setRumble(RumbleType.kLeftRumble, 0);
						Robot.oi.drive2Controller.setRumble(RumbleType.kRightRumble,(Vision.getBoilerAngleOffset()-2.0) / 10.00);
					}
				}
			}
			else
			{
				Robot.shooter.shooterStop();
				Robot.oi.drive2Controller.setRumble(RumbleType.kLeftRumble, 0);
				Robot.oi.drive2Controller.setRumble(RumbleType.kRightRumble, 0);
			}
		}
		else
		{
			if(Robot.oi.drive2Controller.getBumper(Hand.kLeft))
			{
				Robot.oi.drive2Controller.setRumble(RumbleType.kLeftRumble, 1);
				Robot.oi.drive2Controller.setRumble(RumbleType.kRightRumble, 1);
			}
			else
			{
				Robot.oi.drive2Controller.setRumble(RumbleType.kLeftRumble, 0);
				Robot.oi.drive2Controller.setRumble(RumbleType.kRightRumble, 0);
			}
		}
	}

	@Override
	protected boolean isFinished() {
		return Robot.oi.drive2Controller.getBumper(Hand.kRight);
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
