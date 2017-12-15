package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ClimberClimb extends InstantCommand {

    public ClimberClimb() {
        requires(Robot.climber);
    }

    protected void execute() {
    	Robot.chassis.disableCompressor();
    	Robot.climber.climb();
    }
}
