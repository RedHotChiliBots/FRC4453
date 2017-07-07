package org.usfirst.frc.team4453.robot;


/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;


	// controller ports
    public static final int
        FIRST_CONTROLLER = 0,
        SECOND_CONTROLLER = 1;
    
    // xbox controller axis
    public static final int
		LEFT_X_AXIS = 0,
		LEFT_Y_AXIS = 1,
		LEFT_TRIGGER_AXIS = 2,
		RIGHT_TRIGGER_AXIS = 3,
		RIGHT_X_AXIS = 4,
		RIGHT_Y_AXIS = 5,
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
		START = 7,
		BACK = 8,
		LEFT_STICK = 9,
		RIGHT_STICK = 10;

	// Analog I/0	
	public static final int
		GYRO = 0;
		
	// Digital I/0	
	public static final int
		TOTER_ENCODER1 = 2,
		TOTER_ENCODER2 = 3,
		PLUNGER_FR_LIMIT = 4,
		PLUNGER_FL_LIMIT  = 5,
		PLUNGER_BR_LIMIT = 6,
		PLUNGER_BL_LIMIT = 7,
		CAN_LIMIT = 8, 
		
		TOTER_LIMIT = 9;
	
	// PWM connections
	public static final int //Drive motors
		TOTER_MOTOR = 0,
		FRONT_LEFT_DRIVE_MOTOR = 1,
		FRONT_RIGHT_DRIVE_MOTOR = 2,
		BACK_LEFT_DRIVE_MOTOR = 3,
		BACK_RIGHT_DRIVE_MOTOR = 4,
		RIGHT_PLUNGER_MOTOR = 5,
		LEFT_PLUNGER_MOTOR = 6,
		RIGHT_CAN_MOTOR = 7,
		LEFT_CAN_MOTOR = 8;
	
	// Relay connections
	public static final int
		TATROW_RIGHT_SPIKE = 0,
		TATROW_LEFT_SPIKE = 1;
}

