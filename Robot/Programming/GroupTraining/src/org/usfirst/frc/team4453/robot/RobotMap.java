package org.usfirst.frc.team4453.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
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
	
	public static final int //Drive motors
	LEFT_FRONT_DRIVE_MOTOR = 0,
	LEFT_BACK_DRIVE_MOTOR = 1,
	RIGHT_FRONT_DRIVE_MOTOR = 2,
	RIGHT_BACK_DRIVE_MOTOR = 3;
	
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
