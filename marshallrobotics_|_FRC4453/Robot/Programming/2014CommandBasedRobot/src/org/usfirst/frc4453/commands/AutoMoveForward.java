/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous command
 * 	Moves robot forward for a specific time
 * 	Used to earn 20pts to cross zone line
 * 
 * @author developer
 *
 */
public class AutoMoveForward extends CommandGroup {
	
	public AutoMoveForward() {
        // Add Commands here:
		// e.g. addSequential(new Command1());
		//      addSequential(new Command2());
		// these will run in order.

		//addSequential(new DriveSetDirection(1.0));
		addSequential(new DriveForTime(true));
		
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
	}
}
