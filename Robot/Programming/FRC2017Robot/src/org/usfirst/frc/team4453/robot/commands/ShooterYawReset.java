package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShooterYawReset extends CommandGroup {

    public ShooterYawReset() {
        addSequential(new ShooterYawReset1());
        addSequential(new ShooterYawReset2());
        addSequential(new ShooterResetDefaultCommand());
    }
}
