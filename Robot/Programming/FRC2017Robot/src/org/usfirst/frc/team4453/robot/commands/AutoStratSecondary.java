package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoStratSecondary extends CommandGroup {

    public AutoStratSecondary() {
        addSequential(new AutoDriveDistance(-8*12));
        addSequential(new AutoTurn(-45));
        addSequential(new AutoBoiler());
    }
}
