package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PlaceGear extends CommandGroup {

    public PlaceGear() {
        addSequential(new GrabberRelease());
        addSequential(new GrabberDown());

    }
}
