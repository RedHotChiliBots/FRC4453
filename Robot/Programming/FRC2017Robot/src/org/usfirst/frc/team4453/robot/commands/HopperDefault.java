package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HopperDefault extends CommandGroup {

    public HopperDefault() {
        addSequential(new HopperAgitatorReset());
        addSequential(new HopperAgitatorRun());
    }
}
