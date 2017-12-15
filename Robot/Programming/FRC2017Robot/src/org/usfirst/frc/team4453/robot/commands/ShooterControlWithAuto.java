
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
public class ShooterControlWithAuto extends Command {

	private double speed;
	
	public ShooterControlWithAuto(double s) {
		requires(Robot.shooter);
		speed = s;
	}

	@Override
	protected void initialize() {
//		System.out.println("ShooterControlWithAuto");
	}

	@Override
	protected void execute() {
		
		Robot.hopper.gateOpen();		// SDS: Added this line
		Robot.shooter.shooterSetSpeed(speed);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.hopper.gateClose();		// SDS: Added this line
	}

	@Override
	protected void interrupted() {
		Robot.shooter.shooterStop();
	}
}
