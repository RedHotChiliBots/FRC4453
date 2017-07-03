package org.usfirst.frc.team4453.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4453.robot.RobotMap;
import org.usfirst.frc.team4453.robot.commands.MasterReset;
import org.usfirst.frc.team4453.robot.commands.ResetToterMotor;
import org.usfirst.frc.team4453.robot.commands.SetTatrowForward;
import org.usfirst.frc.team4453.robot.commands.SetTatrowReverse;
import org.usfirst.frc.team4453.robot.commands.SetNextToterPosition;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */


public class OI {
	private Joystick driveController;
	private Joystick toterController;

	private Button resetToterButton;
	private Button addScoreButton;
	private Button addStepButton;
	private Button nextToterButton;

	private Button tatrowIn;
	private Button tatrowOut;

	private Button masterReset;
	
	public OI() {
    	System.out.println("OI starting...");
    	
	    driveController = new Joystick(RobotMap.FIRST_CONTROLLER);		
	    toterController = new Joystick(RobotMap.SECOND_CONTROLLER);		

	    resetToterButton = new JoystickButton(toterController, RobotMap.Y_BUTTON);
	    addScoreButton = new JoystickButton(toterController, RobotMap.A_BUTTON);
	    addStepButton = new JoystickButton(toterController, RobotMap.B_BUTTON);
	    nextToterButton = new JoystickButton(toterController, RobotMap.X_BUTTON);
	    
		resetToterButton.whenPressed(new ResetToterMotor());
		addScoreButton.whenPressed(new SetNextToterPosition());
		addStepButton.whenPressed(new SetNextToterPosition());
		nextToterButton.whenPressed(new SetNextToterPosition());

		tatrowIn = new JoystickButton(toterController, RobotMap.RIGHT_BUMPER);
		tatrowOut = new JoystickButton(toterController, RobotMap.LEFT_BUMPER);
		
		tatrowIn.whileHeld(new SetTatrowForward());
		tatrowOut.whileHeld(new SetTatrowReverse());
		
		masterReset = new JoystickButton(toterController, RobotMap.BACK);
		
		masterReset.whenPressed(new MasterReset());
		
	    System.out.println("OI is running.");
	}
	
	//// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
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
    
    public double getLeftYDriveStick() {
    	double left_Y_pos = driveController.getRawAxis(RobotMap.LEFT_Y_AXIS);
    	return (Math.abs(left_Y_pos) < 0.15 ? 0.0 : left_Y_pos);
    }
    
    public double getLeftXDriveStick() {
    	double left_X_pos = driveController.getRawAxis(RobotMap.LEFT_X_AXIS);
    	return (Math.abs(left_X_pos) < 0.15 ? 0.0 : left_X_pos);
    }
    
    public double getRightXDriveStick() {
    	double right_X_pos = driveController.getRawAxis(RobotMap.RIGHT_X_AXIS);
    	return (Math.abs(right_X_pos) < 0.15 ? 0.0 : right_X_pos);
    }
    
    public Joystick getDriveController() {
        return driveController;
    }

    public double getToterStick() {
    	double toter_pos = toterController.getRawAxis(RobotMap.RIGHT_Y_AXIS);
    	return (Math.abs(toter_pos) < 0.15 ? 0.0 : toter_pos);
    }
    
    public double getToterLeftYStick() {
    	double can_pos = toterController.getRawAxis(RobotMap.LEFT_Y_AXIS);
    	return (Math.abs(can_pos) < 0.15 ? 0.0 : can_pos);
    }
    
	public Joystick getToterController() {
        return toterController;
    }
	
	public double getToterTrigger() {
		double pos = toterController.getRawAxis(RobotMap.RIGHT_TRIGGER_AXIS) ;
				toterController.getRawAxis(RobotMap.LEFT_TRIGGER_AXIS);
		SmartDashboard.putNumber("Plunger Value", pos) ;
		return (Math.abs(pos) < 0.15 ? 0.0 : pos);
   
	}
	
}

// We need to add more comments into our code 'cause we can't remember stuff.