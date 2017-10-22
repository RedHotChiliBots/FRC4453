package org.usfirst.frc.team4453.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {
	
//	public boolean xbox;
//	public boolean attack3;
	
	// controller ports
    public static final int
        FIRST_CONTROLLER = 0,
        SECOND_CONTROLLER = 1;
    
    public static final int
    	X_AXIS = 0,
    	Y_AXIS = 1,
    	THROTTLE_AXIS = 2;
    
	public static final int
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

    // xbox controller axis
    /*public static final int
		LEFT_X_AXIS = 0,
		LEFT_Y_AXIS = 1,
		LEFT_TRIGGER_AXIS = 2,
		RIGHT_TRIGGER_AXIS = 3,
		RIGHT_X_AXIS = 4,
		RIGHT_Y_AXIS = 5,
	//These are the D-pad buttons
		DPAD_X_AXIS = 6,
		DPAD_Y_AXIS = 7;
    
	// xbox controller buttons
	public static final int
		A_BUTTON = 1,
		B_BUTTON = 2,
		X_BUTTON = 3,
		Y_BUTTON = 4,
		LEFT_BUMPER = 5,
		RIGHT_BUMPER = 6,
		BACK = 7,
		START = 8,
		LEFT_STICK = 9,
		RIGHT_STICK = 10;*/

	// Analog I/0	
	public static final int
		GYRO = 0;
		
	// Digital I/0	
	public static final int
		LEFT_DRIVE_ENCODER_A = 0,
		LEFT_DRIVE_ENCODER_B = 1,
		RIGHT_DRIVE_ENCODER_A = 2,
		RIGHT_DRIVE_ENCODER_B = 3,
		TILT_ENCODER_A = 4,
		TILT_ENCODER_B = 5;
	public static final int
		ARM_LIMIT = 7,
		WINCH_LIMIT = 8,
		SHOOTER_MIN_LIMIT = 9;
	
	// CAN Bus - Device IDs
	public static final int //Drive motors
		LEFT_FRONT_DRIVE_MOTOR = 0,
		LEFT_BACK_DRIVE_MOTOR = 1,
		RIGHT_FRONT_DRIVE_MOTOR = 2,
		RIGHT_BACK_DRIVE_MOTOR = 3,
		
		LEFT_SHOOTER_MOTOR = 4,
		RIGHT_SHOOTER_MOTOR = 5,
		
		LEFT_TILT_MOTOR = 6,
		RIGHT_TILT_MOTOR = 7,
		
		ARM_MOTOR = 8,
		WINCH_MOTOR = 9;

	// PWMs
	//public static final int
	
	
	public static final int //Pneumatic solenoids
		SHOOTER_SOLENOID1ST = 0,
		SHOOTER_SOLENOID2ND = 1;
	
	public static final int //Compressor
		COMPRESSOR = 0;
	
	// Relay connections
	//public static final int
//	public static final int
//		NOT_USED = 0;
}

