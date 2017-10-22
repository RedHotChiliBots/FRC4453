package org.usfirst.frc.team4453.robot.subsystems;

import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.BubblerStop;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Bubbler extends Subsystem {

	private static double speed = -1.0;	// speed of the collector motor
										// set to -1 to reverse; Talon reverse did not work

	private static CANTalon talonBubbler;

	public Bubbler() {
		System.out.println("Bubbler is Starting...");

		talonBubbler = new CANTalon(RobotMap.BUBBLER_MOTOR);

		System.out.println("Bubbler is running.");
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new BubblerStop());
	}

	public void bubbler() {
		talonBubbler.set(speed);
	}

	public void stop() {
		talonBubbler.set(0.0);
	}
}
