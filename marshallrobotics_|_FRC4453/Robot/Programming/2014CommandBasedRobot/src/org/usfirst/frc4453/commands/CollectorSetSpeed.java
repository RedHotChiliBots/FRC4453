/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc4453.commands;

/**
 *
 * Command
 * Sets Collector motor speed from the joystick,
 * parameter constant, or preference value.
 * 
 * @author Developer
 */
public class CollectorSetSpeed extends CommandBase {
	
	private double m_speed;
	private boolean m_dyn = false;
	private boolean m_src = false;
	
	/**
	 * Set Collector speed using input from joystick
	 */
	public CollectorSetSpeed() {
        // Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(collector);
		m_dyn = true;
	}

	/**
	 * Set Collector speed using either the joystick or
	 * preference value
	 * 
	 * @param src boolean - True use preference; False use joystick
	 */
	public CollectorSetSpeed(boolean src) {
        // Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(collector);
		m_dyn = true;
		m_src = src;
	}

	/**
	 * Set Collector speed from parameter constant
	 * 
	 * @param speed double - motor speed (-1.0 to 1.0)
	 */
	public CollectorSetSpeed(double speed) {
        // Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(collector);
		m_speed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("CollectorSetSpeed");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double m_scale;
		
		if (m_dyn) {
			if (m_src) {
				m_speed = CommandBase.prefs.getDouble("CollectorSpeed", 1.0);
			} else {
				m_scale = CommandBase.prefs.getDouble("CollectorScale", 1.0);
				m_speed = m_scale * CommandBase.oi.getCollectorStick();	// range -1 to +1	
			}
		}

		collector.setCollectorMotor(m_speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (!m_dyn || m_src);
	}

	// Called once after isFinished returns true
	protected void end() {
	}

    // Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
