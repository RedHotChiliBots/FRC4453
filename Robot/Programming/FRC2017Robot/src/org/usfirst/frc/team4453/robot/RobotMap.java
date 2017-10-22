package org.usfirst.frc.team4453.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public static final int
		// controller ports
		FIRST_CONTROLLER = 0,
		SECOND_CONTROLLER = 1;
	
	
    public static final int
    	// xbox controller axis
		LEFT_X_AXIS = 0,
		LEFT_Y_AXIS = 1,
		LEFT_TRIGGER_AXIS = 2,
		RIGHT_TRIGGER_AXIS = 3,
		RIGHT_X_AXIS = 4,
		RIGHT_Y_AXIS = 5,
		DPAD_X_AXIS = 6,
		DPAD_Y_AXIS = 7;

	public static final int
		// xbox controller buttons
		A_BUTTON = 1,
		B_BUTTON = 2,
		X_BUTTON = 3,
		Y_BUTTON = 4,
		LEFT_BUMPER = 5,
		RIGHT_BUMPER = 6,
		BACK = 7,
		START = 8,
		LEFT_STICK = 9,
		RIGHT_STICK = 10;

	public static final int
		// attack 3 axis
		X_AXIS = 0,
		Y_AXIS = 1,
		THROTTLE_AXIS = 2;

	public static final int
		// attack 3 buttons
		TRIGGER_1 = 1,
		BUTTON_2 = 2,
		BUTTON_3 = 3,
		BUTTON_4 = 4,
		BUTTON_5 = 5,
		BUTTON_6 = 6,
		BUTTON_7 = 7,
		BUTTON_8 = 8,
		BUTTON_9 = 9,
		BUTTON_10 = 10,
		BUTTON_11 = 11;

	public static final int
		// Chassis Subsystem
		DRIVE_L1_MOTOR = 10,
		DRIVE_L2_MOTOR = 11,
		DRIVE_R1_MOTOR = 12,
		DRIVE_R2_MOTOR = 13,
	
		// Bubbler Subsystem
		BUBBLER_MOTOR = 14,
			
		// Shooter Subsystem
		TILT_MOTOR = 15,
		YAW_MOTOR = 16,
		SHOOTER_MOTOR = 17,	
		
		// Agitator Subsystem
		AGITATOR_MOTOR = 18,
	
		// Climber Subsystem
		CLIMBER_MOTOR = 19; // TODO
		
	public static final int
		// Solenoids
		GEAR_GRAB = 0,
		GEAR_RELEASE = 1,
		GEAR_TIPUP = 2,
		GEAR_TIPDOWN = 3;
	
	public static final int
		// Servos
		HOPPER_GATE = 0;
	
	public static final int
		// Analog Inputs
		PRESSURE_SENSOR = 0;
}
