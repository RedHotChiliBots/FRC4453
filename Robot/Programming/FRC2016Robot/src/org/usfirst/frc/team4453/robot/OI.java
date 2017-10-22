package org.usfirst.frc.team4453.robot;

import org.usfirst.frc.team4453.robot.commands.Aim;
import org.usfirst.frc.team4453.robot.commands.DriveWithCamera;
//import org.usfirst.frc.team4453.robot.commands.Climb;
import org.usfirst.frc.team4453.robot.commands.ShooterCollect;
import org.usfirst.frc.team4453.robot.commands.ShooterFire;
import org.usfirst.frc.team4453.robot.commands.ShooterReady;
import org.usfirst.frc.team4453.robot.commands.TiltInitialize;
import org.usfirst.frc.team4453.robot.commands.TiltManual;
import org.usfirst.frc.team4453.robot.commands.TiltReset;
import org.usfirst.frc.team4453.robot.commands.TiltWithCamera;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick drive1Controller;
	private Joystick drive2Controller;
	
	private Button reset;
	private Button init;
	
	private Button collect;
	private Button ready;
	private Button fire;
	private Button autoAim;
	private Button aimVert;
	private Button aimHoriz;
	private Button manualAim;
//	private Button setAngle1;
//	private Button setAngle2;
//	private Button climb;
	
	//private Button raise;
	//private Button lower;
	
	public OI() {
    	System.out.println("OI starting...");
    	
	    drive1Controller = new Joystick(RobotMap.FIRST_CONTROLLER);		
	    drive2Controller = new Joystick(RobotMap.SECOND_CONTROLLER);		
	    
	    reset = new JoystickButton(drive2Controller, RobotMap.BUTTON_4);
	    init = new JoystickButton(drive2Controller, RobotMap.BUTTON_5);

	    collect = new JoystickButton(drive2Controller, RobotMap.BUTTON_2);
	    ready = new JoystickButton(drive2Controller, RobotMap.BUTTON_3);
	    fire = new JoystickButton(drive2Controller, RobotMap.TRIGGER_1);
	    
	    autoAim = new JoystickButton(drive2Controller, RobotMap.BUTTON_7);
	    aimVert = new JoystickButton(drive2Controller, RobotMap.BUTTON_6);
	    aimHoriz = new JoystickButton(drive2Controller, RobotMap.BUTTON_8);
	    manualAim = new JoystickButton(drive2Controller, RobotMap.BUTTON_9);

	    //setAngle1 = new JoystickButton(drive2Controller, RobotMap.BUTTON_10);
	    //setAngle2 = new JoystickButton(drive2Controller, RobotMap.BUTTON_11);
	    
//	    climb = new JoystickButton(drive2Controller, RobotMap.BUTTON_6);
	    
	    reset.whenPressed(new TiltReset());
	    init.whenPressed(new TiltInitialize());

	    collect.whileHeld(new ShooterCollect());
	    ready.whenPressed(new ShooterReady());
	    fire.whenPressed(new ShooterFire());

//	    setAngle1.whenPressed(new TiltSetAngle(1));
//	    setAngle2.whenPressed(new TiltSetAngle(2));
	    
	    autoAim.whenPressed(new Aim());
	    aimVert.whenPressed(new TiltWithCamera());
	    aimHoriz.whenPressed(new DriveWithCamera() );
	    manualAim.whenPressed(new TiltManual());
	    
//	    climb.whenPressed(new Climb());
	    	    	   	    
	    System.out.println("OI is running.");
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
    
	//for driving
    public double getLeftXDriveStick() {
    	double leftX = drive1Controller.getRawAxis(RobotMap.X_AXIS);
    	return (Math.abs(leftX) < 0.15 ? 0.0 : -leftX);
    }
    
    public double getThrottleDrive(){
    	double speedValue = drive1Controller.getRawAxis(RobotMap.THROTTLE_AXIS);
    	return ((-speedValue + 1.0) / 2.0);
    }
    
	//only for tank on xbox
    //public double getRightYDriveStick() {
    //	double right_Y_pos = drive1Controller.getRawAxis(RobotMap.RIGHT_Y_AXIS);
    //	return (Math.abs(right_Y_pos) < 0.15 ? 0.0 : right_Y_pos);
    //}
    
    //for adjusting the shooter
    public double getRightYShooterStick() {
    	double rightY = drive2Controller.getRawAxis(RobotMap.Y_AXIS);
    	return (Math.abs(rightY) < 0.15 ? 0.0 : rightY);
    }
    
    public double getThrottleShooter(){
    	double speedValue = drive2Controller.getRawAxis(RobotMap.THROTTLE_AXIS);
    	return ((-speedValue + 1.0) / 2.0);
    }
    
	public Joystick getDrive2Controller() {
        return drive2Controller;
    }

}

