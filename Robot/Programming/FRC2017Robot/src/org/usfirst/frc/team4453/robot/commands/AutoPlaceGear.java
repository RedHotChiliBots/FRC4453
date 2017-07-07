package org.usfirst.frc.team4453.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoPlaceGear extends CommandGroup {
	
    public AutoPlaceGear() {
    	addSequential(new AutoGearApproach(36.0));
    	addSequential(new AutoDriveTime(0.25, 2.0));
    	addSequential(new WaitCommand(0.75));
        addSequential(new PlaceGear());
        addSequential(new WaitCommand(0.5));
        addSequential(new AutoDriveTime(0.5, -0.5));
    }
}
