package org.usfirst.frc4453.commands;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.usfirst.frc4453.RobotMap;

/**
 * Command (sensors)
 * <p>
 * Reset horizontal and vertical camera servos
 *
 * @author Developer
 */

public class ResetCameraServos extends CommandBase {
 	
    /**
     * 
	 */
	public ResetCameraServos() {
		requires(sensors);
	}
	
	// 
	protected void initialize() {
		System.out.println("ResetCameraServos");
	}

	//
	protected void execute() {

		sensors.servoSetCameraHorz(RobotMap.SERVO_HPOS_RESET);
		sensors.servoSetCameraVert(RobotMap.SERVO_VPOS_RESET);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
