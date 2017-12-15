package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoGearRight extends CommandGroup {
	
//	private static final double ALIGN_DIST = 24.0;
//	private static final double ALIGN_ANGLE = 60;
	
    public AutoGearRight() {
    	addSequential(new AutoDriveTime(SmartDashboard.getNumber("rtime", 3.8), .25));
        addSequential(new WaitCommand(0.25));
        addSequential(new PlaceGear());
        addSequential(new WaitCommand(0.25));
        addSequential(new AutoDriveTime(0.5, -0.5));
//    	addSequential(new AutoDriveTime(3.1, .25));
//    	addSequential(new AutoTurnTime(0.25, 0.5));
//    	addSequential(new AutoDriveTime(0.5, .25));
//        addSequential(new WaitCommand(0.25));
//        addSequential(new GrabberRelease());
//        addSequential(new WaitCommand(0.25));
//        addSequential(new AutoDriveTime(0.5, -0.5));
    	//addSequential(new AutoPlaceGear());
    }
}
