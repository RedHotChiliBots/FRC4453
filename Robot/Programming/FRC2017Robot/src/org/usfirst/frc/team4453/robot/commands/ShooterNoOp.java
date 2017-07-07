
package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * DriveWithjoystick - gets commands from the joysticks defined in OI to send to
 * the shooter method to drive the robot.
 * 
 * Requires the shooter. Only command that can be running for the shooter.
 * 
 */
public class ShooterNoOp extends Command {
	
	public ShooterNoOp() {
		requires(Robot.shooter);
	}

	@Override
	protected void initialize() {
		//System.out.println("ShooterNoOp: Initialize");
	}

	@Override
	protected void execute() {
		//System.out.println("ShooterNoOp: Execute");
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		//System.out.println("ShooterNoOp: End");
	}

	@Override
	protected void interrupted() {
	}
}
