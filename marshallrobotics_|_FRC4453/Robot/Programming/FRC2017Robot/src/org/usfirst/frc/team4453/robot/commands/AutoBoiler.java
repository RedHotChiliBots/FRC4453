package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBoiler extends CommandGroup {

	public AutoBoiler() {
		addSequential(new ShooterAimBoiler(false));
		addSequential(new AutoShoot());
	}
}
