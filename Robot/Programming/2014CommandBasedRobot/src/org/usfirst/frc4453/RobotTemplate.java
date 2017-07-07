/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4453;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4453.commands.CommandBase;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {

	Command autonomousCommand;


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.  This code is run one time only.
	 */
	public void robotInit() {
		// Initialize all subsystems
		CommandBase.init();

		// instantiate the command used for the autonomous period
		autonomousCommand = (Command) CommandBase.autoChooser.getSelected();
		//autonomousCommand = (Command) new AutoMoveForward();
		
		// reset sensors
		CommandBase.chassis.encodersReset();
		CommandBase.launchShoot.encoderReset();
		CommandBase.launchTilt.encoderReset();
		CommandBase.sensors.gyroReset();

        // initialize servos
		CommandBase.sensors.servoSetCameraHorz(RobotMap.SERVO_HPOS_RESET);
		CommandBase.sensors.servoSetCameraVert(RobotMap.SERVO_VPOS_RESET);
	}

	/**
	 * This function is run when autonomous mode is enabled.  It is run at the 
	 * beginning of autonomous mode. 
	 */
	public void autonomousInit() {
		// schedule the autonomous command (example)
		CommandBase.sensors.gyroReset();
		autonomousCommand = (Command) CommandBase.autoChooser.getSelected();
		//autonomousCommand = (Command) new AutoMoveForward();
		autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is run when teleop mode is enabled.  It is run at the 
	 * beginning of teleop mode. 
	 */
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to 
		// continue until interrupted by another command, remove
		// this line or comment it out.
		autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
        // Display sensor values on dashboard
		SmartDashboard.putNumber("Servo (vert)", CommandBase.sensors.servoGetCameraVert());
		SmartDashboard.putNumber("Servo (horz)", CommandBase.sensors.servoGetCameraHorz());
		SmartDashboard.putNumber("Encoder (left)", CommandBase.chassis.encoderGetLeftRate());
		SmartDashboard.putNumber("Encoder (right)", CommandBase.chassis.encoderGetRightRate());
		SmartDashboard.putNumber("Sonar", CommandBase.sensors.sonarGetRange());
		SmartDashboard.putNumber("Gyro", CommandBase.sensors.gyroGetAngle());
		SmartDashboard.putString("Encoder Dir (left)", (CommandBase.chassis.encoderGetLeftDirection() ? "Forward" : "Reverse"));
		SmartDashboard.putString("Encoder Dir (right)", (CommandBase.chassis.encoderGetRightDirection() ? "Forward" : "Reverse"));                
		SmartDashboard.putNumber("Encoder (shoot)", CommandBase.launchShoot.getDist());
		SmartDashboard.putString("Encoder Dir (shoot)", (CommandBase.launchShoot.encoderGetDirection() ? "Forward" : "Reverse"));
		SmartDashboard.putNumber("Encoder (tilt)", CommandBase.launchTilt.getAngle());
		SmartDashboard.putString("Encoder Dir (tilt)", (CommandBase.launchTilt.encoderGetDirection() ? "Forward" : "Reverse"));
	}
    
	/**
	 * This function is run when test mode is enabled.  It is run at the 
	 * beginning of test mode. 
	 */
	public void testInit() {
	}
    
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
    
	/**
	 * This function is run when the robot is disabled. 
	 */
	public void disabledInit() {
	}

	/**
	 * This function is called periodically during disabled mode 
	 */
	public void disabledPeriodic() {
	}
}
