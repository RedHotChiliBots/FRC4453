package org.usfirst.frc4453.commands;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.usfirst.frc4453.RobotMap;

/**
 * Command (sensors)
 * <p>
 * Sets the position of the horizontal and vertical servos
 * connected to the camera.  If arguments are provided the
 * servos are positioned accordingly.  If no arguments are
 * provided then the position increment is read from the 
 * configured joystick.
 * <p>
 * The joysticks position is used as a "rate of change" value
 * rather than an absolute position value
 *
 * @author Developer
 */

public class CameraSetServo extends CommandBase {
    
    private double horzCmd;
    private double vertCmd;
    private double m_hPos;
    private double m_vPos;
    private boolean m_Dyn = false;
    private boolean hSet = false;
    private boolean vSet = false;
	
    /**
     * Use joystick values to compute a rate of change for the servo.
     * If the value is below the SERVO_MIN_CMD value, then set the
     * value to zero to eliminate noise from the joystick when in
     * its neutral position.
     */
	public CameraSetServo() {
		requires(sensors);

		m_Dyn = true;
	}

	/**
	 * Based on the first argument, set either the horizontal or the 
	 * vertical servo to the absolute position provided in the second
	 * argument.
	 * 
	 * @param servo int - Servo identifier
	 * @param pos double - Position angle, range -45 degrees to +45 degrees
	 */
	public CameraSetServo(int servo, double pos) {
		requires(sensors);

		if (servo == RobotMap.HORZ_CAMERA_SERVO) {
			horzCmd = pos;
			hSet = true;
			
		} else if (servo == RobotMap.VERT_CAMERA_SERVO) {
			vertCmd = pos;
			vSet = true;
		}
	}

	// Get the current position of the servos
	protected void initialize() {
		System.out.println("CameraSetServo");
	}

	/**
	 * Based on how the command was called, either set the 
	 * new servo position to an absolution position or calculate
	 * a new incremental position and add to its existing position.
	 * <p>
	 * Servo position is a range of 0 to +1.
	 */
	protected void execute() {
		/**
		 * Calculate a new incremental position and 
		 * limit to the range from 0 to 1.
		 */
		if (m_Dyn) {
			horzCmd = sensors.servoGetCameraHorz();
			vertCmd = sensors.servoGetCameraVert();
			
			//System.out.println("from joystick");
                        
			m_hPos = CommandBase.oi.getHorzServoStick();	// range -1 to +1
			//System.out.println("SetCameraServo orig joystick m_hPos = "+m_hPos);

			horzCmd += (m_hPos * RobotMap.SERVO_MAX_RATE);
			if (horzCmd > 45.0) {
				horzCmd = 45.0 ;
			} else if (horzCmd < -45.0) {
				horzCmd = -45.0;
			}

			m_vPos = CommandBase.oi.getVertServoStick();	// range -1 to +1
			//System.out.println("SetCameraServo orig joystick m_vPos = "+m_vPos);

			vertCmd += (m_vPos * RobotMap.SERVO_MAX_RATE);
			if (vertCmd > 45.0) {
				vertCmd = 45.0 ;
			} else if (vertCmd < -45.0) {
				vertCmd = -45.0;
			}
		}
		
		//System.out.println("SetCameraServo execute exit horzCmd = "+horzCmd+" vertCmd = "+vertCmd);
		if (m_Dyn || hSet) {
			sensors.servoSetCameraHorz(horzCmd);
		}
		if (m_Dyn || vSet) {
			sensors.servoSetCameraVert(vertCmd);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (!m_Dyn);
	}

	// Called once after isFinished returns true
	protected void end() {
	}

    // Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
