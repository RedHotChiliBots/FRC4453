package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoStratPrimary extends CommandGroup {

	public AutoStratPrimary() {
		addSequential(new AutoDriveDistance(97));
		addSequential(new AutoTurn(90)); // Turn to hopper.
		addSequential(new AutoDriveDistance(6*12+1)); // Hit the hopper to get fuel.
		addSequential(new WaitCommand(2.5)); // Wait for the fuel to fall in.
		addSequential(new AutoDriveDistance(-12));
		addSequential(new AutoTurn(90));
		addSequential(new AutoBoiler()); // Fire fuel.
	}
}
