package org.usfirst.frc.team4453.robot.commands;

import org.usfirst.frc.team4453.robot.OI;
import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBoilerAndGear extends CommandGroup {
    public AutoBoilerAndGear() {
        addSequential(new ShooterSpinup(Robot.shooter.getAimSpeed(OI.SHOOT_PRESET.WALL)));
        addSequential(new ShooterFire(5, Robot.shooter.getAimSpeed(OI.SHOOT_PRESET.WALL)));
        addSequential(new AutoDriveTime(0.5, -0.5));
        addSequential(new AutoTurnTime(0.5, 0.5));
        addSequential(new AutoPlaceGear());
    }
}
