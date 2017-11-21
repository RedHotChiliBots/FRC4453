package org.usfirst.frc.team4453.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick drive1Controller;

	public OI() {
		System.out.println("OI starting...");
    	
	    	drive1Controller = new Joystick(RobotMap.FIRST_CONTROLLER);	
	}
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
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
	
	public Joystick getDrive1Controller() {
        return drive1Controller;
    }

    //for driving
	public double getLeftYDriveStick() {
    	double leftY = drive1Controller.getRawAxis(RobotMap.Y_AXIS);
    	return (Math.abs(leftY) < 0.15 ? 0.0 : -leftY);
    }
	public double getLeftXDriveStick() {
		double leftX = drive1Controller.getRawAxis(RobotMap.X_AXIS);
		return (Math.abs(leftX) < 0.15 ? 0.0 : -leftX);
	}
	public double getThrottleDrive() {
		double leftZ = drive1Controller.getRawAxis(RobotMap.THROTTLE_AXIS);
			return 0.35 * -leftZ + 0.65;
	}
}

