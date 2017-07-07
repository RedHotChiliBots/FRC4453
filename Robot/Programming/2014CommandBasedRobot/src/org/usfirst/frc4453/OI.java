
package org.usfirst.frc4453;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc4453.commands.ResetCameraServos;
import org.usfirst.frc4453.commands.ShootFireTrigger;
import org.usfirst.frc4453.commands.ShootHoldTrigger;
import org.usfirst.frc4453.commands.ShootSetDistance;
import org.usfirst.frc4453.commands.TiltSetAngle;
import org.usfirst.frc4453.commands.ResetLaunchDistTilt;
import org.usfirst.frc4453.commands.DriveSetDirection;
import org.usfirst.frc4453.subsystems.LaunchTilt;
import org.usfirst.frc4453.subsystems.LaunchShoot;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
    
	private final Joystick firstController; 
	private final Joystick secondController;
	private final JoystickButton shoot5Button;
	private final JoystickButton shoot10Button;
	private final JoystickButton tilt5Button;
	private final JoystickButton tilt10Button;
	private final JoystickButton HoldButton;
	private final JoystickButton FireButton;
	private final JoystickButton resetServoButton;
	private final JoystickButton resetLaunchButton;
	private final JoystickButton DriveButton;
	
	private static double collectorDirection = 1.0;
	private static double launchDistDirection = 1.0;
	private static double launchTiltDirection = 1.0;
	
	public OI() {
		firstController = new Joystick(RobotMap.FIRST_CONTROLLER);
		secondController = new Joystick(RobotMap.SECOND_CONTROLLER);
		
		resetServoButton = new JoystickButton(firstController,RobotMap.LEFT_STICK);
		resetLaunchButton = new JoystickButton(firstController,RobotMap.RIGHT_STICK);
		                
		resetServoButton.whenPressed(new ResetCameraServos());
		resetLaunchButton.whenPressed(new ResetLaunchDistTilt());		

		shoot5Button = new JoystickButton(secondController,RobotMap.B_BUTTON);
		shoot10Button = new JoystickButton(secondController,RobotMap.A_BUTTON);

		shoot5Button.whenPressed(new ShootSetDistance(LaunchShoot.RESET_STRETCH));		
		shoot10Button.whenPressed(new ShootSetDistance(LaunchShoot.FULL_STRETCH));		

		tilt5Button = new JoystickButton(secondController,RobotMap.X_BUTTON);
		tilt10Button = new JoystickButton(secondController,RobotMap.Y_BUTTON);

		tilt5Button.whenPressed(new TiltSetAngle(LaunchTilt.HALF_TILT));		
		tilt10Button.whenPressed(new TiltSetAngle(LaunchTilt.RESET_TILT));

		HoldButton = new JoystickButton(secondController,RobotMap.LEFT_BUMPER);
		FireButton = new JoystickButton(secondController,RobotMap.RIGHT_BUMPER);

		HoldButton.whenPressed(new ShootHoldTrigger());		
		FireButton.whenPressed(new ShootFireTrigger());

		DriveButton = new JoystickButton(firstController,RobotMap.START);
		DriveButton.whenPressed(new DriveSetDirection());
	}
	
	public Joystick getFirstController() {
		return firstController;
	}
	
	public double getLeftDriveStick() {
		double pos = getFirstController().getRawAxis(RobotMap.LEFT_Y_AXIS);
		if (Math.abs(pos) < RobotMap.DRIVE_MIN_CMD) {
			pos = 0.0;
		}
		return pos;
	}
	
	public double getRightDriveStick() {
		double pos = getFirstController().getRawAxis(RobotMap.RIGHT_Y_AXIS);
		if (Math.abs(pos) < RobotMap.DRIVE_MIN_CMD) {
			pos = 0.0;
		}
		return pos;
	}
	
	public Joystick getSecondController() {
		return secondController;
	}
	
	public double getVertServoStick() {
		double pos = getSecondController().getRawAxis(RobotMap.LEFT_Y_AXIS);
		if (Math.abs(pos) < RobotMap.SERVO_MIN_CMD) {
			pos = 0.0;
		}
		//System.out.println("Getting vertical stick "+pos);
		return pos;
	}
	
	public double getHorzServoStick() {
		double pos = getSecondController().getRawAxis(RobotMap.LEFT_X_AXIS);
		if (Math.abs(pos) < RobotMap.SERVO_MIN_CMD) {
			pos = 0.0;
		}
		//System.out.println("Getting horizontal stick "+pos);
		return pos;
	}
	
	public double getLaunchDistStick() {
		double pos = getSecondController().getRawAxis(RobotMap.RIGHT_Y_AXIS);
		if (Math.abs(pos) < RobotMap.LAUNCH_DIST_MIN_CMD) {
			pos = 0.0;
		}
		//System.out.println("Getting vertical stick "+pos);
		return pos*launchDistDirection;
	}
	
	public double getLaunchTiltStick() {
		double pos = getSecondController().getRawAxis(RobotMap.RIGHT_X_AXIS);
		if (Math.abs(pos) < RobotMap.LAUNCH_TILT_MIN_CMD) {
			pos = 0.0;
		}
		//System.out.println("Getting horizontal stick "+pos);
		return pos*launchTiltDirection;
	}

	public double getCollectorStick() {
		double pos = getSecondController().getRawAxis(RobotMap.TRIGGER_AXIS);
		if (Math.abs(pos) < RobotMap.LAUNCH_COLLECTOR_MIN_CMD) {
			pos = 0.0;
		}
		//System.out.println("Getting horizontal stick "+pos);
		return pos*collectorDirection;
	}
	
	public void reverseCollectorStick(boolean dir) {
		collectorDirection = (dir ? -1.0 : 1.0);
	}
	
	public void reverseTiltStick(boolean dir) {
		launchTiltDirection = (dir ? -1.0 : 1.0);
	}
	
	public void reverseDistStick(boolean dir) {
		launchDistDirection = (dir ? -1.0 : 1.0);
	}
	
	public boolean isReverseCollectorStick() {
		return (collectorDirection == -1.0);
	}
	
	public boolean isReverseTiltStick() {
		return (launchTiltDirection == -1.0);
	}
	
	public boolean isReverseDistStick() {
		return (launchDistDirection == -1.0);
	}
}

