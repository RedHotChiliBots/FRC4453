package org.usfirst.frc.team4453.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoGearCenter extends CommandGroup {
	
    public AutoGearCenter() {
    	addSequential(new AutoDriveTime(3.3, .25));
        addSequential(new WaitCommand(0.25));
        addSequential(new PlaceGear());
        addSequential(new WaitCommand(0.25));
        addSequential(new AutoDriveTime(0.5, -0.5));
//    	addSequential(new AutoPlaceGear());
    }
}
