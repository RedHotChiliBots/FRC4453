
package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 * DriveWithjoystick - gets commands from the joysticks defined in OI to send to
 * the chassis method to drive the robot.
 * 
 * Requires the chassis. Only command that can be running for the chassis.
 * 
 */
public class DriveWithJoysticks extends Command {

	public DriveWithJoysticks() {
		requires(Robot.chassis);
	}

	@Override
	protected void initialize() {
		System.out.println("DriveWithJoystick: init");
	}

	@Override
	protected void execute() {
		double rightCmd = Robot.oi.getRightYDriveStick();
		double leftCmd = Robot.oi.getLeftYDriveStick();
		if(Robot.oi.drive1Controller.getBumper(Hand.kRight))
		{
			rightCmd*=0.5;
			leftCmd*=0.5;
		}
		Robot.chassis.tankDrive(leftCmd, rightCmd);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		System.out.println("DriveWithJoystick: interrupt");
	}
}
