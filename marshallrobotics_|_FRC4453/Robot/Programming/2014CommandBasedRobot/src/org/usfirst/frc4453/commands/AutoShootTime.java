/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Autonomous Command
 * Used to drive forward for a specific time
 * and shoot the ball at a specified angle and strength.

 * @author Developer
 */
public class AutoShootTime extends CommandGroup {
	
	public AutoShootTime() {
        // Add Commands here:
		// e.g. addSequential(new Command1());
		//      addSequential(new Command2());
		// these will run in order.

		addSequential(new ShootHoldTrigger());
		addSequential(new DriveSetDirection(1.0));
		
		addParallel(new DriveWithTimer(true));		
		addParallel(new ShootSetDistance(true));
		addSequential(new TiltSetAngle(true));
		
		addSequential(new ShootFireTrigger());
		addSequential(new WaitCommand(1.0));
		addSequential(new ResetLaunchDistTilt());
		
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
