/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.subsystems;

import org.usfirst.frc4453.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc4453.commands.CollectorSetSpeed;

/**
 *
 * @author Developer
 */

public class Collector extends Subsystem {

	public final double COLLECTOR_FORWARD = 1.0;
	public final double COLLECTOR_REVERSE = -1.0;
	public final double COLLECTOR_STOP = 0.0;

	private static Talon collectorMotor;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Collector() {
		collectorMotor = new Talon(RobotMap.COLLECTOR_MOTOR);

		setCollectorMotor(COLLECTOR_STOP);
		
		LiveWindow.addActuator("Collector", "Collector Motor", (Talon) collectorMotor);
	}
	// Define default command for subsystem, used when no other commands are active
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		setDefaultCommand(new CollectorSetSpeed());
	}
	
	/**
	 * Set the Collector motor speed
	 * 
	 * @param speed double - motor speed (-1.0 to 1.0)
	 */
	public void setCollectorMotor(double speed) {
		collectorMotor.set(speed);
	}
	
	/**
	 * Get the Collector motor speed
	 * 
	 * @return double - motor speed (-1.0 to 1.0)
	 */
	public double getCollectorMotor() {
		return collectorMotor.get();
	}
}
