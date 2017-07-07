package org.usfirst.frc4453;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The RobotMap is a mapping from the ports, sensors, and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
	
    //make the constructor private, so no one can instantiate this class
    private RobotMap() {
    }
	
    //	Constants
    public static final double
		DRIVE_MIN_CMD = 0.15,
    	LAUNCH_DIST_MIN_CMD = 0.15,
		LAUNCH_DIST_MAX_RATE = 0.5,
    	LAUNCH_TILT_MIN_CMD = 0.15,
    	LAUNCH_TILT_MAX_RATE = 0.5,
    	LAUNCH_COLLECTOR_MIN_CMD = 0.15,
        SERVO_MIN_CMD = 0.15,   // +-1 joystick value
        SERVO_MAX_RATE = 1.0,  // degrees / cycle (20ms)
        SERVO_HPOS_RESET = 0.0, // degrees
        SERVO_VPOS_RESET = 0.0; // degrees
	 
	// controller ports
    public static final int
        FIRST_CONTROLLER = 1,
        SECOND_CONTROLLER = 2;
    
    // xbox controller axis
    public static final int
		LEFT_X_AXIS = 1,
		LEFT_Y_AXIS = 2,
		TRIGGER_AXIS = 3,
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
		BACK = 7,
		START = 8,
		LEFT_STICK = 9,
		RIGHT_STICK = 10;

	// PWM connections
	public static final int //Drive motors
		LEFT_DRIVE_MOTOR = 1,
		RIGHT_DRIVE_MOTOR = 2,
		SHOOT_MOTORS = 3,
		TILT_MOTORS = 4,
		COLLECTOR_MOTOR = 5,
		VERT_CAMERA_SERVO = 9,
		HORZ_CAMERA_SERVO = 10;

   public static final int // Digital I/O
        LEFT_DRIVE_ENCODER_A = 1,
		LEFT_DRIVE_ENCODER_B = 2,
		RIGHT_DRIVE_ENCODER_A = 3,
		RIGHT_DRIVE_ENCODER_B = 4,
		TILT_ENCODER_A = 5,
		TILT_ENCODER_B = 6,
		SHOOT_ENCODER_A = 7,
		SHOOT_ENCODER_B = 8;

	// Analog
	public static final int
		GYRO = 1,
		SONAR = 3;
	
	// Relays
	public static final int
		SHOOT_TRIGGER = 1,
		CAMERA_LIGHT = 2;
	
	public static final int
		FIRE = DoubleSolenoid.Value.kForward_val,
		RESET = DoubleSolenoid.Value.kOff_val,
		ON = DoubleSolenoid.Value.kReverse_val;
}
