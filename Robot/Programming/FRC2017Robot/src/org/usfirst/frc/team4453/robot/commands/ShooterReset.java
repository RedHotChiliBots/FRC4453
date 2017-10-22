package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterReset extends CommandGroup {

    public ShooterReset() {
    	setInterruptible(false);
    	requires(Robot.shooter);
        addParallel(new ShooterTiltReset());
        addSequential(new ShooterYawReset());
    }
}
