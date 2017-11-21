package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ClimberStop extends InstantCommand {

    public ClimberStop() {
        requires(Robot.climber);
    }

    protected void execute() {
    	Robot.chassis.enableCompressor();
    	Robot.climber.stop();
    }
}
