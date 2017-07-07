package org.usfirst.frc.team4453.robot.triggers;

import org.usfirst.frc.team4453.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class GearGrabRelease extends Trigger {

    public boolean get() {
		return Robot.oi.getDPAD_X();
    }
}
