package org.usfirst.frc.team4453.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MasterReset extends CommandGroup {
    
    public  MasterReset() {
    	
    	System.out.println("Executing Order 66...");
    	
    	addParallel(new ResetCan());
    	addParallel(new ResetPlunger());
    	addParallel(new ResetToterMotor());
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	System.out.println("Order 66 complete, all Jedi eradicated.");
    }
}
