package org.usfirst.frc.team4453.robot;

import org.usfirst.frc.team4453.robot.commands.BubblerStart;
import org.usfirst.frc.team4453.robot.commands.BubblerStop;
import org.usfirst.frc.team4453.robot.commands.ClimberClimb;
import org.usfirst.frc.team4453.robot.commands.PlaceGear;
import org.usfirst.frc.team4453.robot.commands.ShooterControlWithJoystick;
import org.usfirst.frc.team4453.robot.commands.ShooterTiltReset;
import org.usfirst.frc.team4453.robot.commands.ShooterYawReset;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public XboxController drive1Controller;
	public XboxController drive2Controller;

	public Button shooterWall;
	public Button shooterKey;
	public Button shooterGear;
	public Button shooterHopper;
	public Button shooterManualOverride;
	
	public Button bubblerBubble;	

	public Button climberClimb1;
	//public Button climberClimb2;
	
	public Button tiltReset;
	public Button yawReset;
	
	public Button grabberPlace;
	
	
	
	public class SHOOT_PRESET { 
		public static final double
		WALL = 1.73, KEY = 2.39, GEAR = 4.11, HOPPER = 3.81; // TODO
	};

	
	public OI() {
		System.out.println("OI starting...");

		// Define Controllers
		drive1Controller = new XboxController(RobotMap.FIRST_CONTROLLER);
		drive2Controller = new XboxController(RobotMap.SECOND_CONTROLLER);
		
		// Driver 1 Buttons

		// Climber
		climberClimb1 = new JoystickButton(drive1Controller, RobotMap.LEFT_BUMPER);
		climberClimb1.whileHeld(new ClimberClimb());
		//climberClimb2 = new JoystickButton(drive1Controller, RobotMap.LEFT_BUMPER);
		//climberClimb2.whileHeld(new ClimberClimb());
		// Driver 2 Buttons

		// Bubbler
		bubblerBubble = new JoystickButton(drive2Controller, RobotMap.START);
		bubblerBubble.whileHeld(new BubblerStart());
		bubblerBubble.whenReleased(new BubblerStop());
		
		// Manual Shooter Override (left bumper finishes command)
		shooterManualOverride = new JoystickButton(drive2Controller, RobotMap.RIGHT_BUMPER);
		shooterManualOverride.toggleWhenPressed(new ShooterControlWithJoystick());
//		shooterManualOverride.whenReleased(new AutoShooterControl());

		// Tilt/Yaw Reset
		tiltReset = new JoystickButton(drive2Controller, RobotMap.BACK);
		tiltReset.whenPressed(new ShooterTiltReset());
		yawReset = new JoystickButton(drive2Controller, RobotMap.BACK);
		yawReset.whenPressed(new ShooterYawReset());
		
		grabberPlace = new JoystickButton(drive2Controller, RobotMap.A_BUTTON);
		grabberPlace.whenPressed(new PlaceGear());
		
		System.out.println("OI running.");
	}
	
	// Tank Drive
	public double getLeftYDriveStick() {
		double leftY = drive1Controller.getRawAxis(RobotMap.LEFT_Y_AXIS);
		return (Math.abs(leftY) < 0.15 ? 0.0 : -leftY);
	}
	public double getRightYDriveStick() {
		double rightY = drive1Controller.getRawAxis(RobotMap.RIGHT_Y_AXIS);
		return (Math.abs(rightY) < 0.15 ? 0.0 : -rightY);
	}

	// Shooter aim tilt/yaw
	public double getYShooterStick() {
		double leftY = drive2Controller.getRawAxis(RobotMap.LEFT_Y_AXIS);
		return (Math.abs(leftY) < 0.15 ? 0.0 : leftY);
	}
	public double getXShooterStick() {
		double rightX = drive2Controller.getRawAxis(RobotMap.RIGHT_X_AXIS);
		return (Math.abs(rightX) < 0.15 ? 0.0 : rightX);
	}
	
	// Shooter speed adjustment
	public double getRightShooterTrigger() {
		double rightTrigValue = drive2Controller.getRawAxis(RobotMap.RIGHT_TRIGGER_AXIS);
		return rightTrigValue;
	}
	public double getLeftShooterTrigger() {
		double leftTrigValue = drive2Controller.getRawAxis(RobotMap.LEFT_TRIGGER_AXIS);
		return leftTrigValue;
	}

	
	// Gear grab/lift booleans
	public boolean getDPAD_X() {
		double dpadValue = drive2Controller.getPOV();
		return dpadValue == 90 || dpadValue == 270;
	}
	public boolean getDPAD_Y() {
		double dpadValue = drive2Controller.getPOV();
		return dpadValue == 0 || dpadValue == 180;
	}
}
